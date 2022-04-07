package org.example.calendarproject.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.calendarproject.core.util.TimeUnit;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class NotificationCreateReq {

    private String title;
    private LocalDateTime notifyAt;
    private RepeatInfo repeatInfo;

    public List<LocalDateTime> getRepeatTimes() {
        if(repeatInfo == null){
            return Collections.singletonList(notifyAt);
        }
        return IntStream.range(0, repeatInfo.times).mapToObj(
                i -> {
                    long increment = (long) repeatInfo.interval.intervalValue * i;
                    switch (repeatInfo.interval.timeUnit){
                        case DAY:
                            return notifyAt.plusDays(increment);
                        case WEEK:
                            return notifyAt.plusWeeks(increment);
                        case MONTH:
                            return notifyAt.plusMonths(increment);
                        case YEAR:
                            return notifyAt.plusYears(increment);
                        default:
                            throw new RuntimeException("bad requests. not match time unit");
                    }
                }
        ).collect(Collectors.toList());
    }

    @NoArgsConstructor
    @Getter
    @Setter
    @AllArgsConstructor
    public static class RepeatInfo{
        private Interval interval;
        private int times;

    }

    @NoArgsConstructor
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Interval{
        private int intervalValue;
        private TimeUnit timeUnit;
    }

}
