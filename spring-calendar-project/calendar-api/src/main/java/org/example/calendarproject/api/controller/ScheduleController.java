package org.example.calendarproject.api.controller;

import lombok.RequiredArgsConstructor;
import org.example.calendarproject.api.dto.AuthUser;
import org.example.calendarproject.api.dto.TaskCreateReq;
import org.example.calendarproject.api.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static org.example.calendarproject.api.service.LoginService.LOGIN_SESSION_KEY;

@RequiredArgsConstructor
@RequestMapping("/api/schedules")
@RestController
public class ScheduleController {

    private final TaskService taskService;

    @PostMapping("/tasks")
    public ResponseEntity<Void> createTask(
            @RequestBody TaskCreateReq taskCreateReq,
            // ArgumentResolver 로 앞단에서 처리하여 인자로 받게끔 설정
            AuthUser authUser){

        taskService.create(taskCreateReq, authUser);
        return ResponseEntity.ok().build();
    }
}
