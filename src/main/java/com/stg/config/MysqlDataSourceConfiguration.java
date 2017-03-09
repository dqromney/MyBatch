package com.stg.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Data Source Configuration.
 *
 * Created by dqromney on 1/30/17.
 */

public class MysqlDataSourceConfiguration {

    @Value("classpath:schema-mysql.sql")
    private Resource schemaScript;

    @Bean
    public JdbcTemplate jdbcTemplate(final DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    @Primary
    public DataSource mysqlDataSource() throws SQLException, ClassNotFoundException {
        final SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriver(new com.mysql.jdbc.Driver());
        // dataSource.setUrl("jdbc:mysql://localhost:3306/quotes?autoreconnect=true");
        dataSource.setUrl("jdbc:mysql://192.168.0.40:3306/quotes?autoreconnect=true");
        dataSource.setUsername("root");
        // dataSource.setPassword("");
        dataSource.setPassword("iag15501");
        DatabasePopulatorUtils.execute(databasePopulator(), dataSource);
        return dataSource;
    }

    @Bean
    public JdbcTemplate mysqlJdbcTemplate(@Qualifier("mysqlDataSource") final DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    private DatabasePopulator databasePopulator() {
        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(schemaScript);
        return populator;
    }
}
