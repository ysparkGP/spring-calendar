package org.example.calendarproject.api.service;

import org.example.calendarproject.api.controller.BatchController;
import org.example.calendarproject.api.dto.EngagementEmailStuff;
import org.example.calendarproject.core.doamin.entity.Share;

public interface EmailService {
    void sendEngagement(EngagementEmailStuff stuff);

    void sendAlarmMail(BatchController.SendMailBatchReq r);

    void sendShareRequestMail(String email, String name, Share.Direction direction);
}
