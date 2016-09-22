package com.bootcamp.portal.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.concurrent.ScheduledExecutorFactoryBean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.WebContentInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.bootcamp.portal.AppConfig;
import com.bootcamp.portal.web.interceptor.AuthInterceptor;

@Configuration
@EnableWebMvc
@Import(value = { AppConfig.class })
@ComponentScan(basePackageClasses = WebConfig.class)
public class WebConfig extends WebMvcConfigurerAdapter {
	protected static final Logger LOGGER = Logger.getLogger(WebConfig.class);
	public static final String USER_KEY = "authenticatedUser";
	private static final long MAX_UPLOAD_SIZE = new Long(1024 * 1024 * 1024);

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/r/**").addResourceLocations("/r/");
		registry.addResourceHandler("/angular-resources/**")
				.addResourceLocations("/angular-resources/");
		registry.addResourceHandler("/favicon.ico").addResourceLocations("/r/");
	}

	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}

	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(MAX_UPLOAD_SIZE);
		return multipartResolver;
	}

	@Bean
	public ScheduledExecutorFactoryBean scheduledExecutorFactoryBean() {
		ScheduledExecutorFactoryBean scheduledFactoryBean = new ScheduledExecutorFactoryBean();
		return scheduledFactoryBean;
	}

	// TODO maybe it will be needed
	// @Bean
	// public ScheduledExecutorTask taskSheduler() {
	// ScheduledExecutorTask t = new ScheduledExecutorTask();
	// t.setRunnable(new TaskExecutorHelper());
	// t.setDelay(5 * 60 * 1000); // 5 min
	// t.setPeriod(86400000); // 24 Hours = 86400000 Milliseconds
	// return t;
	// }

	private WebContentInterceptor getWebContentInterceptor() {
		WebContentInterceptor w = new WebContentInterceptor();
		w.setUseCacheControlNoStore(true);
		w.setCacheSeconds(0);
		w.setUseExpiresHeader(true);
		w.setUseCacheControlHeader(true);
		return w;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new AuthInterceptor());
		registry.addInterceptor(getWebContentInterceptor());
		super.addInterceptors(registry);
	}

	static public AuthenticatedUser getCurrentUser(HttpSession session) {
		if (session == null) {
			LOGGER.error("Session is null!");
			return null;
		}
		LOGGER.info("*** getCurrentUser *** " + session.getAttribute(USER_KEY));
		LOGGER.info("*** session *** " + session);
		return (AuthenticatedUser) session.getAttribute(USER_KEY);
	}

	static public void setCurrentUser(HttpSession session,
			AuthenticatedUser user) {
		LOGGER.info("*** setCurrentUser *** " + session.getAttribute(USER_KEY));
		session.setAttribute(USER_KEY, user);
		LOGGER.info("*** setCurrentUser *** " + session.getAttribute(USER_KEY));
		LOGGER.info("*** session *** " + session);
	}

	static public void logout(HttpServletRequest request,
			HttpServletResponse response) {
		request.getSession(true).removeAttribute(USER_KEY);
	}
}