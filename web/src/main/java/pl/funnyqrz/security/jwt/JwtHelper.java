package pl.funnyqrz.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;

@Component
public class JwtHelper {

    private String appName;

    private int expiresIn;

    private String authHeader;

    private String secretKey;

    public JwtHelper(@Value("${app.name}") String appName, @Value("${jwt.expires_in}")
                      int expiresIn, @Value("${jwt.header}") String authHeader,  @Value("${jwt.secret_key}")String secretKey) {
        this.appName = appName;
        this.expiresIn = expiresIn;
        this.authHeader = authHeader;
        this.secretKey = secretKey;
    }

    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    private Date getIssuedAtDateFromToken(String token) {
        Date issueAt;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            issueAt = claims.getIssuedAt();
        } catch (Exception e) {
            issueAt = null;
        }
        return issueAt;
    }

    public String generateToken(String username) {
        Date startDate = new Date();
        return Jwts.builder()
                .setIssuer(appName)
                .setSubject(username)
                .setIssuedAt(startDate)
                .setExpiration(generateExpirationDate(startDate))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    private Claims getAllClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private Date generateExpirationDate(Date startDate) {
        return new Date(startDate.getTime() + expiresIn);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        final Date createdDate = getIssuedAtDateFromToken(token);
        final Date expirationDate = getExpirationToken(token);
        return validateUser(userDetails, username) && validateTokenDate(createdDate, expirationDate);
    }

    private boolean validateUser(UserDetails userDetails, String username) {
        return username != null && username.equals(userDetails.getUsername());
    }

    private boolean validateTokenDate(Date createdDate, Date expirationDate) {
        Date now = Calendar.getInstance().getTime();
        return createdDate.before(now) && now.before(expirationDate);

    }

    public String getToken(HttpServletRequest request) {

        String authHeader = getAuthHeaderFromHeader(request);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        return null;
    }

    public String getAuthHeaderFromHeader(HttpServletRequest request) {
        return request.getHeader(authHeader);
    }

    private Date getExpirationToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return claims.getExpiration();
    }

    public int getExpiresIn() {
        return expiresIn;
    }
}