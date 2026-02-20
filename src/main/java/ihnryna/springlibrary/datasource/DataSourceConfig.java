package ihnryna.springlibrary.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;

@Configuration
@EnableTransactionManagement
public class DataSourceConfig {

    @Autowired
    private Environment env;

    @Bean
    public DataSource primaryDataSource() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(Objects.requireNonNull(env.getProperty("primary.datasource.url")));
        ds.setUsername(Objects.requireNonNull(env.getProperty("primary.datasource.username")));
        ds.setPassword(Objects.requireNonNull(env.getProperty("primary.datasource.password")));
        ds.setMaximumPoolSize(5);
        ds.setConnectionTimeout(1000);
        return ds;
    }

    @Bean
    public DataSource replicaDataSource() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(Objects.requireNonNull(env.getProperty("replica.datasource.url")));
        ds.setUsername(Objects.requireNonNull(env.getProperty("primary.datasource.username")));
        ds.setPassword(Objects.requireNonNull(env.getProperty("primary.datasource.password")));
        ds.setMaximumPoolSize(5);
        ds.setConnectionTimeout(1000);
        ds.setInitializationFailTimeout(0);
        return ds;
    }

    @Bean
    public DataSource routingDataSourceForJpa(@Qualifier("primaryDataSource") DataSource primary,
                                              @Qualifier("replicaDataSource") DataSource replica) {
        RoutingDataSource routing = new RoutingDataSource();
        Map<Object, Object> sources = new HashMap<>();
        sources.put(DataSourceType.PRIMARY, primary);
        sources.put(DataSourceType.REPLICA, replica);
        routing.setTargetDataSources(sources);
        routing.setDefaultTargetDataSource(primary);
        routing.afterPropertiesSet();
        return routing;
    }


    @Bean
    @Primary
    public DataSource dataSource(
            @Qualifier("routingDataSourceForJpa") DataSource routing) {
        return new LazyConnectionDataSourceProxy(routing);
    }

}
