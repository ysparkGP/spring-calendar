package org.example.calendarproject.api.dto;

import lombok.*;

import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginReq {

    private String email;
    @Size(min = 6, message = "6자리 이상 입력해주세요.")
    private String password;
}
