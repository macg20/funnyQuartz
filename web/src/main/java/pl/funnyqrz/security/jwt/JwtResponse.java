package pl.funnyqrz.security.jwt;

public class JwtResponse {

   private long expirationTime;
   private String accessToken;

    public JwtResponse(long expirationTime, String token) {
        this.expirationTime = expirationTime;
        this.accessToken = token;
    }

    public JwtResponse() {
    }

    public long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(long expirationTime) {
        this.expirationTime = expirationTime;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
