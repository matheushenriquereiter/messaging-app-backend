package org.example.messagingapp.util;

public class BearerTokenUtil {
    private BearerTokenUtil() {
    }

    public static String extractToken(String bearerToken) {
        return bearerToken.replace("Bearer ", "");
    }
}
