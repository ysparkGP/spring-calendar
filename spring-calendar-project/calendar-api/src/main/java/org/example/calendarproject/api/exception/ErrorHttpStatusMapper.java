package org.example.calendarproject.api.exception;

import org.example.calendarproject.core.exception.ErrorCode;
import org.springframework.http.HttpStatus;

// 헬퍼 클래스나 유틸 클래스의 성격을 띄는 경우 의미없는 기본 생성자의 생성을 막기위해
// 추상 클래스로 선언
public abstract class ErrorHttpStatusMapper {
    public static HttpStatus mapToStatus(ErrorCode errorCode){
        switch (errorCode){
            case ALREADY_EXISTS_USER:
            case VALIDATION_FAIL:
            case BAD_REQUEST:
            case EVENT_CREATE_OVERLAPPED_PERIOD:
                return HttpStatus.BAD_REQUEST;
            case PASSWORD_NOT_MATCH:
            case USER_NOT_FOUND:
                return HttpStatus.UNAUTHORIZED;
            default:
                return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
