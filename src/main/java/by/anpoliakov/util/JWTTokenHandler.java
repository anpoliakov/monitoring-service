package by.anpoliakov.util;

import by.anpoliakov.domain.dto.UserDto;
import by.anpoliakov.repository.AuditLogRepository;
import by.anpoliakov.repository.impl.AuditLogRepositoryImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;

import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * Class for creating, validating token and getting information from token
 */
public class JWTTokenHandler {
    private static JWTTokenHandler instance;
    private String jwtSecret;
    private String jwtIssuer;
    private String jwUser;
    private String jwtSystem;

    private JWTTokenHandler() {
        jwtSecret = PropertiesLoader.getProperty("jwt.secret");
        jwtIssuer = PropertiesLoader.getProperty("jwt.issuer");
        jwUser = PropertiesLoader.getProperty("jwt.user");
        jwtSystem = PropertiesLoader.getProperty("jwt.system");
    }

    public static JWTTokenHandler getInstance() {
        if (instance == null) {
            instance = new JWTTokenHandler();
        }
        return instance;
    }

    /**
     * Define a field with a type {@link ObjectMapper} for further aggregation
     */
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Generate token by {@link UserDto}
     * @param dto and put it into token
     * @return token
     */
    public String generateUserAccessToken(UserDto dto) {
        return generateUserAccessToken(dto, dto.getLogin());
    }

    /**
     * Generate token by {@link UserDto} and name of user
     * @param dto and put it into token
     * @param name and put it into token
     * @return token
     */
    public String generateUserAccessToken(UserDto dto, String name) {
        return Jwts.builder()
                .claim(jwUser, dto)
                .setSubject(name)
                .setIssuer(jwtIssuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7)))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    /**
     * Generate token by system for system
     * @param name should be "system"
     * @return token
     */
    public String generateSystemAccessToken(String name) {
        return Jwts.builder()
                .setSubject(name)
                .setIssuer(jwtIssuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7)))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    /**
     * Extract claim
     * @param token with claims
     * @param claimsResolver help to extract claim
     * @return all claims
     * @param <T> generated type for Function
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extract all claims from token
     * @param token with claims
     * @return claims from token
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Give name of user from token
     * @param token has claims with name of user
     * @return name of user
     */
    public String getUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Ginve {@link UserDto} from token
     * @param token has claims with field of {@link UserDto}
     * @return {@link UserDto}
     */
    public UserDto getUser(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return objectMapper.convertValue(claims.get(jwUser), UserDto.class);
    }

    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            ex.getMessage();
        } catch (MalformedJwtException ex) {
            ex.getMessage();
        } catch (ExpiredJwtException ex) {
            ex.getMessage();
        } catch (UnsupportedJwtException ex) {
            ex.getMessage();
        } catch (IllegalArgumentException ex) {
            ex.getMessage();
        }

        return false;
    }
}
