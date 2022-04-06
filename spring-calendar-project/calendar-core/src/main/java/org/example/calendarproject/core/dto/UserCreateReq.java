package org.example.calendarproject.core.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateReq {
    private String name;

    private String email;

    private String password;

    private LocalDate birthday;
}
