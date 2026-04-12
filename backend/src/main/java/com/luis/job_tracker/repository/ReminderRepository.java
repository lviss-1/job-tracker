package com.luis.job_tracker.repository;

import com.luis.job_tracker.entity.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {
    List<Reminder> findBySentFalseAndRemindAtBefore(LocalDateTime remindAtBefore);
}
