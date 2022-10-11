package com.autumnia.junitprj.bookshop.service;

import com.autumnia.junitprj.bookshop.domain.model.Book;
import com.autumnia.junitprj.bookshop.domain.repository.BookRepository;
import com.autumnia.junitprj.bookshop.utils.MailSender;
import com.autumnia.junitprj.bookshop.web.dto.request.BookRequestDto;
import com.autumnia.junitprj.bookshop.web.dto.response.BookListResponseDto;
import com.autumnia.junitprj.bookshop.web.dto.response.BookResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ActiveProfiles("dev")
@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private MailSender mailSender;

    @Test
    public void 책등록_테스트() {
        // given
        BookRequestDto bookRequestDto = new BookRequestDto();
        bookRequestDto.setTitle("잭체목");
        bookRequestDto.setAuthor("홍길동");

        //stub mock 처리
        when(bookRepository.save(any())).thenReturn(bookRequestDto.toEntity());
        when(mailSender.send()).thenReturn(true);

        // when
        BookResponseDto bookResponseDto = bookService.registBook(bookRequestDto);

        // then
        assertThat(bookResponseDto.getTitle()).isEqualTo(bookRequestDto.getTitle());
        assertThat(bookResponseDto.getAuthor()).isEqualTo(bookRequestDto.getAuthor());
    }

    @Test
    public void 책목록보기_테스트() {
        // given

        // stub
        List<Book> books = new ArrayList<>();
        books.add( new Book(1L, "책제목1", "저자1") );
        books.add( new Book(2L, "책제목2", "저자2") );
        when(bookRepository.findAll()).thenReturn(books);

        // when
        BookListResponseDto bookListResponseDto = bookService.showBookList();

        // then
        assertThat( bookListResponseDto.getBooks().get(0).getTitle() ).isEqualTo("책제목1");
        //assertThat( bookResponseDtos.get(0).getTitle()).isEqualTo("책제목1");

    }

    @Test
    public void 책한권보기_테스트() {
        //given
        Long id = 1L;
        Book aBook = new Book(id, "책제목1", "저자1");
        Optional<Book> bookSaved = Optional.of(aBook);

        // stub
        when(bookRepository.findById(id)).thenReturn(bookSaved);

        // when
        BookResponseDto bookResponseDto = bookService.getBook(id);

        // then
        assertThat( bookResponseDto.getTitle()).isEqualTo(aBook.getTitle());
    }

    @Test
    public void 책수정하기_테스트() {
        // given
        Long id = 1L;
        BookRequestDto bookRequestDto = new BookRequestDto();
        bookRequestDto.setTitle("잭제목2");
        bookRequestDto.setAuthor("책저자2");

        // stub
        Book aBook = new Book(id, "제목1", "저자1");
        Optional<Book> bookSaved = Optional.of( aBook );
        when( bookRepository.findById(id)).thenReturn(bookSaved);

        // when
        BookResponseDto bookResponseDtoUpdated = bookService.updateBook(id, bookRequestDto);

        // then
        assertThat(bookResponseDtoUpdated.getTitle()).isEqualTo( bookRequestDto.getTitle());
    }

    @Test
    public void 책삭제하기_테스트() {
        // given
//        BookRequestDto bookRequestDto = new BookRequestDto();
//        bookRequestDto.setTitle("잭체목");
//        bookRequestDto.setAuthor("홍길동");

        // stub
//        when(bookRepository.save(any())).thenReturn(bookRequestDto.toEntity());
//        when(mailSender.send()).thenReturn(true);

        // when
//        bookService.
//        BookResponseDto bookResponseDto = bookService.deleteBook();

        // then
    }

}