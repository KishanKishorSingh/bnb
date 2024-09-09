package com.airbnb;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class AdminPass {
    public static void main(String[] args) {
        String pass = BCrypt.hashpw("Test", BCrypt.gensalt(10));
        System.out.println(pass);
    }
}
