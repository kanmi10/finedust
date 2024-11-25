package com.project.dust.board;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BoardSearchDTO {
    private Long sidoId;
    private Integer searchType;
    private String searchWord;
    private int limit;
    private int offset;

    public BoardSearchDTO(Long sidoId, Integer searchType, String searchWord, int limit, int offset) {
        this.sidoId = sidoId;
        this.searchType = searchType;
        this.searchWord = searchWord;
        this.limit = limit;
        this.offset = offset;
    }
}
