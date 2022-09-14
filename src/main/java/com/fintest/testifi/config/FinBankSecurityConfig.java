package com.fintest.testifi.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class FinBankSecurityConfig {
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((authz) -> {
			authz
			.anyRequest().authenticated();
		})
		.httpBasic(withDefaults());
		
		return http.build();
	}
	
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().antMatchers("/unsecured", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**", "/fintest-api/**");
	}
	
	@Bean
	public InMemoryUserDetailsManager users() {
		String passwordHash = passwordEncoder.encode("password");
		UserDetails user = User.builder()
				.username("user")
				.password(passwordHash)
				.roles("ADMIN", "USER")
				.build();
		
		return new InMemoryUserDetailsManager(user);
	}
	

}
