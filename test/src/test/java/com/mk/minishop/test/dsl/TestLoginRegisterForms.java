package com.mk.minishop.test.dsl;

import com.mk.minishop.api.auth.LoginRegisterFormDto;

public class TestLoginRegisterForms {

    private TestLoginRegisterForms() {
    }

    public static LoginRegisterFormDto VALID_LOGIN_REGISTER_FORM = new LoginRegisterFormDto("Valid User", "qwer1234");
    public static LoginRegisterFormDto LOGIN_FORM_INVALID_LOGIN = new LoginRegisterFormDto("Invalid Login", "qwer1234");
    public static LoginRegisterFormDto LOGIN_FORM_INVALID_PASS= new LoginRegisterFormDto("Valid User", "xxxxxxxxxxxxxx");
    public static LoginRegisterFormDto REGISTER_FORM_PASS_INVALID_FORMAT = new LoginRegisterFormDto("Invalid User", "qw");
}
