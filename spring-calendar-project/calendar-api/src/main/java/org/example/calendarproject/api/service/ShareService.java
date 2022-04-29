package org.example.calendarproject.api.service;

import lombok.RequiredArgsConstructor;
import org.example.calendarproject.api.dto.AuthUser;
import org.example.calendarproject.api.dto.CreateShareReq;
import org.example.calendarproject.core.doamin.RequestReplyType;
import org.example.calendarproject.core.doamin.RequestStatus;
import org.example.calendarproject.core.doamin.entity.Share;
import org.example.calendarproject.core.doamin.entity.User;
import org.example.calendarproject.core.doamin.entity.repository.ShareRepository;
import org.example.calendarproject.core.exception.CalendarException;
import org.example.calendarproject.core.exception.ErrorCode;
import org.example.calendarproject.core.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShareService {
    private final UserService userService;
    private final ShareRepository shareRepository;
    private final EmailService emailService;

    @Transactional
    public void createShare(AuthUser authUser, CreateShareReq req) {
        final User fromUser = userService.findByUserId(authUser.getId());
        final User toUser = userService.findByUserId(req.getToUserId());
        shareRepository.save(Share.builder()
                        .fromUserId(fromUser.getId())
                        .toUserId(toUser.getId())
                        .direction(req.getDirection())
                        .requestStatus(RequestStatus.REQUESTED)
                        .build());
        emailService.sendShareRequestMail(toUser.getEmail(), fromUser.getName(), req.getDirection());
    }

    @Transactional
    public void replyToShareRequest(Long shareId, AuthUser authUser, RequestReplyType type) {
        shareRepository.findById(shareId)
                .filter(share -> share.getToUserId().equals(authUser.getId()))
                .filter(share -> share.getRequestStatus() == RequestStatus.REQUESTED)
                .map(share -> share.reply(type))
                .orElseThrow(()-> new CalendarException(ErrorCode.BAD_REQUEST));
    }
}
