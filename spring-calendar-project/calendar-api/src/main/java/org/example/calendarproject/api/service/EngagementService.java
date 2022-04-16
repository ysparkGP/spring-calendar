package org.example.calendarproject.api.service;

import lombok.RequiredArgsConstructor;
import org.example.calendarproject.api.dto.AuthUser;
import org.example.calendarproject.core.doamin.RequestReplyType;
import org.example.calendarproject.core.doamin.RequestStatus;
import org.example.calendarproject.core.doamin.entity.repository.EngagementRepository;
import org.example.calendarproject.core.exception.CalendarException;
import org.example.calendarproject.core.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class EngagementService {
    private final EngagementRepository engagementRepository;

    @Transactional
    public RequestStatus update(AuthUser authUser, Long engagementId, RequestReplyType type) {
        // engagementId 조회
        // 참석자가 auth user 와 같은지 비교
        // requested 상태인지 체크
        // update
        return engagementRepository.findById(engagementId)
                .filter(e -> e.getRequestStatus() == RequestStatus.REQUESTED)
                .filter(e -> e.getAttendee().getId().equals(authUser.getId()))
                .map(e -> e.reply(type))
                .orElseThrow(() -> new CalendarException(ErrorCode.BAD_REQUEST))
                .getRequestStatus();

    }
}
