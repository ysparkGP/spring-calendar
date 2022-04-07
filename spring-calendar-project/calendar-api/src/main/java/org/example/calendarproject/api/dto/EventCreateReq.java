package org.example.calendarproject.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventCreateReq {
    private String title;
    private String description;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private List<Long> attendeeIds;
}
