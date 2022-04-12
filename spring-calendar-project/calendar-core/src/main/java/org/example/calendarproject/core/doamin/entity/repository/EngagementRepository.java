package org.example.calendarproject.core.doamin.entity.repository;

import org.example.calendarproject.core.doamin.entity.Engagement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EngagementRepository extends JpaRepository<Engagement, Long> {
    List<Engagement> findAllByAttendee_Id(Long userId);
}
