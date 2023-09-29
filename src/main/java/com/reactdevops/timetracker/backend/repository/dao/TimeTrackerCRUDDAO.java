package com.reactdevops.timetracker.backend.repository.dao;

import com.reactdevops.timetracker.backend.repository.entities.TrackedTimeEntity;
import tech.tablesaw.api.Table;

import java.sql.SQLException;
import java.util.List;

public interface TimeTrackerCRUDDAO extends CreateReadDeleteDAO<TrackedTimeEntity> {

    List<TrackedTimeEntity> readAllByUserId(Long userId) throws SQLException;

    Table readReport() throws SQLException;
}
