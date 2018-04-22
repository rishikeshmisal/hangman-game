package game.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * Created by Rishikesh on 4/12/2018.
 */
@org.springframework.context.annotation.Configuration
@EnableJpaRepositories(basePackages = "game", transactionManagerRef = "transMgr")
public class Configuration {

    private static final Logger logger = LoggerFactory.getLogger(Configuration.class);
    private static final String DB_CLASS_NAME = "org.postgresql.Driver";
    private static String DB_URL = "jdbc:postgresql://localhost:5433/postgres";
    private static String DB_USER_NAME = "postgres";
    private static String DB_PASSWORD = "admin123";

    @Primary
    @Bean
    public DataSource dataSource(){

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(DB_CLASS_NAME);
        dataSource.setUrl(DB_URL);
        dataSource.setUsername(DB_USER_NAME);
        dataSource.setPassword(DB_PASSWORD);
        logger.debug("Data source details obtained");
        return dataSource;
    }

    @Bean(name = "transMgr")
    public PlatformTransactionManager myDAPTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setDataSource(dataSource());
        return transactionManager;
    }
}
