package com.flukebsk.quartzschedulerdatabase.repository;

import com.flukebsk.quartzschedulerdatabase.entity.HistoryJobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryJobRepository extends JpaRepository<HistoryJobEntity, Long> {
}
