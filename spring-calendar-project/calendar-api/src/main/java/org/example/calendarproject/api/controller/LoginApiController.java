package org.example.calendarproject.api.controller;

import lombok.RequiredArgsConstructor;
import org.example.calendarproject.api.dto.LoginReq;
import org.example.calendarproject.api.dto.SignUpReq;
import org.example.calendarproject.api.service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RestController
public class LoginApiController {

    private final LoginService loginService;

    @PostMapping("/api/sign-up")
    public ResponseEntity<Void> signUp(@RequestBody SignUpReq signUpReq, HttpSession session){
        loginService.signUp(signUpReq, session);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/login")
    public ResponseEntity<Void> login(@RequestBody LoginReq loginReq, HttpSession session){
        loginService.login(loginReq, session);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/logout")
    public ResponseEntity<Void> logout(HttpSession session){
        loginService.logout(session);
        return ResponseEntity.ok().build();
    }

}
