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

    private final Encryptor bcryptEncryptor;
    private final UserRepository userRepository;

    @Transactional
    public User create(UserCreateReq req) {
        userRepository.findByEmail(req.getEmail())
                .ifPresent(u -> {
                    throw new RuntimeException("cannot find user");
                });
        return userRepository.save(User.builder()
                .name(req.getName())
                .password(bcryptEncryptor.encrypt(req.getPassword()))
                .email(req.getEmail())
                .birthday(req.getBirthday())
                .build());
    }

    @Transactional
    public Optional<User> findPwMatchUser(String email, String password) {
        return userRepository.findByEmail(email)
                .map(u -> u.isMatched(bcryptEncryptor, password) ? u : null);
    }

    @Transactional
    public User findByUserId(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("no user by id"));
    }
}
