package timepass.ge.com.game.config;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * Created by Rishikesh on 4/12/2018.
 */
@org.springframework.context.annotation.Configuration
@EnableJpaRepositories(basePackages = "timepass/ge/com/game")
public class Configuration {

    private static final Logger logger = LoggerFactory.getLogger(Configuration.class);
    /*private static final String DB_CLASS_NAME = "org.postgresql.Driver";
    private static String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static String DB_USER_NAME = "postgres";
    private static String DB_PASSWORD = "postgres";

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
    }*/
    @Bean
    @Primary
    LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) throws IOException {
        return builder.dataSource(db())
                .packages("timepass.ge.com.game.entity")
                .persistenceUnit("postgres")
                .build();
    }

    @Bean
    @Primary
    @FlywayDataSource
    DataSource db() throws IOException {
        EmbeddedPostgres pg = EmbeddedPostgres.builder().setPort(62776).start();
        return pg.getPostgresDatabase();
    }
}
