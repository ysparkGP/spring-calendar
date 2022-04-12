package org.example.calendarproject.core.doamin.entity.repository;

import org.example.calendarproject.core.doamin.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule,Long> {

    public List<Schedule> findAllByWriter_Id(Long userId);
}
