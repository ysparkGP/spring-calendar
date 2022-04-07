package org.example.calendarproject.api.service;

import org.example.calendarproject.core.doamin.entity.Engagement;

public interface EmailService {
    void sendEngagement(Engagement engagement);
}
