package com.mk.minishop.server.adapters.security;

import com.mk.minishop.server.auth.Password;
import com.mk.minishop.server.auth.PasswordEncoderVerifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCPasswordEncoderVerifier implements PasswordEncoderVerifier {

    @Override
    public String encode(CharSequence toEncode) {
        return new BCryptPasswordEncoder().encode(toEncode);
    }

    @Override
    public boolean matches(CharSequence rawPass, Password encodedPass) {
        return new BCryptPasswordEncoder().matches(rawPass, encodedPass.asString());
    }
}
