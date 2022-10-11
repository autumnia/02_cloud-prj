package com.autumnia.junitprj.bookshop.domain;

import com.autumnia.junitprj.bookshop.domain.model.Book;
import com.autumnia.junitprj.bookshop.domain.repository.BookRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ActiveProfiles("dev")
@DataJpaTest
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private EntityManager em;

    @BeforeEach
    public void 데이터_한건_매번준비() {
        System.out.println("+++++++++ BeforEach 시작 ++++++++++++++++++");
        String title = "책제목";
        String author = "책저자";

        Book saveBook = Book.builder()
                .title(title)
                .author(author)
                .build();
        bookRepository.save(saveBook);

        System.out.println("+++++++++ BeforEach 종료 ++++++++++++++++++");
    }


    @Test
    @DisplayName("책등록테스트")
    public void create_book_test() {
        // given
        String title = "책제목";
        String author = "책저자";

        Book newBook = Book.builder()
                .title(title)
                .author(author)
                .build();

        // when
        Book bookSaved = bookRepository.save(newBook);

        // then
        assertEquals("책제목", bookSaved.getTitle());
        assertEquals("책저자", bookSaved.getAuthor());
    }

    @Test
    public void 책목록보기_Test() {
        // given
        String title = "책제목";
        String author = "책저자";

        // when
        List<Book> books = bookRepository.findAll();

        // then
        System.out.println("사이즈: " + books.size() );
        System.out.println(books.get(0).getTitle());
        assertEquals(title, books.get(0).getTitle());
    }

    @Sql("classpath:db/tableInit.sql")
    @Test
    void 책한권보기_Test() {
        //given
        String title = "책제목";
        String author = "책저자";

        // when
        Optional<Book> book = bookRepository.findById(1L);

        // then
        System.out.println(book.get().getTitle());
        assertEquals(title, book.get().getTitle() );
    }

    @Sql("classpath:db/tableInit.sql")
    @Test
    void 책삭제_Test() {
        // given
        Long id = 1L;

        // when
        bookRepository.deleteById(id);

        // then
        assertFalse(bookRepository.findById(id).isPresent());
    }

    @Sql("classpath:db/tableInit.sql")
    @Test
    void 책수정_Test() {
        // given
        Long id = 1L;
        String title = "책제목2";
        String author = "책저자2";
        Book updateBook = Book.builder()
                .id(id)
                .title(title)
                .author(author)
                .build();

        // when
        Book bookSaved = bookRepository.save(updateBook);
//        bookRepository.findAll()
//                .stream()
//                .forEach( (book) -> {
//                    System.out.println("ID: " + book.getId());
//                    System.out.println("저자: " + book.getAuthor());
//                    System.out.println("제목: " + book.getTitle());
//                });

        // then
        assertEquals(id, bookSaved.getId());
    }
}
