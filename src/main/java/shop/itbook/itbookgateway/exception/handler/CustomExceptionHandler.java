package shop.itbook.itbookgateway.exception.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import shop.itbook.itbookgateway.common.dto.response.CommonResponseBody;

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

        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        CommonResponseBody<Void> exceptionResponse = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(
                ex.getMessage()), null);

        ObjectMapper objectMapper = new ObjectMapper();

        String error = "Gateway Error";
        try {
            error = objectMapper.writeValueAsString(exceptionResponse);
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException {}", e.getMessage());
        }

        log.error("exceptionResponse {}", exceptionResponse);

        byte[] bytes = error.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        response.setStatusCode(HttpStatus.FORBIDDEN);

        return response.writeWith(Flux.just(buffer));
    }
}
