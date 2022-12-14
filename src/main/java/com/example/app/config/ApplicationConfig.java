package com.example.app.config;



import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.app.filter.AuthFilter;

@Configuration
public class ApplicationConfig implements WebMvcConfigurer{

	@Override
	public Validator getValidator() {
		var validator = new LocalValidatorFactoryBean();
		validator.setValidationMessageSource(messageSource());
		return validator;
	}

	@Bean
	public ResourceBundleMessageSource messageSource() {
		var messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("validation");
		return messageSource;
	}

//AuthFilter.javaの有効化
	@Bean
	 public FilterRegistrationBean<AuthFilter> authFilter() {
	 var bean = new FilterRegistrationBean<AuthFilter>(new AuthFilter());
	 bean.addUrlPatterns("/ticketInventory/*");
	 return bean;
	}

//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler("/uploads/**")
//		.addResourceLocations("file:///c:/Users/zd1K30/uploads/");
//	}

}
