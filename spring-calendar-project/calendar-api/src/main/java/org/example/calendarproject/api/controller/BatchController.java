package org.example.calendarproject.api.controller;

import lombok.*;
import org.example.calendarproject.api.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class BatchController {
    private final EmailService emailService;

    @PostMapping("/api/batch/mail")
    public ResponseEntity<Void> sendMail(@RequestBody List<SendMailBatchReq> req){
        req.forEach(r -> emailService.sendAlarmMail(r));
        return ResponseEntity.ok().build();
    }

    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class SendMailBatchReq {
        private Long id; // scheduleId
        private LocalDateTime startAt;
        private String title;
        private String userMail;
    }

}
