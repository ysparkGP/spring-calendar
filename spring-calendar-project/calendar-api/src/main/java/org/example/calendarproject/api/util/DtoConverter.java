package org.example.calendarproject.api.util;

import org.example.calendarproject.api.dto.EventDto;
import org.example.calendarproject.api.dto.NotificationDto;
import org.example.calendarproject.api.dto.ScheduleDto;
import org.example.calendarproject.api.dto.TaskDto;
import org.example.calendarproject.core.doamin.entity.Schedule;
import org.example.calendarproject.core.exception.CalendarException;
import org.example.calendarproject.core.exception.ErrorCode;

public abstract class DtoConverter {

    public static ScheduleDto fromSchedule(Schedule schedule){
        switch(schedule.getScheduleType()){
            case EVENT:
                return EventDto.builder()
                        .scheduleId(schedule.getId())
                        .description(schedule.getDescription())
                        .startAt(schedule.getStartAt())
                        .endAt(schedule.getEndAt())
                        .title(schedule.getTitle())
                        .writerId(schedule.getWriter().getId())
                        .build();
            case TASK:
                return TaskDto.builder()
                        .scheduleId(schedule.getId())
                        .taskAt(schedule.getStartAt())
                        .description(schedule.getDescription())
                        .writerId(schedule.getWriter().getId())
                        .title(schedule.getTitle())
                        .build();
            case NOTIFICATION:
                return NotificationDto.builder()
                        .notifyAt(schedule.getStartAt())
                        .scheduleId(schedule.getId())
                        .title(schedule.getTitle())
                        .writerId(schedule.getWriter().getId())
                        .build();
            default:
                throw new CalendarException(ErrorCode.BAD_REQUEST);
        }
    }
}
