package shop.itbook.itbookgateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 잘못된 토큰 형식 요청에 대한 Exception Class
 *
 * @author 강명관
 * @since 1.0
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class InvalidTokenRequestException extends RuntimeException {

    private static final String MESSAGE = "잘못된 토큰 형식에 대한 요청입니다.";

    public InvalidTokenRequestException() {
        super(MESSAGE);
    }
}
