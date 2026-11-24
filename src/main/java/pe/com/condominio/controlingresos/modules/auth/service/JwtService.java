package pe.com.condominio.controlingresos.modules.auth.service;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import pe.com.condominio.controlingresos.modules.auth.model.AuthUser;

@Service
public class JwtService {
	private final SecretKey secretKey;
	private final long expirationMs;
	public JwtService(
			@Value("${security.jwt.secret}") String secret,
			@Value("${security.jwt.expiration-ms}") long expirationMs) 
	{
		this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
		this.expirationMs = expirationMs;
	}
	
	public String generateAccessToken(AuthUser user) {
		Date now = new Date();
		Date expiration = new Date(now.getTime()+expirationMs);
		return Jwts.builder()
				.issuer("control-ingresos")
				.subject(user.getUsername())
				.claim("userId",user.getId())
				.claim("roles", user.getRoles())
				.issuedAt(now)
				.expiration(expiration)
				.signWith(secretKey,Jwts.SIG.HS256)
				.compact();
	}
	public long getExpirationSeconds() {
		return expirationMs / 1000;
	}
	public boolean isTokenValid(String token) {
		try {
			Jwts.parser()
			.verifyWith(secretKey)
			.build()
			.parseSignedClaims(token);
			return true;
		}catch (JwtException | IllegalArgumentException exception) {
			return false;
		} 
		catch (Exception e) {
		  return false;
		}
	}
	
	public String extractUsername(String token) {
		return Jwts.parser()
				.verifyWith(secretKey)
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.getSubject();
	}
	
	
}
