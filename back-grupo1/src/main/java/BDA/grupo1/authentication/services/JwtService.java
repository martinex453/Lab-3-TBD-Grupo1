package BDA.grupo1.authentication.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${jwt.secret_key}")
    private String SECRET_KEY;
    long TOKEN_EXPIRATION_TIME_SECONDS = 60 * 60 * 24 * 5; // Define la duración del token en segundos (5 días)

    // Método que obtiene el nombre de usuario (subject) del JWT
    public String getUsername(String token) {
        return getClaim(token, Claims::getSubject);
    }

    // Método que obtiene la fecha de expiración del JWT
    public Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    // Método genérico para obtener un "claim" específico del JWT
    public <T> T getClaim(String token, Function<Claims, T> claimsFunction) {
        Claims claims = getAllClaims(token);
        return claimsFunction.apply(claims);
    }

    // Método que obtiene todos los "claims" del JWT
    private Claims getAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Método para obtener la clave de firma, descodificando la clave secreta en formato Base64
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Método que genera un JWT con los detalles del usuario
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    // Método que genera un JWT con claims adicionales
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * TOKEN_EXPIRATION_TIME_SECONDS))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Método para verificar si el token es válido comparando el username y asegurándose de que no haya expirado
    public boolean isValidToken(String token, UserDetails userDetails){
        String username = getUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Método que verifica si el token ha expirado
    public boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }
}

