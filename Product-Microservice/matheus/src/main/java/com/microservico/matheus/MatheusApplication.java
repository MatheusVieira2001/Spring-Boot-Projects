package com.microservico.matheus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.microservico.matheus.security.*;


@SpringBootApplication
public class MatheusApplication {

	public static void main(String[] args) {
		SpringApplication.run(MatheusApplication.class, args);
	}
	
	@Bean
    public FilterRegistrationBean<AuthenticationFilter> authenticationFilterRegistrationBean() {
        FilterRegistrationBean<AuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AuthenticationFilter());
        registrationBean.addUrlPatterns("/Product/*");
        return registrationBean;
    }

}
