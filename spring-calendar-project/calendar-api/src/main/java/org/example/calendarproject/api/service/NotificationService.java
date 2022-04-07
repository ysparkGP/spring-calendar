package org.example.calendarproject.api.service;

import lombok.RequiredArgsConstructor;
import org.example.calendarproject.api.dto.AuthUser;
import org.example.calendarproject.api.dto.NotificationCreateReq;
import org.example.calendarproject.core.doamin.entity.Schedule;
import org.example.calendarproject.core.doamin.entity.User;
import org.example.calendarproject.core.doamin.entity.repository.ScheduleRepository;
import org.example.calendarproject.core.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final ScheduleRepository scheduleRepository;
    private final UserService userService;


    @Transactional
    public void create(NotificationCreateReq notificationCreateReq, AuthUser authUser) {
        final User user = userService.findByUserId(authUser.getId());

        final List<LocalDateTime> notifyAtList = notificationCreateReq.getRepeatTimes();
        notifyAtList.forEach(
                notifyAt -> {
                    final Schedule notificationSchedule = Schedule.notification(
                            notificationCreateReq.getTitle(),
                            notifyAt,
                            user);
                    scheduleRepository.save(notificationSchedule);
                }
        );


    }
}
