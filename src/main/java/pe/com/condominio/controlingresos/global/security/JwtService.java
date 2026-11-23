package pe.com.condominio.controlingresos.global.security;



import java.time.Instant;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import pe.com.condominio.controlingresos.dto.AuthUser;

@Service
public class JwtService {
	private final SecretKey secretKey;
	private final long expirationMs;
	public JwtService(
			@Value("${security.jwt.secret}") String secret,
			@Value("${security.jwt.expiration-ms}") long expirationMS
			){
		this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
		this.expirationMs = expirationMS;
	}
	public String generateToken(AuthUser user) {
		Instant now = Instant.now();
		return Jwts.builder()
				.subject(user.username())
				.claim("userId", user.id())
				.claim("nombres", user.nombres())
				.claim("apellidos", user.apellidos())
				.claim("roles", user.roles())
			    .issuedAt(Date.from(now))
			    .expiration(Date.from(now.plusMillis(expirationMs)))
			    .signWith(secretKey)
			    .compact();
		
	}

	public Claims extractClaims(String token) {
		return Jwts.parser()
				.verifyWith(secretKey)
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
	public String extractUsername(String token) {
		return extractClaims(token).getSubject();
	}
	public boolean isTokenValid(String token) {
		try {
			Claims claims = extractClaims(token);
			return claims.getExpiration().after(new Date());
		} catch (Exception e) {
			return false;
		}
	}
}
