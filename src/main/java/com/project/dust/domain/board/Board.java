package com.project.dust.domain.board;

import lombok.Data;

@Data
public class Board {

    private Long boardId;
    private Long sidoId;
    private Long memberId;
    private String title;
    private String content;

}
