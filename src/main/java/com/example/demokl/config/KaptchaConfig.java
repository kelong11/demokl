package com.example.demokl.config;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

//生成随机数字图形验证码配置类
@Configuration//定义ioc容器
public class KaptchaConfig {
    @Bean
    public Producer kaptchaProducer(){
        Properties properties=new Properties();
        properties.setProperty("kaptcha.image.width","100");
        properties.setProperty("kaptcha.image.height","45");
        properties.setProperty("kaptcha.textproducer.font.size","32");
        properties.setProperty("kaptcha.textproducer.font.color","0,0,0");
        properties.setProperty("kaptcha.textproducer.char.String","0123456789ABCDEFGHIJKLMNOPQRSTUVWSYZ");
        properties.setProperty("kaptcha.textproducer.char.length","4");
        //配置图片干扰
        properties.setProperty("kaptcha.noise.impl","com.google.code.kaptcha.impl.NoNoise");

        DefaultKaptcha kaptcha=new DefaultKaptcha();
        Config config =new Config(properties);
        kaptcha.setConfig(config);
        return kaptcha;
    }
}
