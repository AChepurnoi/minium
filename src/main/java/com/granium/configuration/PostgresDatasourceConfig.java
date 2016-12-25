package com.granium.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

/**
 * Created by Sasha.Chepurnoi on 06.12.16.
 */
@Configuration
@EnableJpaRepositories(
        basePackages = "com.granium.domain.psql.repository",
        entityManagerFactoryRef = "psqlEntityManager",
        transactionManagerRef = "psqlTransactionManager"
)
public class PostgresDatasourceConfig {


    private String schemaUpdate = "none";
    private String hiberDialect = "org.hibernate.dialect.PostgreSQLDialect";
    private String driver = "org.postgresql.Driver";
    @Value("${spring.datasource.url:NULL}")
    private String url;

    @Value("${spring.datasource.username:NULL}")
    private String user;

    @Value("${spring.datasource.password:NULL}")
    private String pass;

    @Bean
    public LocalContainerEntityManagerFactoryBean psqlEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(psqlDataSource());
        em.setPackagesToScan("com.granium.domain.psql.entity");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", schemaUpdate);
        properties.put("hibernate.dialect", hiberDialect);
        properties.put("hibernate.show_sql", "true");
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean
    public DataSource psqlDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(pass);

        return dataSource;
    }

    @Bean
    public PlatformTransactionManager psqlTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(psqlEntityManager().getObject());
        return transactionManager;
    }
}