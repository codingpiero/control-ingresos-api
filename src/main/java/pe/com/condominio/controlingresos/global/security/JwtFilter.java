package pe.com.condominio.controlingresos.global.security;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pe.com.condominio.controlingresos.global.security.dto.UsuarioAutenticado;
import pe.com.condominio.controlingresos.global.utils.JwtUtil;

public class JwtFilter extends  OncePerRequestFilter{
	private JwtUtil jwtUtil;
	
	public JwtFilter(JwtUtil jwtUtil) {
		super();
		this.jwtUtil = jwtUtil;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		
		if(authHeader == null || authHeader.isEmpty() || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		String jwt = authHeader.split(" ")[1].trim();
		
		try {
			DecodedJWT decoded = jwtUtil.decodedJwt(jwt);
			String username =  jwtUtil.getUsername(decoded);
			Long id = jwtUtil.getId(decoded);
			List<SimpleGrantedAuthority> authorities = jwtUtil.getRoles(decoded)
					.stream()
					.map(SimpleGrantedAuthority::new)
					.toList();
			if(SecurityContextHolder.getContext().getAuthentication() == null) {
				UsuarioAutenticado principal = new UsuarioAutenticado(id,username);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(principal,null,authorities);
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
			}
		}catch (TokenExpiredException e) {
			request.setAttribute("jwt_error", "TOKEN_EXPIRED");
			SecurityContextHolder.clearContext();
		} 
		catch (JWTVerificationException e) {
			SecurityContextHolder.clearContext();
		}
		filterChain.doFilter(request, response);
		
		
	}

}
