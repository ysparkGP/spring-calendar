package org.example.calendarproject.core.util;

public interface Encryptor {
    String encrypt(String origin);
    boolean isMatch(String origin, String hashed);
}
