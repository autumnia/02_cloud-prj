package com.autumnia.junitprj.bookshop.web.controllers;

import com.autumnia.junitprj.bookshop.service.BookService;
import com.autumnia.junitprj.bookshop.web.dto.request.BookRequestDto;
import com.autumnia.junitprj.bookshop.web.dto.response.BookListResponseDto;
import com.autumnia.junitprj.bookshop.web.dto.response.BookResponseDto;
import com.autumnia.junitprj.bookshop.web.dto.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Date;

@Slf4j
@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class BookApiController {
    private final BookService bookService;

    private <T> ResponseDto settingResponse(Integer code, String msg, T body) {
        return ResponseDto.builder()
                .timestamp(new Date())
                .code(code)
                .msg(msg)
                .body( body )
                .build();
    }

    // 책등록
    @PostMapping("/v1/book")
    public ResponseEntity<?> createBook(@RequestBody @Valid BookRequestDto bookRequestDto, BindingResult bindingResult)  {
        if ( bindingResult.hasErrors() ) {
            throw new RuntimeException( "책 저장 에러: " + bindingResult.toString() );
        }

        BookResponseDto bookResponseDto = bookService.registBook(bookRequestDto);
        ResponseDto responseDto = settingResponse(1, "책저장 성공", bookResponseDto);

//        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(bookResponseDto.getId())
//                .toUri();
//        ResponseEntity.created(location).build();

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
    
    // 책목록 보기
    @GetMapping("/v1/book/list")
    public ResponseEntity<?> getBookList() {
        BookListResponseDto bookListResponseDto = bookService.showBookList();
        ResponseDto<?> responseDto = settingResponse(1, "책목록 보기 성공", bookListResponseDto);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 책한권 보기
    @GetMapping("/v1/book/{id}")
    public ResponseEntity<?> getBookOne(@PathVariable Long id) {
        BookResponseDto bookResponseDto = bookService.getBook(id);
        ResponseDto<?> responseDto = settingResponse(1, "책 한권 보기 성공", bookResponseDto);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 책 수정하기
    @PutMapping("/v1/book/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id, @RequestBody @Valid BookRequestDto bookRequestDto, BindingResult bindingResult) {
        if ( bindingResult.hasErrors() ) {
            throw new RuntimeException( "책 수정 에러: " + bindingResult.toString() );
        }

        BookResponseDto bookResponseDto = bookService.updateBook(id, bookRequestDto);
        ResponseDto<?> responseDto = settingResponse(1, "책 한권 수정 성공", bookResponseDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 책 삭제하기
    @DeleteMapping("/v1/book/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        ResponseDto<?> responseDto = settingResponse(1, "책 한권 삭제 성공", null);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}
