package com.jz.dlyk.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * 验证码生成工具kaptcha的配置累
 */
@Configuration
public class CaptchaConfig {
    /**
     * 配置Producer接口的实现类DefaultKaptcha的Bean对象，该对象用于生成验证码图片；
     * 并给其指定生成的验证码图片的设置项；Bean对象的id引用名为captchaProducer；
     * @return
     */
    @Bean("captchaProducer")
    public DefaultKaptcha getDefaultKaptcha() {
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        // 是否有边框 默认为true 我们可以自己设置 yes，no
        properties.setProperty("kaptcha.border", "no");
        // 边框颜色 默认为Color.BLACK
        properties.setProperty("kaptcha.border.color","105,179,90");
        // 验证码文本字符颜色 默认为Color.BLACK
        properties.setProperty("kaptcha.textproducer.font.color","blue");
        // 验证码图片宽度 默认为200
        properties.setProperty("kaptcha.image.width","120");
        // 验证码图片高度 默认为50
        properties.setProperty("kaptcha.image.height","40");
        // 验证码文本字符大小 默认为40
        properties.setProperty("kaptcha.textproducer.font.size","4");
        // KAPTCHA_SESSION_KEY
        properties.setProperty("kaptcha.session.key","kaptchaCode");
        // 验证码文本字符间距 默认为2
        properties.setProperty("kaptcha.textproducer.char.space","4");
        // 验证码文本字符长度 默认为5
        properties.setProperty("kaptcha.textproducer.char.length","4");
        // 验证码文本字体样式 默认为 new Fount("Arial",1,fontSize)
        properties.setProperty("kaptcha.textproducer.font.names","Arial,Courier");
        // 验证码噪点颜色 默认为Color.BLACK
        properties.setProperty("kaptcha.noise.color","gray");

        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
