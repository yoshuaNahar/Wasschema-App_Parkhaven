package nl.parkhaven.wasschema.config;

import java.beans.PropertyVetoException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "nl.parkhaven.wasschema" })
public class DispatcherConfig extends WebMvcConfigurerAdapter {

	// Context for the dispatcherServlet.
	// http://stackoverflow.com/questions/3652090/difference-between-applicationcontext-xml-and-spring-servlet-xml-in-spring-frame

	// https://kielczewski.eu/2013/11/spring-mvc-without-web-xml-using-webapplicationinitializer/

	// http://docs.spring.io/autorepo/docs/spring-framework/4.1.x/javadoc-api/org/springframework/web/WebApplicationInitializer.html

	@Bean
	public PropertiesConfiguration getConfiguration() throws ConfigurationException {
		return new PropertiesConfiguration("production.properties");
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

	@Bean
	public ViewResolver getViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/WEB-INF/resources/")
				.setCachePeriod(31556926); // 1 year
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

}
