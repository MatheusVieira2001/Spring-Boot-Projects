package com.microservico.matheus.security;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthenticationFilter  extends OncePerRequestFilter {
	//value da key
		 private static final String API_KEY = "lXoztHM7pla0RaUuM0a9DoCxeKBNhqZI647fbef9bde2d";
		 
		 //key
		 private static final String AUTH_HEADER = "TOKEN-API";

		    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		        String apiKey = request.getHeader(AUTH_HEADER);

		        if (apiKey == null || apiKey.isEmpty() || !isValidApiKey(apiKey)) {
		            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		            return;
		        }

		        filterChain.doFilter(request, response);
		    }

		    private boolean isValidApiKey(String apiKey) {
		        return API_KEY.equals(apiKey);
		    }
   
}