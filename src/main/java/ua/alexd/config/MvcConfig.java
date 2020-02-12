package ua.alexd.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(@NotNull ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/favicon.ico").addResourceLocations("classpath:/static/public/images/favicon.ico");
        registry.addResourceHandler("/images/**").addResourceLocations("classpath:/static/public/images/");
        registry.addResourceHandler("/styles/**").addResourceLocations("classpath:/static/public/styles/");
        registry.addResourceHandler("/scripts/**").addResourceLocations("classpath:/static/public/scripts/");
    }
}