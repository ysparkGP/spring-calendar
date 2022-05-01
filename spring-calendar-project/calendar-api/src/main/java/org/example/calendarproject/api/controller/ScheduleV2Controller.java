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
@RequestMapping("/api/v2/schedules")
@RestController
public class ScheduleV2Controller {

    private final ScheduleQueryService scheduleQueryService;

    @GetMapping("/day")
    public List<SharedScheduleDto> getScheduleByDay(
            AuthUser authUser,
            @RequestParam(required = false)
            // requestParam 에 담기기 위해서 @DateTimeFormat 으로 LocalDate 형식을 명확히 지정
                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date)
    {
        return scheduleQueryService.getSharedScheduleByDay(authUser, date == null? LocalDate.now(): date);
    }
    @GetMapping("/week")
    public List<SharedScheduleDto> getScheduleByWeek(
            AuthUser authUser,
            @RequestParam(required = false)
            // requestParam 에 담기기 위해서 @DateTimeFormat 으로 LocalDate 형식을 명확히 지정
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startOfWeek)
    {
        return scheduleQueryService.getSharedScheduleByWeek(authUser, startOfWeek == null? LocalDate.now(): startOfWeek);
    }
    @GetMapping("/month")
    public List<SharedScheduleDto> getScheduleByMonth(
            AuthUser authUser,
            @RequestParam(required = false)
            // requestParam 에 담기기 위해서 @DateTimeFormat 으로 LocalDate 형식을 명확히 지정
            @DateTimeFormat(pattern = "yyyy-MM") String yearMonth) // 2022-04
    {
        return scheduleQueryService.getSharedScheduleByMonth(authUser, yearMonth == null? YearMonth.now(): YearMonth.parse(yearMonth));
    }


}
