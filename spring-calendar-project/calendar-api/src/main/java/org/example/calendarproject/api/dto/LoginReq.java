package org.example.calendarproject.api.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginReq {

    private String email;

    private String password;
}
