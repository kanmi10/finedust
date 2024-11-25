package com.project.dust.board;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Board {

    private Long boardId;

    private Long sidoId;
    private String sidoName;

    private Long memberId;
    private String name;

    private String title;
    private String content;

    private LocalDateTime createdDate;
}
