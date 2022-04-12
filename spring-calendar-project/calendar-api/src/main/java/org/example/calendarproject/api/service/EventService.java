package org.example.calendarproject.api.service;

import lombok.RequiredArgsConstructor;
import org.example.calendarproject.api.dto.AuthUser;
import org.example.calendarproject.api.dto.EventCreateReq;
import org.example.calendarproject.core.doamin.RequestStatus;
import org.example.calendarproject.core.doamin.entity.Engagement;
import org.example.calendarproject.core.doamin.entity.Schedule;
import org.example.calendarproject.core.doamin.entity.User;
import org.example.calendarproject.core.doamin.entity.repository.EngagementRepository;
import org.example.calendarproject.core.doamin.entity.repository.ScheduleRepository;
import org.example.calendarproject.core.exception.CalendarException;
import org.example.calendarproject.core.exception.ErrorCode;
import org.example.calendarproject.core.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EmailService emailService;
    private final EngagementRepository engagementRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserService userService;

    @Transactional
    public void create(EventCreateReq eventCreateReq, AuthUser authUser) {
        // 이벤트 참여자의 다른 이벤트와 중복이 되면 안된다.
        // 추가로 이메일 발송
        final List<Engagement> engagementList = engagementRepository.findAll(); // TODO findAll 금지 개선
        if(engagementList.stream().anyMatch(
                e -> eventCreateReq.getAttendeeIds().contains(e.getAttendee().getId())
                && e.getRequestStatus() == RequestStatus.ACCEPTED
                && e.getEvent().isOverlapped(eventCreateReq.getStartAt(), eventCreateReq.getEndAt())
        )){
            throw new CalendarException(ErrorCode.EVENT_CREATE_OVERLAPPED_PERIOD);
        }
        final Schedule eventSchedule = Schedule.event(
                eventCreateReq.getTitle(),
                eventCreateReq.getDescription(),
                eventCreateReq.getStartAt(),
                eventCreateReq.getEndAt(),
                userService.findByUserId(authUser.getId())
        );
        scheduleRepository.save(eventSchedule);
        eventCreateReq.getAttendeeIds()
                .forEach(atId ->{
                    final User attendee = userService.findByUserId(atId);
                    final Engagement engagement = Engagement.builder()
                            .schedule(eventSchedule)
                            .requestStatus(RequestStatus.REQUESTED)
                            .attendee(attendee)
                            .build();
                    engagementRepository.save(engagement);
                    emailService.sendEngagement(engagement);
                });
    }
}
