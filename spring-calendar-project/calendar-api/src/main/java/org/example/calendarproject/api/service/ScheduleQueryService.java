package org.example.calendarproject.api.service;

import lombok.RequiredArgsConstructor;
import org.example.calendarproject.api.dto.AuthUser;
import org.example.calendarproject.api.dto.ScheduleDto;
import org.example.calendarproject.api.dto.SharedScheduleDto;
import org.example.calendarproject.api.util.DtoConverter;
import org.example.calendarproject.core.doamin.entity.repository.EngagementRepository;
import org.example.calendarproject.core.doamin.entity.repository.ScheduleRepository;
import org.example.calendarproject.core.service.UserService;
import org.example.calendarproject.core.util.Period;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ScheduleQueryService{

    private final UserService userService;
    private final ShareService shareService;
    private final ScheduleRepository scheduleRepository;
    private final EngagementRepository engagementRepository;

    public List<ScheduleDto> getScheduleByDay(AuthUser authUser, LocalDate date) {
        return getScheduleByPeriod(authUser, Period.of(date, date));
    }

    public List<ScheduleDto> getScheduleByWeek(AuthUser authUser, LocalDate startOfWeek) {
        return getScheduleByPeriod(authUser, Period.of(startOfWeek, startOfWeek.plusDays(6)));
    }

    public List<ScheduleDto> getScheduleByMonth(AuthUser authUser, YearMonth yearMonth) {
        return getScheduleByPeriod(authUser, Period.of(yearMonth.atDay(1), yearMonth.atEndOfMonth()));
    }

    private List<ScheduleDto> getScheduleByPeriod(AuthUser authUser, Period period) {
        final Stream<ScheduleDto> schedules =
                scheduleRepository.findAllByWriter_Id(authUser.getId())
                        .stream()
                        .filter(schedule -> schedule.isOverlapped(period))
                        .map(DtoConverter::fromSchedule);
        final Stream<ScheduleDto> engagements =
                engagementRepository.findAllByAttendee_Id(authUser.getId())
                        .stream()
                        .filter(engagement -> engagement.isOverlapped(period))
                        .map(engagement -> DtoConverter.fromSchedule(engagement.getSchedule()));

        return Stream.concat(schedules, engagements).collect(Collectors.toList());
    }

    public List<SharedScheduleDto> getSharedScheduleByDay(AuthUser authUser, LocalDate date) {
        // 대상 userId 모아오기 -> 기존 메서드 재사용
        return getSharedScheduleByFunction(authUser, (Long userId) -> getScheduleByDay(AuthUser.of(userId), date));
    }

    public List<SharedScheduleDto> getSharedScheduleByWeek(AuthUser authUser, LocalDate startOfWeek) {
        return getSharedScheduleByFunction(authUser, (Long userId) -> getScheduleByWeek(AuthUser.of(userId), startOfWeek));
    }

    public List<SharedScheduleDto> getSharedScheduleByMonth(AuthUser authUser, YearMonth yearMonth) {
        return getSharedScheduleByFunction(authUser, (Long userId) -> getScheduleByMonth(AuthUser.of(userId), yearMonth));
    }

    private List<SharedScheduleDto> getSharedScheduleByFunction(AuthUser authUser, Function<Long, List<ScheduleDto>> function) {
        return Stream.concat(shareService.findSharedUserIdsByUser(authUser).stream(), Stream.of(authUser.getId()))
                .map(userId -> SharedScheduleDto.builder()
                        .userId(userId)
                        .name(userService.findByUserId(userId).getName())
                        .me(userId.equals(authUser.getId()))
                        .schedules(function.apply(userId))
                        .build())
                .collect(Collectors.toList());
    }
}
