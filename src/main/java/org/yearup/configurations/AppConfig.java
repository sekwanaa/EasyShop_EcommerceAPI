package org.yearup.configurations;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yearup.data.*;
import org.yearup.data.mysql.*;

import javax.sql.DataSource;

@Configuration
public class AppConfig
{
    private final BasicDataSource basicDataSource;

    @Bean
    public BasicDataSource dataSource()
    {
        return basicDataSource;
    }

    @Bean
    public ProductDao productDao(DataSource dataSource) {
        return new MySqlProductDao(dataSource);
    }

    @Bean
    public CategoryDao categoryDao(DataSource dataSource) {
        return new MySqlCategoryDao(dataSource);
    }

    @Bean
    public ShoppingCartDao shoppingCartDao(DataSource dataSource) {
        return new MySqlShoppingCartDao(dataSource);
    }

    @Bean
    public ProfileDao profileDao (DataSource dataSource) {
        return new MySqlProfileDao(dataSource);
    }

    @Bean
    public UserDao userDao (DataSource dataSource) {
        return new MySqlUserDao(dataSource);
    }

    @Bean
    public OrderDao orderDao(DataSource dataSource) {
        return new MySqlOrderDao(dataSource);
    }

    @Autowired
    public AppConfig(@Value("${datasource.url}") String url,
                     @Value("${datasource.username}") String username,
                     @Value("${datasource.password}") String password)
    {
        basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(url);
        basicDataSource.setUsername(username);
        basicDataSource.setPassword(password);
    }

}