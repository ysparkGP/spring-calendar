package org.example.calendarproject.api.controller;

import lombok.RequiredArgsConstructor;
import org.example.calendarproject.api.dto.*;
import org.example.calendarproject.api.service.*;
import org.example.calendarproject.core.doamin.RequestStatus;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/schedules")
@RestController
public class ScheduleController {

    private final ScheduleQueryService scheduleQueryService;
    private final TaskService taskService;
    private final EventService eventService;
    private final EngagementService engagementService;
    private final NotificationService notificationService;

    @PostMapping("/tasks")
    public ResponseEntity<Void> createTask(
            @RequestBody TaskCreateReq taskCreateReq,
            // ArgumentResolver 로 앞단에서 처리하여 인자로 받게끔 설정
            AuthUser authUser){

        taskService.create(taskCreateReq, authUser);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/events")
    public ResponseEntity<Void> createEvent(
            @Valid
            @RequestBody EventCreateReq eventCreateReq,
            // ArgumentResolver 로 앞단에서 처리하여 인자로 받게끔 설정
            AuthUser authUser){

        eventService.create(eventCreateReq, authUser);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/notifications")
    public ResponseEntity<Void> createNotifications(
            @RequestBody NotificationCreateReq notificationCreateReq,
            // ArgumentResolver 로 앞단에서 처리하여 인자로 받게끔 설정
            AuthUser authUser){

        notificationService.create(notificationCreateReq, authUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/day")
    public List<ScheduleDto> getScheduleByDay(
            AuthUser authUser,
            @RequestParam(required = false)
            // requestParam 에 담기기 위해서 @DateTimeFormat 으로 LocalDate 형식을 명확히 지정
                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date)
    {
        return scheduleQueryService.getScheduleByDay(authUser, date == null? LocalDate.now(): date);
    }
    @GetMapping("/week")
    public List<ScheduleDto> getScheduleByWeek(
            AuthUser authUser,
            @RequestParam(required = false)
            // requestParam 에 담기기 위해서 @DateTimeFormat 으로 LocalDate 형식을 명확히 지정
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startOfWeek)
    {
        return scheduleQueryService.getScheduleByWeek(authUser, startOfWeek == null? LocalDate.now(): startOfWeek);
    }
    @GetMapping("/month")
    public List<ScheduleDto> getScheduleByMonth(
            AuthUser authUser,
            @RequestParam(required = false)
            // requestParam 에 담기기 위해서 @DateTimeFormat 으로 LocalDate 형식을 명확히 지정
            @DateTimeFormat(pattern = "yyyy-MM") String yearMonth) // 2022-04
    {
        return scheduleQueryService.getScheduleByMonth(authUser, yearMonth == null? YearMonth.now(): YearMonth.parse(yearMonth));
    }

    @PutMapping("/events/engagements/{engagementId}")
    public RequestStatus updateEngagement(
            @Valid @RequestBody ReplyEngagementReq replyEngagementReq,
            @PathVariable Long engagementId,
            AuthUser authUser
    ){
        return engagementService.update(authUser, engagementId, replyEngagementReq.getType());
    }
}
