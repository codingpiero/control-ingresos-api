package pe.com.condominio.controlingresos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import pe.com.condominio.controlingresos.exception.SecurityExceptionHandler;
import pe.com.condominio.controlingresos.global.security.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final SecurityExceptionHandler securityExceptionHandler;
	
	public SecurityConfig(JwtAuthenticationFilter jwAuthenticationFilter,SecurityExceptionHandler securityExceptionHandler) {
		this.jwtAuthenticationFilter = jwAuthenticationFilter;
		this.securityExceptionHandler = securityExceptionHandler;
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
		.csrf(csrf -> csrf.disable())
		.sessionManagement(session -> 
		      session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		)
		.authorizeHttpRequests(auth -> auth
				.requestMatchers("/api/auth/login").permitAll()
				.requestMatchers(
				   HttpMethod.GET,
				   "/api/ingresos",
				   "/api/ingresos/**"
						).hasAnyRole("ASOCIADO","VIGILANTE")
				.requestMatchers(
						HttpMethod.POST,
						"/api/ingresos",
						"/api/ingresos/**"
						).hasAnyRole("VIGILANTE")
				.requestMatchers(
						HttpMethod.PUT,
						"/api/ingresos",
						"/api/ingresos/**").hasAnyRole("VIGILANTE")
				.requestMatchers(
						HttpMethod.DELETE,
						"/api/ingresos",
						"/api/ingresos/**").hasAnyRole("VIGILANTE")
				.anyRequest().authenticated()
		)
		.exceptionHandling(exception -> exception
				.authenticationEntryPoint(securityExceptionHandler)
				.accessDeniedHandler(securityExceptionHandler))
		.addFilterBefore(
				jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
}
