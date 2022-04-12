package org.example.calendarproject.api.dto;

import lombok.*;
import org.example.calendarproject.core.doamin.ScheduleType;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class NotificationDto implements ScheduleDto{

    private Long scheduleId;
    private LocalDateTime notifyAt;
    private String title;
    private Long writerId;

    @Override
    public ScheduleType getScheduleType() {
        return ScheduleType.NOTIFICATION;
    }
}
