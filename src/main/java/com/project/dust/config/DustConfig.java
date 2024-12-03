package com.project.dust.config;

import com.project.dust.dust.repository.DustMapper;
import com.project.dust.dust.repository.DustRepository;
import com.project.dust.dust.repository.MyBatisDustRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DustConfig {

    private final DustMapper dustMapper;

    @Bean
    public DustRepository dustRepository() {
        return new MyBatisDustRepository(dustMapper);
    }

  /*  @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setConfiguration(myBatisConfig());
        return factoryBean.getObject();
    }

    private org.apache.ibatis.session.Configuration myBatisConfig() {
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setDefaultExecutorType(ExecutorType.BATCH);
        return configuration;
    }*/

}
