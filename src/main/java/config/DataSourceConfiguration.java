package config;

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
public class DataSourceConfiguration {

    @Value("classpath:schema-mysql.sql")
    private Resource schemaMysqlScript;
//    @Value("classpath:schema-hsql.sql")
//    private Resource schemaHsqlScript;

//    @Bean
//    @Primary
//    public DataSource hsqldbDataSource() throws SQLException {
//        final SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
//        dataSource.setDriver(new org.hsqldb.jdbcDriver());
//        dataSource.setUrl("jdbc:hsqldb:mem:mydb");
//        dataSource.setUsername("sa");
//        dataSource.setPassword("");
//        return dataSource;
//    }

    @Bean
    public JdbcTemplate jdbcTemplate(final DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    @Primary
    public DataSource mysqlDataSource() throws SQLException, ClassNotFoundException {
        final SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriver(new com.mysql.jdbc.Driver());
        dataSource.setUrl("jdbc:mysql://localhost:3306/dev?autoreconnect=true");
        dataSource.setUsername("root");
        dataSource.setPassword("");
        DatabasePopulatorUtils.execute(databaseMysqlPopulator(), dataSource);
        return dataSource;
    }

    @Bean
    public JdbcTemplate mysqlJdbcTemplate(@Qualifier("mysqlDataSource") final DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    private DatabasePopulator databaseMysqlPopulator() {
        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(schemaMysqlScript);
        return populator;
    }
}
