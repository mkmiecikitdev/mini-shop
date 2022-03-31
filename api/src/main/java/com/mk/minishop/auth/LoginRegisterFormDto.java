package com.mk.minishop.auth;

public class LoginRegisterFormDto {

    private String login;

    private String password;

    public LoginRegisterFormDto(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public LoginRegisterFormDto() {
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
