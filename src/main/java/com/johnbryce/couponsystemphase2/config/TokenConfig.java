package com.johnbryce.couponsystemphase2.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.johnbryce.couponsystemphase2.Security.UserData;

@Configuration
public class TokenConfig {
	
	@Bean
	public Map<String, UserData> map(){
		return new HashMap<>();
	}
}
