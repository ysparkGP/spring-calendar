package org.example.calendarproject.core;

import org.example.calendarproject.core.doamin.entity.User;
import org.example.calendarproject.core.util.Encryptor;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

    private final Encryptor alwaysMatchEncryptor = new Encryptor() {
        @Override
        public String encrypt(String origin) {
            return origin;
        }

        @Override
        public boolean isMatch(String origin, String hashed) {
            return true;
        }
    };

    @Test
    void isMatchTest(){
        final User me = new User("me", "email", "pw", LocalDate.now());
        assertEquals(true,me.isMatch(alwaysMatchEncryptor, "aa"));
    }
}
