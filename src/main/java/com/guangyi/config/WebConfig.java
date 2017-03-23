package com.guangyi.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.accept.ContentNegotiationManagerFactoryBean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.dialect.SpringStandardDialect;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by zhongjing on 5/11/16.
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
        converters.add(stringHttpMessageConverter());
        restTemplate.setMessageConverters(converters);
        return restTemplate;
    }

    public StringHttpMessageConverter stringHttpMessageConverter(){
        return new StringHttpMessageConverter(Charset.forName("UTF-8"));
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(stringHttpMessageConverter());
        super.configureMessageConverters(converters);
    }

    @Bean
    public TemplateResolver templateResolver() {
        ServletContextTemplateResolver resolver = new ServletContextTemplateResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        resolver.setCharacterEncoding("UTF-8");
        resolver.setCacheable(false);
        return resolver;
    }



    @Bean
    public SpringTemplateEngine templateEngine() {
        Set<IDialect> additionalDialects = new HashSet<IDialect>();
        additionalDialects.add(new SpringStandardDialect());
        additionalDialects.add(new ShiroDialect());
        additionalDialects.add(new LayoutDialect());
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(templateResolver());
        engine.setAdditionalDialects(additionalDialects);
        return engine;
    }

    @Bean
    public ContentNegotiationManager contentNegotiationManager() {
        ContentNegotiationManagerFactoryBean contentNegotiationManager = new ContentNegotiationManagerFactoryBean();
        Properties mediaTypes = new Properties();
        mediaTypes.put("html", MediaType.TEXT_HTML_VALUE);
        contentNegotiationManager.setMediaTypes(mediaTypes);
        contentNegotiationManager.setDefaultContentType(MediaType.TEXT_HTML);
        contentNegotiationManager.setIgnoreAcceptHeader(true);
        return contentNegotiationManager.getObject();
    }

    @Bean
    public ViewResolver contentNegotiatingViewResolver(
            ContentNegotiationManager contentNegotiationManager,
            SpringTemplateEngine templateEngine
    ) {
        ContentNegotiatingViewResolver viewResolver = new ContentNegotiatingViewResolver();
        viewResolver.setOrder(1);
        viewResolver.setContentNegotiationManager(contentNegotiationManager);

        List<ViewResolver> viewResolvers = new ArrayList<ViewResolver>();
        ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
        thymeleafViewResolver.setTemplateEngine(templateEngine);
        thymeleafViewResolver.setCharacterEncoding("UTF-8");
        viewResolvers.add(thymeleafViewResolver);
        viewResolver.setViewResolvers(viewResolvers);

        return viewResolver;
    }

    @Bean
    public FormattingConversionServiceFactoryBean conversionService() {
        FormattingConversionServiceFactoryBean conversionService = new FormattingConversionServiceFactoryBean();
        Set<org.springframework.format.Formatter> formatters = new HashSet<>();
        formatters.add(new DateFormatter());
        conversionService.setFormatters(formatters);
        return conversionService;
    }
}
