package pe.com.condominio.controlingresos.global.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import pe.com.condominio.controlingresos.global.utils.JwtUtil;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	private final JwtUtil jwtUtil;
	private final JwtAuthenticationEntryPoint entryPoint;
	private final JwtAccessDeniedHandler accessDeniedHandler;
	
	public SecurityConfig(JwtUtil jwtUtil,
			JwtAuthenticationEntryPoint entryPoint,JwtAccessDeniedHandler accDeniedHandler) {
		this.jwtUtil = jwtUtil;
		this.entryPoint = entryPoint;
		this.accessDeniedHandler = accDeniedHandler;
	}
	@Bean
	public SecurityFilterChain securityFilterChain(
			HttpSecurity http,
			AuthenticationProvider authenticationProvider) throws Exception{
		http
		.csrf(csrf -> csrf.disable())
		.sessionManagement(session -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.authorizeHttpRequests(auth -> auth
				.requestMatchers(HttpMethod.POST,"/api/v1/auth/**").permitAll()
				.requestMatchers(HttpMethod.DELETE,"/**").denyAll()
				.requestMatchers(HttpMethod.GET,"/api/v1/ingresos/**").hasAnyRole("ASOCIADO","VIGILANTE")
				.requestMatchers(HttpMethod.POST,"/api/v1/ingresos/**").hasRole("VIGILANTE")
				.requestMatchers(HttpMethod.PUT,"/api/v1/ingresos/**").hasRole("VIGILANTE")
				.requestMatchers(HttpMethod.PATCH,"/api/v1/ingresos/**").hasRole("VIGILANTE")
				.requestMatchers(HttpMethod.GET,"/api/v1/visitas/**").hasRole("VISITA")
				.requestMatchers(HttpMethod.POST,"/api/v1/visitas/**").hasRole("VISITA")
				.requestMatchers(HttpMethod.PUT,"/api/v1/visitas/**").hasRole("VISITA")
				.requestMatchers(HttpMethod.PATCH,"/api/v1/visitas/**").hasRole("VISITA")
				.anyRequest().authenticated())
		.exceptionHandling(ex -> ex
				.authenticationEntryPoint(entryPoint)
				.accessDeniedHandler(accessDeniedHandler))
		.authenticationProvider(authenticationProvider)
		.addFilterBefore(new JwtFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService,PasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder);
		return provider;
	}
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
		
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
