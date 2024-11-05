package com.whatsapp.backend.config;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class JWTConstants {
    // Replace this secret key with your own randomly generated key
    static String secretKeyString = "1A2B3C4D5E6F7G8H9I0J1K2L3M4N5O6P";

    // Convert the string to a byte array
    static byte[] decodedKey = secretKeyString.getBytes();

    // Create a SecretKey object using HMAC SHA-256 algorithm
    static SecretKey SECRET_KEY = new SecretKeySpec(decodedKey, "HmacSHA256");
}
