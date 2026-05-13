package ir.aspireapps.authservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import ir.aspireapps.authservice.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	public final CustomUserDetailsService customUserDetailsService;
	private final PasswordEncoder passwordEncoder;
	
	@Value("${security.rememeberme.key}")
	private String rememberMeKey;
	
	@Bean
	AuthenticationManager authenticationManager(HttpSecurity http) {
		AuthenticationManagerBuilder builder = 
				http.getSharedObject(AuthenticationManagerBuilder.class);
		
		builder
			.userDetailsService(customUserDetailsService)
			.passwordEncoder(passwordEncoder);
		
		return builder.build();
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) {
		http
			.authorizeHttpRequests(auth -> auth
					.requestMatchers("/api/auth/register",
									 "/api/auth/login").permitAll()
					.anyRequest().authenticated()
					)
			.rememberMe(r -> r
					.key(rememberMeKey)
					.tokenValiditySeconds(60 * 60 * 24 *7)
					.userDetailsService(customUserDetailsService));
		
		return http.build();
	}
}
