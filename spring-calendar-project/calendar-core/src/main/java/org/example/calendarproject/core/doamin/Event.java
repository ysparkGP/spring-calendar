package org.example.calendarproject.core.doamin;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.calendarproject.core.doamin.entity.Schedule;

@NoArgsConstructor
@Getter
public class Event {

    private Schedule schedule;

    public Event(Schedule schedule) {
        this.schedule = schedule;
    }
}
