package nl.parkhaven.wasschema.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.beans.PropertyVetoException;

@Configuration
@ComponentScan(basePackages = "nl.parkhaven.wasschema.modules")
@EnableTransactionManagement
public class TestConfig {

    // Integration testing the service layer with the dao layer.

    // The reason I should have used an embedded db for testing is that I have no control over
    // my current db state. Even with Spring transactions.
    // This would have been handy if I wanted to add a user with a specific housenumber. I wouldnt
    // have to worry about picking a housenumber that doesnt already exists.
    // or When adding an appointment... How do I add an appointment somewhere in a database that isnt clean?

    // Handy links :
    // -http://blog.trifork.com/2012/12/11/properly-testing-spring-mvc-controllers/
    // https://www.petrikainulainen.net/programming/spring-framework/unit-testing-of-spring-mvc-controllers-normal-controllers/
    // https://myshittycode.com/2013/10/23/how-to-unit-test-spring-mvc-controller/
    // https://developer.salesforce.com/page/How_to_Write_Good_Unit_Tests

    @Bean
    public PropertiesConfiguration getConfiguration() throws ConfigurationException {
        return new PropertiesConfiguration("production.properties");
    }

    @Bean
    public PlatformTransactionManager transactionManagerForTest() throws PropertyVetoException, ConfigurationException {
        return new DataSourceTransactionManager(getDataSource());
    }

    @Bean
    public JdbcTemplate getJdbcTemplate() throws PropertyVetoException, ConfigurationException {
        return new JdbcTemplate(getDataSource());
    }

    @Bean()
    public ComboPooledDataSource getDataSource() throws PropertyVetoException, ConfigurationException {
        PropertiesConfiguration conf = getConfiguration();

        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(conf.getString("db.driverClass"));
        dataSource.setJdbcUrl(conf.getString("db.jdbcUrl"));
        dataSource.setUser(conf.getString("db.user"));
        dataSource.setPassword(conf.getString("db.password"));
        dataSource.setMinPoolSize(conf.getInt("db.minPoolSize"));
        dataSource.setMaxPoolSize(conf.getInt("db.maxPoolSize"));
        dataSource.setAcquireIncrement(conf.getInt("db.acquireIncrement"));
        dataSource.setMaxConnectionAge(conf.getInt("db.maxConnectionAge"));
        dataSource.setMaxIdleTimeExcessConnections(conf.getInt("db.maxIdleTimeExcessConnections"));
        dataSource.setCheckoutTimeout(conf.getInt("db.checkoutTimeout"));

        return dataSource;
    }

}
