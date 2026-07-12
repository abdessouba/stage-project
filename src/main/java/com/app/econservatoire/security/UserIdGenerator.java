package com.app.econservatoire.security;

import java.security.SecureRandom;

public class UserIdGenerator {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int LENGTH = 8;
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generate() {
        StringBuilder id = new StringBuilder(LENGTH);

        for (int i = 0; i < LENGTH; i++) {
            id.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }

        return id.toString();
    }
}
