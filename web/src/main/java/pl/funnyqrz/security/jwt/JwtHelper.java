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

    @Value("${app.name}")
    private String APP_NAME;

    @Value("${jwt.expires_in}")
    private int EXPIRES_IN;

    @Value("${jwt.header}")
    private String AUTH_HEADER;

    @Value("${jwt.secret_key}")
    private String SECRET_KEY;

    private SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

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
                .setIssuer(APP_NAME)
                .setSubject(username)
                .setIssuedAt(startDate)
                .setExpiration(generateExpirationDate(startDate))
                .signWith(SIGNATURE_ALGORITHM, SECRET_KEY)
                .compact();
    }

    private Claims getAllClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private Date generateExpirationDate(Date startDate) {
        return new Date(startDate.getTime() + EXPIRES_IN);
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
        return request.getHeader("Bearer");
    }

    private Date getExpirationToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return claims.getExpiration();
    }
}