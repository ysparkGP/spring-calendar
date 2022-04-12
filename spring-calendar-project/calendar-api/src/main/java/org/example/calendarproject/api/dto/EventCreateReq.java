package org.example.calendarproject.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventCreateReq {
    @NotBlank
    private String title;
    private String description;
    @NotNull
    private LocalDateTime startAt;
    @NotNull
    private LocalDateTime endAt;
    private List<Long> attendeeIds;
}
