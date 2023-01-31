package shop.itbook.itbookgateway.filter;

import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import shop.itbook.itbookgateway.exception.InvalidTokenRequestException;
import shop.itbook.itbookgateway.token.TokenUtil;
import shop.itbook.itbookgateway.token.dto.TokenDto;

/**
 * 프론트서버에서 넘어온 요청 헤더의 JWT 토큰을 복호화하고, Back 서버로 전송해주는 필터입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Slf4j
@Component
public class CustomAuthFilter extends AbstractGatewayFilterFactory<CustomAuthFilter.Config> {

    private static final String AUTH_HEADER = "Authorization";
    private static final String HEADER_PREFIX = "Bearer";

    public CustomAuthFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {

            List<String> authHeader = exchange.getRequest().getHeaders().get(AUTH_HEADER);

            /* 일반 요청의 경우 헤더가 존재하지 않을 경우 */
            if (Objects.isNull(authHeader)) {
                return chain.filter(exchange);
            }

            /* 헤더가 존재하지만 비어있을 경우 혹은 PREFIX가 잘못된 경우 잘못된 요청 */
            if (authHeader.isEmpty() || !authHeader.get(0).startsWith(HEADER_PREFIX)) {
                throw new InvalidTokenRequestException();
            }

            /* Barer 제거 후 accessToken 값*/
            String accessToken = authHeader.get(0).substring(7);

            TokenDto tokenDto = config.tokenUtil.validateAndExtract(accessToken);

            sendHeader(exchange, tokenDto);

            return chain.filter(exchange);
        });
    }

    private static void sendHeader(ServerWebExchange exchange, TokenDto tokenDto) {
        exchange.getRequest().mutate()
            .header("Authorization-MemberNo", tokenDto.getPrincipal()).build();
        exchange.getRequest().mutate()
            .header("Authorization-MemberRole", tokenDto.getRole().toString()).build();
    }


    /**
     * Filter에 사용되는 설정을 정의하는 클래스 입니다.
     *
     * @author 강명관
     * @since 1.0
     */
    public static class Config {

        private final TokenUtil tokenUtil;

        public Config(TokenUtil tokenUtil) {
            this.tokenUtil = tokenUtil;
        }
    }

}
