package com.autumnia.junitprj.bookshop.web.dto.request;

import com.autumnia.junitprj.bookshop.domain.model.Book;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
public class BookRequestDto {
    @Size(min=1, max=50)
    @NotBlank
    private String title;
    @Size(min=2, max=20)
    @NotBlank
    private String author;

    @Builder
    public BookRequestDto(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public Book toEntity() {
        Book book = Book.builder()
                .title(this.title)
                .author(this.author)
                .build();
        return book;
    }

}
