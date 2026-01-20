package app.demo.util;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;

@Component
@Getter
public class JwtUtil {

    private final Key ACCESS_SECRET = Keys.hmacShaKeyFor(
            "nmaleionfsSeroensf33afdsafpoenfknaoifnsfjonsnfdoie4fnsfnsaiofenkfa"
                    .getBytes());

    private final Key REFRESH_SECRET = Keys.hmacShaKeyFor(
            "nmaleionfsSeroensf33afdsafpoenfknaoifnsfjonsnfdoie4fnsfnsaiofenkfademoASDFEEEapp"
                    .getBytes());

    public String generateToken(String username, String role) {

        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 15 * 60 * 1000))
                .signWith(ACCESS_SECRET, SignatureAlgorithm.HS512)
                .compact();
    }

    public String generateRefreshToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000))
                .signWith(REFRESH_SECRET, SignatureAlgorithm.HS512)
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(ACCESS_SECRET)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String extractEmailFromRefreshToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(REFRESH_SECRET)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Date extractExpiration(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(ACCESS_SECRET)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();

    }

    public boolean isTokenExpired(String token, Key key) {
        try {
            Date exp = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration();

            return exp.before(new Date()); // true = hết hạn
        } catch (JwtException e) {
            return true; // token sai → coi như hết hạn
        }
    }



}
