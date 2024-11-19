package com.project.dust.domain.board;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BoardEditForm {

    @NotBlank
    private Long boardId;

    @NotBlank
    private Long memberId;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private Long sidoId;




}
