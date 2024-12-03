package com.project.dust.board;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter @Setter @ToString
public class Board {

    private Long boardId;

    private Long sidoId;
    private String sidoName;

    private Long memberId;
    private String name;

    private String title;
    private String content;

    private LocalDateTime createdDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return Objects.equals(boardId, board.boardId) && Objects.equals(sidoId, board.sidoId) && Objects.equals(memberId, board.memberId) && Objects.equals(title, board.title) && Objects.equals(content, board.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(boardId, sidoId, memberId, title, content);
    }
}
