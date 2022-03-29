package org.example.calendarproject.core.doamin.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.calendarproject.core.doamin.Event;
import org.example.calendarproject.core.doamin.RequestStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private RequestStatus requestStatus;
}
