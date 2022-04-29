package org.example.calendarproject.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.calendarproject.core.doamin.entity.Share;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateShareReq {
    private Long toUserId;
    private Share.Direction direction;
}
