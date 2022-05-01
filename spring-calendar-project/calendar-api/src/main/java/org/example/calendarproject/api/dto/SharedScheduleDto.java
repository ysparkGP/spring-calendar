package org.example.calendarproject.api.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Builder
public class SharedScheduleDto {
    private Long userId;
    private String name;
    private boolean me;
    private List<ScheduleDto> schedules;
}
