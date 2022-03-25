package org.example.calendarproject.core.doamin;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.calendarproject.core.doamin.entity.Schedule;

@Getter
@NoArgsConstructor
public class Notification {

    private Schedule schedule;

    public Notification(Schedule schedule) {
        this.schedule = schedule;
    }
}
