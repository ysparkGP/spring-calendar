package org.example.calendarproject.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class TaskCreateReq {

    private String title;
    private String description;
    private LocalDateTime taskAt;
}
