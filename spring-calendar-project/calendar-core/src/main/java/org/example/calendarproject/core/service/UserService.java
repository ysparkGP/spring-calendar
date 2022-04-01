package org.example.calendarproject.core.service;

import lombok.RequiredArgsConstructor;
import org.example.calendarproject.core.doamin.entity.User;
import org.example.calendarproject.core.doamin.entity.repository.UserRepository;
import org.example.calendarproject.core.dto.UserCreateReq;
import org.example.calendarproject.core.util.Encryptor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final Encryptor encryptor;
    private final UserRepository userRepository;

    @Transactional
    public User create(UserCreateReq userCreateReq) {

        userRepository.findByEmail(userCreateReq.getEmail())
                .ifPresent(action ->
                {
                    throw new RuntimeException("user already existed!");
                });

        return userRepository.save(new User(
                userCreateReq.getName(),
                userCreateReq.getEmail(),
                encryptor.encrypt(userCreateReq.getPassword()),
                userCreateReq.getBirthday()
        ));
    }

    @Transactional
    public Optional<User> findPwMatchUser(String email, String password) {

        return userRepository.findByEmail(email)
                .map(user -> user.isMatch(encryptor, password) ? user: null);
    }
}
