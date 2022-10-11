package com.autumnia.junitprj.bookshop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.autumnia.junitprj.bookshop.domain.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
