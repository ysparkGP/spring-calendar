package org.example.calendarproject.core.doamin.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.calendarproject.core.doamin.Event;
import org.example.calendarproject.core.doamin.RequestReplyType;
import org.example.calendarproject.core.doamin.RequestStatus;
import org.example.calendarproject.core.util.Period;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "shares")
public class Share extends BaseEntity {


    private Long fromUserId;
    private Long toUserId;

    @Enumerated(value = EnumType.STRING)
    private RequestStatus requestStatus;

    @Enumerated(value = EnumType.STRING)
    private Direction direction;

    public Share reply(RequestReplyType type) {
        switch (type) {
            case ACCEPT:
                this.requestStatus = RequestStatus.ACCEPTED;
                break;
            case REJECT:
                this.requestStatus = RequestStatus.REJECTED;
                break;
        }
        return this;
    }
    public enum Direction{
        BI_DIRECTION, UNI_DIRECTION
    }
}