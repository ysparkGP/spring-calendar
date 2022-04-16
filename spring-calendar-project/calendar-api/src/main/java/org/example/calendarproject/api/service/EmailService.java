package org.example.calendarproject.api.service;

import org.example.calendarproject.api.dto.EngagementEmailStuff;

public interface EmailService {
    void sendEngagement(EngagementEmailStuff stuff);
}
