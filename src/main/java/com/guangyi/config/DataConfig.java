package com.guangyi.config;

import com.github.miemiedev.mybatis.paginator.OffsetLimitInterceptor;
import com.github.miemiedev.mybatis.paginator.dialect.MySQLDialect;
import com.guangyi.base.MyBatisRepository;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by zhongjing on 5/12/16.
 */
@Configuration
public class DataConfig {

    @Bean
    public DataSource dataSource(
            @Value("${db.driver}") String driver,
            @Value("${db.url}") String url,
            @Value("${db.username}") String username,
            @Value("${db.password}") String password
    ) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }

    /*
    mybatis configuration start
     */
    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource, ApplicationContext applicationContext) throws IOException {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();

        OffsetLimitInterceptor offsetLimitInterceptor = new OffsetLimitInterceptor();
        offsetLimitInterceptor.setDialect(new MySQLDialect());

        sqlSessionFactory.setPlugins(new Interceptor[] {offsetLimitInterceptor});

        sqlSessionFactory.setDataSource(dataSource);
        sqlSessionFactory.setTypeAliasesPackage("com.guangyi.entity");
//        sqlSessionFactory.setMapperLocations(applicationContext.getResources("classpath:/mappers/*-mapper.xml"));
        Properties properties = new Properties();
        properties.put("mapUnderscoreToCamelCase", true);
        sqlSessionFactory.setConfigurationProperties(properties);
        return sqlSessionFactory;
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.guangyi");
        mapperScannerConfigurer.setAnnotationClass(MyBatisRepository.class);
        return mapperScannerConfigurer;
    }
}
