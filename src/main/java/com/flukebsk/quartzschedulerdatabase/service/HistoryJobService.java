package com.flukebsk.quartzschedulerdatabase.service;

import com.flukebsk.quartzschedulerdatabase.entity.HistoryJobEntity;
import com.flukebsk.quartzschedulerdatabase.repository.HistoryJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HistoryJobService {
    @Autowired
    public HistoryJobRepository historyJobRepository;

    public void createHistory(HistoryJobEntity historyJobEntity){
        historyJobRepository.save(historyJobEntity);
    }
}
