package org.example.calendarproject.api.service;

import org.example.calendarproject.api.controller.BatchController;
import org.example.calendarproject.api.dto.EngagementEmailStuff;
import org.example.calendarproject.core.doamin.entity.Share;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("test")
@Service
public class FakeEmailService implements EmailService{
    @Override
    public void sendEngagement(EngagementEmailStuff stuff) {
        System.out.println("send email. email : " + stuff.getSubject());
    }

    @Override
    public void sendAlarmMail(BatchController.SendMailBatchReq r) {
        System.out.println("send alarm" + r.toString());
    }

    @Override
    public void sendShareRequestMail(String email, String name, Share.Direction direction) {
        System.out.println("send share request mail. " + email + ", " + name +", " + direction);
    }
}
