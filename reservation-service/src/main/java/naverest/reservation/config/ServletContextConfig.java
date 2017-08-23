package naverest.reservation.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import naverest.reservation.interceptor.LoginCheckInterceptor;
import naverest.reservation.resolver.ReservationFormArgumentResolver;
import naverest.reservation.resolver.UserArgumentResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { 
		"naverest.reservation.controller",
		"naverest.reservation.restcontroller",
		"naverest.reservation.handler"})

public class ServletContextConfig extends WebMvcConfigurerAdapter {
	@Value("${naverest.imageMaxSize}")
	private Long IMAGE_MAX_SIZE;
	@Value("${naverest.imagePath}")
	private String IMAGE_PATH;
	
	@Autowired
	public ServletContextConfig() {
	}
	
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/"); 
		registry.addResourceHandler("/imgresources/**").addResourceLocations(IMAGE_PATH);
		registry.addResourceHandler("/favicon.ico").addResourceLocations("/favicon.ico");
	}


	@Bean
	LoginCheckInterceptor loginCheckInterceptor() {
		return new LoginCheckInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginCheckInterceptor()).addPathPatterns(
				"/login/refresh",
				"/myreservation/**",
				"/api/myreservation/**",
				"/booking/**",
				"/reviews/form/**",
				"/api/images/**");
	}
	
	@Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new UserArgumentResolver());
        argumentResolvers.add(new ReservationFormArgumentResolver());
    }
	
	@Bean
    public MultipartResolver multipartResolver() {
        org.springframework.web.multipart.commons.CommonsMultipartResolver multipartResolver = new org.springframework.web.multipart.commons.CommonsMultipartResolver();
        multipartResolver.setMaxUploadSizePerFile(IMAGE_MAX_SIZE); // 1024 * 1024  = 1MB;
        return multipartResolver;
    }
}
