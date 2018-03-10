package pl.funnyqrz.security.jwt;

public class JwtResponse {

   private long expirationTime;
   private String token;

    public JwtResponse(long expirationTime, String token) {
        this.expirationTime = expirationTime;
        this.token = token;
    }

    public JwtResponse() {
    }

    public long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(long expirationTime) {
        this.expirationTime = expirationTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
