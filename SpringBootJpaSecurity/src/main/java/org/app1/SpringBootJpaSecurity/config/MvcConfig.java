package org.app1.SpringBootJpaSecurity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/assets/css/");
        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/static/assets/images/");
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/assets/js/");
        registry.addResourceHandler("/person-storage/**")
                .addResourceLocations("classpath:/storage/person-images/");
    }
}
