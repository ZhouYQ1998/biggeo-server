package edu.zju.gis.dldsj.server.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @author Keran Sun (katus)
 * @version 1.0, 2020-11-23
 */
@Configuration
@MapperScan(basePackages = "edu.zju.gis.dldsj.server.mapper.pg", sqlSessionFactoryRef = "postgresqlSqlSessionFactory")
public class PostgreSQLConfig {
    @Bean(name = "postgresqlDataSource")
    @ConfigurationProperties("spring.datasource.postgresql")
    public DataSource postgresqlDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "postgresqlSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("postgresqlDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/pg/*.xml"));
        return sessionFactoryBean.getObject();
    }
}
