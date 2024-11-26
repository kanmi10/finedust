package com.project.dust.config;

import com.project.dust.dust.repository.DustMapper;
import com.project.dust.dust.repository.DustRepository;
import com.project.dust.dust.repository.MyBatisDustRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DustConfig {

    private final DustMapper dustMapper;

    @Bean
    public DustRepository dustRepository() {
        return new MyBatisDustRepository(dustMapper);
    }

}
