package org.example.calendarproject.core.doamin.entity.repository;

import org.example.calendarproject.core.doamin.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule,Long> {
}
