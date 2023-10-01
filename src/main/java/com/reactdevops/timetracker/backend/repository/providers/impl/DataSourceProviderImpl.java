package com.reactdevops.timetracker.backend.repository.providers.impl;

import com.reactdevops.timetracker.backend.repository.providers.DataSourceProvider;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.RequestScoped;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

@RequestScoped
public class DataSourceProviderImpl implements DataSourceProvider {
  @Resource(name = "jdbc/timetracker")
  private DataSource dataSource;

  public DataSource getDataSource() {
    return dataSource;
  }

  public void reloadDataSource() {
    try {
      Context initContext = new InitialContext();
      Context envContext = (Context) initContext.lookup("java:/comp/env");
      dataSource = (DataSource) envContext.lookup("jdbc/timetracker");
    } catch (NamingException e) {
      throw new RuntimeException("Could not connect to database", e);
    }
  }
}
