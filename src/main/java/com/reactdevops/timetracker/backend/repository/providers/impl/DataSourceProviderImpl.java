package com.reactdevops.timetracker.backend.repository.providers.impl;

import com.reactdevops.timetracker.backend.repository.providers.DataSourceProvider;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.RequestScoped;

import javax.sql.DataSource;

@RequestScoped
public class DataSourceProviderImpl implements DataSourceProvider {
  @Resource(name = "jdbc/timetracker")
  private DataSource dataSource;

  public DataSource getDataSource() {
    return dataSource;
  }
}
