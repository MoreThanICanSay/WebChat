package com.wkb.chat.config;

import com.wkb.chat.util.FileUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.io.File;


/**
 * @author WangKaiBo
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    public static final String separator = File.separator;

    public static final String CLASSPATH = FileUtil.getUplodFilePath();

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/index");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        super.addViewControllers(registry);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //和页面有关的静态目录都放在项目的static目录下
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        //上传文件真实目录配置
        registry.addResourceHandler("/**").addResourceLocations("file:" + CLASSPATH);
    }
}