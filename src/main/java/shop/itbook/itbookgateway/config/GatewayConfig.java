package shop.itbook.itbookgateway.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import shop.itbook.itbookgateway.exception.handler.CustomExceptionHandler;
import shop.itbook.itbookgateway.filter.CustomAuthFilter;
import shop.itbook.itbookgateway.token.TokenUtil;

/**
 * 게이트웨이 라우팅을 위한 설정입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Configuration
public class GatewayConfig {

    private final TokenUtil tokenUtil;

    @Value("${itbook.front.server}")
    private String frontServer;

    @Value("${itbook.auth.server}")
    private String authServer;

    @Value("${itbook.shop.server}")
    private String shopServer;

    @Value("${itbook.delivery.server}")
    private String deliveryServer;

    @Value("${itbook.batch.server}")
    private String batchServer;

    @Value("${itbook.shop-load.server}")
    private String shopLoadServer;

    @Value("${itbook.front.api}")
    private String frontApiPattern;

    @Value("${itbook.auth.api}")
    private String authApiPattern;

    @Value("${itbook.shop.api}")
    private String shopApiPattern;

    @Value("${itbook.delivery.api}")
    private String deliveryApiPattern;

    @Value("${itbook.batch.api}")
    private String batchApiPattern;

    /**
     * 라우팅을 위한 메서드 입니다.
     *
     * @param builder the builder
     * @return the route locator
     */
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("front", r -> r.path(frontApiPattern)
                .uri(frontServer))
            .route("auth", r -> r.path(authApiPattern)
                .uri(authServer))
            .route("shop", r -> r.path(shopApiPattern)
                .filters(f -> f.filter(
                    new CustomAuthFilter().apply(new CustomAuthFilter.Config(tokenUtil))))
                .uri(shopLoadServer))
            .route("delivery", r -> r.path(deliveryApiPattern)
                .uri(deliveryServer))
            .route("batch", r -> r.path(batchApiPattern)
                .uri(batchServer))
            .build();
    }

    @Bean
    public ErrorWebExceptionHandler customExceptionHandler() {
        return new CustomExceptionHandler();
    }


}
