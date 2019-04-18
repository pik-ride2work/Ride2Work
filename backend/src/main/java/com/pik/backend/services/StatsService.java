package com.pik.backend.services;

import com.pik.ride2work.tables.pojos.TeamDailyStats;

import java.util.Date;
import java.util.List;

public interface StatsService {
    public List<TeamDailyStats> getInDateRange(Date start, Date end, Integer teamId);

}
