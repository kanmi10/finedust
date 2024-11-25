package com.project.dust.config;

import com.project.dust.board.repository.BoardMapper;
import com.project.dust.board.repository.BoardRepository;
import com.project.dust.board.repository.MyBatisBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BoardConfig {

    private final BoardMapper boardMapper;

    @Bean
    public BoardRepository boardRepository() {
        return new MyBatisBoardRepository(boardMapper);
    }

}
