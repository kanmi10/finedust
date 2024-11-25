package com.project.dust.board;

import lombok.Getter;

@Getter
public enum Type {
    TITLE(1, "title"),
    CONTENT(2, "content"),
    WRITER(3, "name"),
    TITLE_CONTENT(4, "title_content");

    private final int typeNumber;
    private final String typeName;

    Type(int typeNumber, String typeName) {
        this.typeNumber = typeNumber;
        this.typeName = typeName;
    }
}
