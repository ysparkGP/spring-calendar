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

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    /**
     * 공유 대상 조회
     *
     * 자신과 양방향 공유관계인 상대방(자신이 TO, From 둘 다 가능)
     * 내가 공유관계의 수신자(toUser) 인 경우 & 단 방향
     * @param authUser
     * @return
     */

    @Transactional
    public List<Long> findSharedUserIdsByUser(AuthUser authUser) {
        final Stream<Long> biDirectionShares = shareRepository.findAllByDirection(
                authUser.getId(),
                RequestStatus.ACCEPTED,
                Share.Direction.BI_DIRECTION
        ).stream().map(s -> s.getToUserId().equals(authUser.getId())? s.getFromUserId() : s.getToUserId());
        final Stream<Long> uniDirectionShares = shareRepository.findAllByToUserIdAndRequestStatusAndDirection(
                authUser.getId(),
                RequestStatus.ACCEPTED,
                Share.Direction.UNI_DIRECTION
        ).stream().map(s -> s.getFromUserId());

        return Stream.concat(biDirectionShares, uniDirectionShares).collect(Collectors.toList());
    }
}
