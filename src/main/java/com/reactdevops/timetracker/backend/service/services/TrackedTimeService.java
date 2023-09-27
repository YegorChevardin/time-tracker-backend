package com.reactdevops.timetracker.backend.service.services;


import com.reactdevops.timetracker.backend.web.dto.TrackedTime;

import java.sql.SQLException;
import java.util.List;

public interface TrackedTimeService extends CreateReadDeleteService<TrackedTime> {
    List<TrackedTime> readAllByUserId(Long userId) throws SQLException;
}
