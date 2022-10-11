package com.autumnia.junitprj.bookshop.web.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor
public class BookResponseDto {
    private Long id;
    private String title;
    private String author;

    @Builder
    public BookResponseDto(Long id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }
}
