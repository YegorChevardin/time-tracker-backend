package com.reactdevops.timetracker.backend.repository.providers;

import javax.sql.DataSource;

/**
 * Class for providing datasource tomcat connection pool
 *
 * @author yegorchevardin
 * @version 0.0.1
 */
public interface DataSourceProvider {
    DataSource getDataSource();
}
