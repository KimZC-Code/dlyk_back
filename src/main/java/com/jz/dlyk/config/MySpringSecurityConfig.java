package com.jz.dlyk.config;

import com.jz.dlyk.constants.JWTConstants;
import com.jz.dlyk.dto.R;
import com.jz.dlyk.entity.MyUserDetail;
import com.jz.dlyk.filter.JwtAuthenticationFilter;
import com.jz.dlyk.service.RedisService;
import com.jz.dlyk.utils.JWTUtil;
import com.jz.dlyk.utils.ResponseUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
@EnableWebSecurity
public class MySpringSecurityConfig {
    @Resource
    private RedisService redisService;
    @Resource
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/loginView","/api/login","/error").permitAll()
                                .requestMatchers("/user/*").hasRole("ADMIN")
                                .anyRequest().authenticated()
                ).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

                .exceptionHandling(exception ->{
                    exception.accessDeniedHandler((request, response, accessDeniedException) -> {
                        log.error("无权限");
                        ResponseUtil.responsePack(response,R.failure());
                    });
                });
        http
                .formLogin(form ->
                        form.loginPage("/loginView").permitAll()
                                .loginProcessingUrl("/login").permitAll()
                                .usernameParameter("username").passwordParameter("password")
                                .successHandler((request, response, authentication) -> {
                                    MyUserDetail login = (MyUserDetail) authentication.getPrincipal();

                                    String token = JWTUtil.generateToken(login);
                                    String rememberMe = request.getParameter("rememberMe");
                                    boolean remember = Boolean.parseBoolean(rememberMe);
                                    if (remember){
                                        redisService.saveWithExpire(login.getUsername()+"token",token, JWTConstants.TOKEN_EXPIRE, TimeUnit.SECONDS);
                                        log.info("存储成功");
                                    }else{
                                        redisService.saveWithExpire(login.getUsername()+"token",token, JWTConstants.TOKEN_EXPIRE / 48, TimeUnit.SECONDS);
                                        log.info("存储成功");
                                    }
                                    ResponseUtil.responsePack(response,R.success(token));
                                })
                                .failureHandler((request, response, exception) -> {
                                    ResponseUtil.responsePack(response,R.failure("登录失败"));
                                })
                );
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173")); // 允许所有源，生产环境应限制
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
