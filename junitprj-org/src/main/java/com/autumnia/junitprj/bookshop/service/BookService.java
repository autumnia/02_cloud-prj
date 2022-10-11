package com.autumnia.junitprj.bookshop.service;

import com.autumnia.junitprj.bookshop.domain.model.Book;
import com.autumnia.junitprj.bookshop.domain.repository.BookRepository;
import com.autumnia.junitprj.bookshop.utils.MailSender;
import com.autumnia.junitprj.bookshop.web.dto.request.BookRequestDto;
import com.autumnia.junitprj.bookshop.web.dto.response.BookListResponseDto;
import com.autumnia.junitprj.bookshop.web.dto.response.BookResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final MailSender mailSender;

    // 책등록하기
    @Transactional(rollbackOn = RuntimeException.class )
    public BookResponseDto registBook(BookRequestDto bookRequestDto) {
        Book bookSaved = bookRepository.save(bookRequestDto.toEntity());
        if ( bookSaved != null ) {
            if ( !mailSender.send() ) {
                throw new RuntimeException("메일이 전송되지 않았습니다. ");
            }
        }


        return bookSaved.toDto();
    }

//    @Transactional(rollbackOn = RuntimeException.class )
//    public BookResponseDto registBook2(BookRequestDto bookRequestDto) {
//        Book newBook = ObjectUtils.toModel(bookRequestDto, Book.class);
//        Book bookSaved = bookRepository.save(newBook);
//        if ( bookSaved != null ) {
//            if ( !mailSender.send() ) {
//                throw new RuntimeException("메일이 전송되지 않았습니다. ");
//            }
//        }
//
//        BookResponseDto bookResponseDto = ObjectUtils.toModel(bookSaved, BookResponseDto.class);
//
//        return bookResponseDto ;
//    }

    // 책 여러건 보기
    public BookListResponseDto showBookList() {
        List<BookResponseDto> bookList = bookRepository.findAll()
                .stream()
                .map(Book::toDto)
                .collect(Collectors.toList());

        BookListResponseDto bookListResponseDto = BookListResponseDto.builder()
                .bookList(bookList)
                .build();

        return bookListResponseDto;
    }

//    public List<BookResponseDto> showBookList() {
//        return  bookRepository.findAll().stream()
//                .map( Book::toDto )
//                //.map( (bookSaved) -> new BookResponseDto().toDto(bookSaved))
//                .collect(Collectors.toList());
//    }

    // 책 한건 보기
    public BookResponseDto getBook(Long id ) {
        Optional<Book> bookSaved = bookRepository.findById(id);
        if ( bookSaved.isPresent() ) {
            return bookSaved.get().toDto() ;
        } else {
            throw new RuntimeException( "해당 아이디를 찾을 수 없습니다. id: " + id );
        }
    }

    // 책 삭제하기
    @Transactional(rollbackOn = RuntimeException.class)
    public void deleteBook(Long id) {
        // 다른 로직 있을 수 있음
        bookRepository.deleteById(id);
    }

    // 책 수정하기
    @Transactional(rollbackOn = RuntimeException.class)
    public BookResponseDto updateBook(Long id, BookRequestDto bookRequestDto) {
        Optional<Book> bookSaved = bookRepository.findById(id);
        if (bookSaved.isPresent()) {
            Book updateBook = bookSaved.get();
            updateBook.update( bookRequestDto.getTitle(), bookRequestDto.getAuthor());
        } else {
            new RuntimeException("해당 아이디를 찾을 수 없습니다. id: " + id);
        }
        return bookSaved.get().toDto();
    }

}
