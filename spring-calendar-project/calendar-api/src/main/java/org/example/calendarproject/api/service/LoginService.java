package org.example.calendarproject.api.service;

import lombok.RequiredArgsConstructor;
import org.example.calendarproject.api.dto.LoginReq;
import org.example.calendarproject.api.dto.SignUpReq;
import org.example.calendarproject.core.doamin.entity.User;
import org.example.calendarproject.core.dto.UserCreateReq;
import org.example.calendarproject.core.exception.CalendarException;
import org.example.calendarproject.core.exception.ErrorCode;
import org.example.calendarproject.core.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    public final static String LOGIN_SESSION_KEY = "USER_ID";
    private final UserService userService;

    @Transactional
    public void signUp(SignUpReq signUpReq, HttpSession session){
        /**
         * User Service 에 Create 요청을 담당한다. (이미 존재하는 경우의 유저 검증은 UserService 의 담당)
         * 생성이 되면 session 에 담고 return
         */
        final User user = userService.create(new UserCreateReq(
                signUpReq.getName(),
                signUpReq.getEmail(),
                signUpReq.getPassword(),
                signUpReq.getBirthday()
        ));
        session.setAttribute(LOGIN_SESSION_KEY, user.getId());
    }

    @Transactional
    public void login(LoginReq loginReq, HttpSession session){
        /**
         * session 값이 있으면 return
         * session 값이 없으면 비밀번호 체크 후에 로그인 & session 에 담고 return
         */
        final Long userId = (Long) session.getAttribute(LOGIN_SESSION_KEY);
        if(userId != null ){
            return;
        }

        final Optional<User> user = userService.findPwMatchUser(loginReq.getEmail(), loginReq.getPassword());
        if(user.isPresent()){
            session.setAttribute(LOGIN_SESSION_KEY, user.get().getId());
        }
        else{
            throw new CalendarException(ErrorCode.PASSWORD_NOT_MATCH);
        }
    }

    public void logout(HttpSession session){
        /**
         * session 제거 하고 끝
         */
        session.removeAttribute(LOGIN_SESSION_KEY);
    }

}
