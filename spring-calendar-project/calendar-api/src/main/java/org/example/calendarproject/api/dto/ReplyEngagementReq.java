package org.example.calendarproject.api.dto;

import org.example.calendarproject.core.doamin.RequestReplyType;

public class ReplyEngagementReq {
    private RequestReplyType type;

    public ReplyEngagementReq(){}

    public ReplyEngagementReq(RequestReplyType type) {
        this.type = type;
    }

    public RequestReplyType getType() {
        return type;
    }
}
