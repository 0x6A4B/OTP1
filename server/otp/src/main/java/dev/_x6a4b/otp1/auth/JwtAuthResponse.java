package dev._x6a4b.otp1.auth;

public class JwtAuthResponse {

    private Long id;
    private String accessToken;
    private String tokenType = "Bearer";    // do we need a setter or could we use final?

    public JwtAuthResponse(){}
    public JwtAuthResponse(String accessToken, String tokenType){
        this.accessToken = accessToken;
        this.tokenType = tokenType;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAccessToken(){ return accessToken; }
    public String getTokenType() { return tokenType; }

    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
    public void setTokenType(String tokenType) { this.tokenType = tokenType; }
}
