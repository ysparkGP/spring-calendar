package org.example.calendarproject.core.doamin.entity.repository;

import org.example.calendarproject.core.doamin.RequestStatus;
import org.example.calendarproject.core.doamin.entity.Share;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShareRepository extends JpaRepository<Share, Long> {

    @Query("SELECT s from Share s where (s.fromUserId = ?1 or s.toUserId = ?1) and s.requestStatus = ?2 and s.direction = ?3")
    List<Share> findAllByDirection(Long userId, RequestStatus requestStatus, Share.Direction biDirection);

    List<Share> findAllByToUserIdAndRequestStatusAndDirection(Long userId, RequestStatus requestStatus, Share.Direction biDirection);
}
