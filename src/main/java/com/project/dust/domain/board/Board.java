package com.project.dust.domain.board;

import lombok.Data;

@Data
public class Board {

    private Long boardId;

    private Long sidoId;
    private String sidoName;

    private Long memberId;
    private String name;

    private String title;
    private String content;

}
