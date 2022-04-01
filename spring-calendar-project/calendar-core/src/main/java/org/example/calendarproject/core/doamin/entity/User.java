package org.example.calendarproject.core.doamin.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.calendarproject.core.util.Encryptor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity{


    private String name;

    private String email;

    private String password;

    private LocalDate birthday;

    public User(String name, String email, String password, LocalDate birthday) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
    }

    // strategy 패턴... 인터페이스를 인자로 넘겨서 기능 위임을 하게된다.
    public boolean isMatch(Encryptor encryptor, String password) {
        return encryptor.isMatch(password, this.password);
    }
}
