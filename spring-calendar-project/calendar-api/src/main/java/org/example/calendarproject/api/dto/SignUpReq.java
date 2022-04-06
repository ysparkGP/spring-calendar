package org.example.calendarproject.api.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpReq {

    private String name;

    private String email;

    private String password;

    private LocalDate birthday;
}
