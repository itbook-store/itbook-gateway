package shop.itbook.itbookgateway.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Gateway Exception 에 대해 응답 형식을 CommonResponseBody 로 처리해주기 위한 클래스 입니다.
 *
 * @author 강명관
 * @since 1.0
 */

@Slf4j
@Order(-1)
public class CustomExceptionHandler implements ErrorWebExceptionHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {

        log.error("### Gateway Error ###");

        ServerHttpResponse response = exchange.getResponse();

        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        log.error("error status code {}", response.getStatusCode());

        return null;
    }
}
