package ru.trofimov.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

@Configuration
@ComponentScan("ru.trofimov.controllers")
@EnableWebMvc
public class SpringConfig implements WebMvcConfigurer {

    private final ApplicationContext applicationContext;

    @Autowired
    public SpringConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/views/");
//        templateResolver.setPrefix("classpath:/views/"); из ресурсов
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        resolver.setCharacterEncoding("UTF-8");
        registry.viewResolver(resolver);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**").addResourceLocations("classpath:/img/");
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/js/");
//        registry.addResourceHandler("/img/**").addResourceLocations("/img/");
//        registry.addResourceHandler("images/**").addResourceLocations("classpath:/static/images/");
//        registry.addResourceHandler("images/**").addResourceLocations("/WEB-INF/img/");
//        registry.addResourceHandler("css/**").addResourceLocations("/WEB-INF/css/");
        registry.addResourceHandler("/images/**").addResourceLocations("/WEB-INF/images/");
//        registry.addResourceHandler("/upload/**").addResourceLocations("file://" + System.getProperty("user.dir") + "/src/main/upload/");
//        registry.addResourceHandler("/upload/**").addResourceLocations(System.getProperty("user.dir") + "\\");
        registry.addResourceHandler("/upload/**").addResourceLocations("/upload/");
//        registry.addResourceHandler("/img/**").addResourceLocations("file:/tmp/images/").setCacheControl(CacheControl.noCache());
    }

//    New
    @Bean("multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(5242880);//5MB
        return multipartResolver;
    }

//    @Bean
//    public StandardServletMultipartResolver standardServletMultipartResolver() {
//        return new StandardServletMultipartResolver();
//    }

//

}