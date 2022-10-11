package com.autumnia.junitprj.bookshop.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.autumnia.junitprj.bookshop.web.dto.response.BookResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Book {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 20, nullable = false)
    private String author;

    @Builder
    public Book(long id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public void update( String title, String author ) {
        this.title = title;
        this.author = author;
    }

    public BookResponseDto toDto() {
        return  BookResponseDto.builder()
                .id(id)
                .title(title)
                .author(author)
                .build();

    }
}
