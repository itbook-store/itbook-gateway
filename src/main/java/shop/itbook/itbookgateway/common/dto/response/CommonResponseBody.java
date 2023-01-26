package shop.itbook.itbookgateway.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 프로젝트 공통 응답객체 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponseBody<T> {
    private CommonHeader header;

    private T result;

    /**
     * 프로젝트 공통 응답객체의 헤더 부분입니다.
     *
     * @author 강명관
     * @since 1.0
     */
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class CommonHeader {

        private String resultMessage;
    }
}
