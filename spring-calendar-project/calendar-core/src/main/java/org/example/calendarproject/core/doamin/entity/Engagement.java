package org.example.calendarproject.core.doamin.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.calendarproject.core.doamin.Event;
import org.example.calendarproject.core.doamin.RequestStatus;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "engagements")
public class Engagement extends BaseEntity{


    @JoinColumn(name = "event_id")
    @ManyToOne
    private Schedule schedule;

    @JoinColumn(name = "attendee_id")
    @ManyToOne
    private User attendee;

    @Enumerated(value = EnumType.STRING)
    private RequestStatus requestStatus;

    public Event getEvent(){
        return schedule.toEvent();
    }
}
