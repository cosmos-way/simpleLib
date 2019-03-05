package com.itmoshop.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.List;
import java.util.Properties;

@Configuration
@ComponentScan({
        "com.itmoshop.data",
        "com.itmoshop.persistence"
})
//Настроки базы данных
@EnableTransactionManagement
@PropertySource("/META-INF/persistence_local.properties")
public class PersistenceConfig {

    @Value("${entityManagerFactory.packagesToScan}")
    private List<String> packagesToScan;
    @Value("${properties.keys}")
    private List<String> propertiesKeys;
    @Value("${properties.values}")
    private List<String> propertiesValues;
    @Value("${jpaVendorAdapter.databasePlatform}")
    private String databasePlatform;
    @Value("${dataSource.driverClassName}")
    private String driverClassName;
    @Value("${dataSource.protocol}")
    private String protocol;
    @Value("${dataSource.host}")
    private String host;
    @Value("${dataSource.port}")
    private String port;
    @Value("${dataSource.dbName}")
    private String dbName;
    @Value("${dataSource.username}")
    private String username;
    @Value("${dataSource.password}")
    private String password;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory
            (DataSource dataSource, JpaVendorAdapter jpaVendorAdapter, Properties properties) {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource);
        entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter);
        entityManagerFactory.setJpaProperties(properties);
        entityManagerFactory.setPackagesToScan(packagesToScan.toArray(new String[]{}));

        return entityManagerFactory;
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    //Создаём адаптер
    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setShowSql(true);
        adapter.setGenerateDdl(false);
        adapter.setDatabasePlatform(databasePlatform);

        return adapter;
    }

    //Источник
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(driverClassName);
        ds.setUrl(String.format("%s//%s:%s/%s", protocol, host, port, dbName));
        ds.setUsername(username);
        ds.setPassword(password);

        return ds;
    }


    //Настройки
    @Bean
    public Properties properties() {
        Properties properties = new Properties();
        for (int i = 0; i < propertiesKeys.size(); i++) {
            properties.put(propertiesKeys.get(i), propertiesValues.get(i));
        }
        return properties;
    }
}