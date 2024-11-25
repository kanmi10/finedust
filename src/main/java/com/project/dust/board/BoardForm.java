package com.project.dust.board;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BoardForm {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private Long sidoId;

}
