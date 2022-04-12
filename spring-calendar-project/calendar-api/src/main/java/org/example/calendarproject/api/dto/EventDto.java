package org.example.calendarproject.api.dto;

import lombok.*;
import org.example.calendarproject.core.doamin.ScheduleType;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EventDto implements ScheduleDto{

    private Long scheduleId;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String title;
    private String description;
    private Long writerId;

    @Override
    public ScheduleType getScheduleType() {
        return ScheduleType.EVENT;
    }
}
