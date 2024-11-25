package com.project.dust.board;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class Page<T> {
    private List<T> content;    // 게시물 리스트
    private int pageNumber;     // 페이지 번호
    private int pageSize;       // 페이지 당 게시물 수
    private int totalElements;  // 총 게시물 수
    private int totalPages;     // (총 게시물 수) / (페이지 당 게시물 수) = 총 페이지 번호

    private int groupSize;      // 한 번에 보여줄 페이지 버튼 수
    private int currentGroup;   // 현재 그룹 번호
    private int startPage;      // 그룹의 첫 페이지 번호
    private int endPage;        // 그룹의 마지막 페이지 번호

    private Long sidoId;        // 지역번호

    public Page(List<T> content, int pageNumber, int pageSize, int totalElements, Long sidoId) {
        this.content = content;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = (int) Math.ceil((double) totalElements / pageSize); // 올림

        this.groupSize = 10;
        this.currentGroup = (pageNumber - 1) / groupSize;
        this.startPage = currentGroup * groupSize + 1;
        this.endPage = Math.min(startPage + groupSize - 1, totalPages);
        this.sidoId = sidoId;
    }
}
