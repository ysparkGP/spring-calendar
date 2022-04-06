package org.example.calendarproject.api.service;

import lombok.RequiredArgsConstructor;
import org.example.calendarproject.api.dto.AuthUser;
import org.example.calendarproject.api.dto.TaskCreateReq;
import org.example.calendarproject.core.doamin.entity.Schedule;
import org.example.calendarproject.core.doamin.entity.repository.ScheduleRepository;
import org.example.calendarproject.core.service.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final UserService userService;
    private final ScheduleRepository scheduleRepository;

    public void create(TaskCreateReq taskCreateReq, AuthUser authUser) {
        final Schedule taskSchedule = Schedule.task(taskCreateReq.getTitle(),
                taskCreateReq.getDescription(),
                taskCreateReq.getTaskAt(),
                userService.findByUserId(authUser.getId()));

        scheduleRepository.save(taskSchedule);
    }
}
