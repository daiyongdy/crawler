package com.crawler.configuration;

import com.crawler.interceptor.ApiInceptor;
import com.crawler.interceptor.SignInceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfiguration extends WebMvcConfigurerAdapter {

	@Bean
	public SignInceptor signInceptor() {
		return new SignInceptor();
	}

	/**
	 * 配置拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//校验用户登录
		registry.addInterceptor(new ApiInceptor())
				.addPathPatterns("/api/**");

		//验证签名
//		registry.addInterceptor(signInceptor())
//				.addPathPatterns("/api/p/report"); //客户数据上报
	}

}
