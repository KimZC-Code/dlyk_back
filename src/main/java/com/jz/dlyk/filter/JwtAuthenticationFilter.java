package com.jz.dlyk.filter;

import com.jz.dlyk.constants.JWTConstants;
import com.jz.dlyk.dto.R;
import com.jz.dlyk.entity.MyUserDetail;
import com.jz.dlyk.service.RedisService;
import com.jz.dlyk.utils.JWTUtil;
import com.jz.dlyk.utils.ResponseUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Resource
    private RedisService redisService;
    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // 明确排除登录路径和其他不需要过滤的路径
        return request.getServletPath().startsWith("/login") ||
                request.getServletPath().startsWith("/loginView");
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        // 1. 从请求头获取Token
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        // 2. 提取并解析Token
        String token = authHeader.substring(7);
        MyUserDetail myUserDetail = null;
        try {
            myUserDetail = JWTUtil.parseUserFromJWT(token);
            if (myUserDetail == null) {
                ResponseUtil.responsePack(response,R.failure("无效的Token格式"));
                return;
            }
        } catch (Exception e) {
            ResponseUtil.responsePack(response,R.failure("Token解析失败: " + e.getMessage()));
            return;
        }

        // 3. 验证Redis中的Token
        String redisToken = redisService.get(myUserDetail.getUsername() + "token");
        if (redisToken == null || !redisToken.equals(token)) {
            ResponseUtil.responsePack(response,R.failure("Token已失效"));
            return;
        }

        // 4. 加载用户详情并设置认证
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(myUserDetail.getUsername());
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (UsernameNotFoundException e) {
            ResponseUtil.responsePack(response,R.failure("用户不存在"));
            return;
        }
        //5.token续期，异步处理
        MyUserDetail finalMyUserDetail = myUserDetail;
        threadPoolTaskExecutor.execute(() -> {
            String rememberMe = request.getHeader("rememberMe");
            if (Boolean.parseBoolean(rememberMe)){
                redisService.saveWithExpire(finalMyUserDetail.getUsername()+"token",token, JWTConstants.TOKEN_EXPIRE, TimeUnit.SECONDS);
                log.info("存储成功");
            }else{
                redisService.saveWithExpire(finalMyUserDetail.getUsername()+"token",token, JWTConstants.TOKEN_EXPIRE / 48, TimeUnit.SECONDS);
                log.info("存储成功");
            }
        });

        chain.doFilter(request, response);
    }
}
