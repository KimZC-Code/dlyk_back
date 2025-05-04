package com.jz.dlyk.controller;

import com.google.code.kaptcha.Producer;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

@RestController
@Slf4j
@RequestMapping("/captcha")
public class VerificationCodeController {
    @Resource(name = "captchaProducer")
    private Producer producer;
    // 注入redis模版
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/captchaImage")
    public void getKaptchaImage(HttpServletResponse response) {
        ServletOutputStream out = null;
        try {
            // 禁止浏览器缓存验证码图片的响应头
            response.setDateHeader("Expires", 0);
            response.setHeader("Cache-Control", "no-store,no-cache,must-revalidate");
            response.addHeader("Cache-Control", "post-check=0,pre-check=0");
            response.setContentType("image/jpeg");
            String code = producer.createText();
            BufferedImage image = producer.createImage(code);
            stringRedisTemplate.opsForValue().set("code", code);
            out = response.getOutputStream();
            ImageIO.write(image, "jpg", out);
            out.flush();
        }catch (Exception e) {
            log.error(e.getMessage());
        }finally {
            try {
                if (out != null) {
                    out.close();
                }
            }catch (Exception e) {
                log.error(e.getMessage());
            }

        }
    }
}
