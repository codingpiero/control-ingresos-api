package pe.com.condominio.controlingresos.global.utils;

import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import pe.com.condominio.controlingresos.modules.auth.repository.model.CustomUserDetailModel;

@Component
public class JwtUtil {
	private final String ISSUER = "control-ingresos";
	private final String secret = "3GDEkJ5gUWobOiidrcvOHXTzazHWRzXw5QqWwycKo7g=";
	private final long expirationMs = 3600000;
	private Algorithm algorithm = Algorithm.HMAC256(secret);

	public String generarToken(CustomUserDetailModel payload) {
		Date ahora = new Date();
		
		List<String> roles = payload.getAuthorities()
				.stream()
				.map(GrantedAuthority::getAuthority)
				.toList();
		
		return JWT.create()
		.withIssuer(ISSUER)
		.withSubject(payload.getUsername())
		.withClaim("id",payload.getId())
		.withClaim("username",payload.getUsername())
		.withClaim("nombres",payload.getNombres())
		.withClaim("roles", roles)
		.withIssuedAt(ahora)
		.withExpiresAt(new Date(ahora.getTime() + expirationMs))
		.sign(algorithm);
		
	}
}
