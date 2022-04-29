package org.example.calendarproject.api.dto;

import org.example.calendarproject.core.doamin.RequestReplyType;

public class ReplyReq {
    private RequestReplyType type;

    public ReplyReq(){}

    public ReplyReq(RequestReplyType type) {
        this.type = type;
    }

    public RequestReplyType getType() {
        return type;
    }
}
