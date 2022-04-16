package org.example.calendarproject.api.service;

import org.example.calendarproject.api.dto.EngagementEmailStuff;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("test")
@Service
public class FakeEmailService implements EmailService{
    @Override
    public void sendEngagement(EngagementEmailStuff stuff) {
        System.out.println("send email. email : " + stuff.getSubject());
    }
}
