package com.autumnia.junitprj.bookshop.web.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class BookListResponseDto {
    List<BookResponseDto> books;

    @Builder
    public BookListResponseDto(List<BookResponseDto> bookList) {
        this.books = bookList;
    }
}
