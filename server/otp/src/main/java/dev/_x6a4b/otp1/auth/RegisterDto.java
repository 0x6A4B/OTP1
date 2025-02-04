package dev._x6a4b.otp1.auth;

public class RegisterDto {

    private String username;
    private String password;

    public RegisterDto(){}
    public RegisterDto(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername(){ return username; }
    public String getPassword() { return password; }

    public void setUsername(String username){ this.username = username; }
    public void setPassword(String password) { this.password = password; }


}
