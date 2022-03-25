package org.example.calendarproject.core.doamin;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.calendarproject.core.doamin.entity.Schedule;

@NoArgsConstructor
@Getter
public class Task {

    private Schedule schedule;

    public Task(Schedule schedule) {
        this.schedule = schedule;
    }

    public String getTitle() {
        return schedule.getTitle();
    }
}
