package com.autumnia.junitprj.bookshop.web.controllers;

import com.autumnia.junitprj.bookshop.domain.model.Book;
import com.autumnia.junitprj.bookshop.domain.repository.BookRepository;
import com.autumnia.junitprj.bookshop.service.BookService;
import com.autumnia.junitprj.bookshop.web.dto.request.BookRequestDto;
import com.autumnia.junitprj.bookshop.web.dto.response.BookResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

// 통합테스트
@ActiveProfiles("dev")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookApiControllerTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private TestRestTemplate rt;

    @Autowired
    private BookRepository bookRepository;
    private static ObjectMapper om;
    private static HttpHeaders headers;

    @BeforeAll
    public static void fromClient() {
        om = new ObjectMapper();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @BeforeEach
    public void 데이터준비() {
        String title = "책제목";
        String author = "책저자";

        Book newBook = Book.builder()
                .title(title)
                .author(author)
                .build();
        Book bookSaved = bookRepository.save(newBook);
    }

    @Sql("classpath:db/tableInit.sql")  // 늘 초기화 해야 에러가 빌생하지 않는다.
    @Test
    public void modifyBookTest() throws Exception {
        // given
        Integer id = 1;
        BookRequestDto bookRequestDto = BookRequestDto.builder()
                .title("책제목2")
                .author("책저자2")
                .build();
        String body = om.writeValueAsString( bookRequestDto );
        System.out.println("given body: " + body);

        // when
        HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> responseEntity = rt.exchange("/api/v1/book/" + id, HttpMethod.PUT, requestEntity, String.class );
        System.out.println( "when: " + responseEntity.getBody().toString());

        // then
        DocumentContext dc = JsonPath.parse(responseEntity.getBody());
        Integer code = dc.read("$.code");
        String title = dc.read( "$.body.title");
        String author = dc.read( "$.body.author");

        assertThat(code).isEqualTo(1);
        assertThat(title).isEqualTo("책제목2");
        assertThat(author).isEqualTo("책저자2");
    }

    @Sql("classpath:db/tableInit.sql")  // 늘 초기화 해야 에러가 빌생하지 않는다.
    @Test
    public void deleteBookTest() {
        // given
        Integer id = 1;

        // when
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<String> responseEntity = rt.exchange("/api/v1/book/" + id, HttpMethod.DELETE, requestEntity, String.class );
        System.out.println( responseEntity.getBody().toString());

        // then
        System.out.println("then: " + responseEntity.getStatusCode());
        System.out.println("then: " + responseEntity.getStatusCodeValue());

        DocumentContext dc = JsonPath.parse(responseEntity.getBody());
        Integer code = dc.read("$.code");

        assertThat(code).isEqualTo(1);
    }

    @Sql("classpath:db/tableInit.sql")  // 늘 초기화 해야 에러가 빌생하지 않는다.
    @Test
    public void getBookTest() {
        // given
        Integer id = 1;

        // when
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<String> responseEntity = rt.exchange("/api/v1/book/" + id, HttpMethod.GET, requestEntity, String.class );
        System.out.println( responseEntity.getBody().toString());

        // then
        DocumentContext dc = JsonPath.parse(responseEntity.getBody());
        Integer code = dc.read("$.code");
        String title = dc.read( "$.body.title");

        assertThat(code).isEqualTo(1);
        assertThat(title).isEqualTo("책제목");
    }


    @Sql("classpath:db/tableInit.sql")  // 늘 초기화 해야 에러가 빌생하지 않는다.
    @Test
    public void getBookListTest() throws Exception {
        // given

        // when
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<String> responseEntity = rt.exchange("/api/v1/book/list", HttpMethod.GET, requestEntity, String.class );
//        System.out.println( responseEntity.getBody().toString());

        // then
        DocumentContext dc = JsonPath.parse(responseEntity.getBody());
        Integer code = dc.read("$.code");
        String title = dc.read( "$.body.books[0].title");

        assertThat(code).isEqualTo(1);
        assertThat(title).isEqualTo("책제목");
    }

    @Test
    public void createBookTest() throws Exception {
        // given
        BookRequestDto bookRequestDto = new BookRequestDto();
        bookRequestDto.setTitle("book01");
        bookRequestDto.setAuthor("author01");
        String body = om.writeValueAsString( bookRequestDto );
        System.out.println("body: " + body);

        // when
        HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> responseEntity = rt.exchange("/api/v1/book", HttpMethod.POST, requestEntity, String.class );
        System.out.println(responseEntity);

        // then
        DocumentContext dc = JsonPath.parse(responseEntity.getBody());
        String title = dc.read("$.body.title");
        String author = dc.read("$.body.author");
        assertThat(title).isEqualTo(bookRequestDto.getTitle());
    }
}