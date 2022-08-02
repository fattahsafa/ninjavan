package co.ninja.qa.api.beans;

public class AuthenticationResponse {
    private String access_token;
    private long expires;

    public AuthenticationResponse(String access_token, long expires) {
        this.access_token = access_token;
        this.expires = expires;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public long getExpires() {
        return expires;
    }

    public void setExpires(long expires) {
        this.expires = expires;
    }



}
