package by.anpoliakov.util;

import by.anpoliakov.exception.TokenNotExist;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

import javax.crypto.SecretKey;
import java.util.Date;

public final class JwtTokenManager {
    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);

    public static String generateToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(SECRET_KEY)
                .compact();
    }

    public static Long validateToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            try {
                Jws<Claims> claimsJws = Jwts.parserBuilder()
                        .setSigningKey(SECRET_KEY)
                        .build()
                        .parseClaimsJws(token);

                return Long.valueOf(claimsJws.getBody().getSubject());
            } catch (Exception e) {
                throw new TokenNotExist("Token not exist!");
            }
        }

        throw new TokenNotExist("Token not exist!");
    }
}
