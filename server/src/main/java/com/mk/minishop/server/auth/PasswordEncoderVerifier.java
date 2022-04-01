package com.mk.minishop.server.auth;

public interface PasswordEncoderVerifier {

    String encode(CharSequence toEncode);

    boolean matches(CharSequence rawPass, Password encodedPass);

}
