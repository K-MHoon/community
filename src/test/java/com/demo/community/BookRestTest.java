package com.demo.community;

import com.demo.community.domain.Book;
import com.demo.community.service.BookRestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.HttpServerErrorException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RestClientTest(BookRestService.class)
@AutoConfigureDataJpa
public class BookRestTest {

    @Autowired
    private BookRestService bookRestService;

    @Autowired
    private MockRestServiceServer server;

    /**
     * 요청에 대해 응답과 기댓값이 같은지 테스트 한다.
     * '/rest/test' 경로로 요청을 보내면, 현재 리소스 폴더에 생성되어 있는 test.json 파일의 데이터로 응답을 주도록 설정한다.
     * 그리고 bookService의 getRestBook() 메서드를 실행하여, 컨트롤러에서 가져온 test.json 데이터와 가져온 Book 값이 일치하는지 확인한다.
     */
    @Test
    public void rest_테스트() {
        this.server.expect(MockRestRequestMatchers.requestTo("/rest/test"))
                .andRespond(MockRestResponseCreators.withSuccess(new ClassPathResource("/test.json", getClass()), MediaType.APPLICATION_JSON));
        Book book = this.bookRestService.getRestBook();
        assertThat(book.getTitle()).isEqualTo("테스트");
    }

    /**
     * 서버 에러가 발생했을 경우 테스트
     * HTTP 500 에러 발생시 예외 발생
     */
    @Test
    public void rest_error_테스트() {
        assertThrows(HttpServerErrorException.class, () -> {
            this.server.expect(MockRestRequestMatchers.requestTo("/rest/test"))
                    .andRespond(MockRestResponseCreators.withServerError());
            this.bookRestService.getRestBook();
        });
    }
}
