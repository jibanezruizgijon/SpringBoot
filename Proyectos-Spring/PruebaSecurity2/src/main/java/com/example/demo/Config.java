package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class Config {
	@Bean
	public UserDetailsService userDetailsService() {
		var userDetailsService = new InMemoryUserDetailsManager();
		UserDetails user1 = User.builder().username("admin").password(this.passwordEncoder().encode("admin"))
				.authorities("write").build();
		UserDetails user2 = User.builder().username("Jonathan").password(this.passwordEncoder().encode("4321"))
				.authorities("write").build();
		userDetailsService.createUser(user1);
		userDetailsService.createUser(user2);
		return userDetailsService;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
