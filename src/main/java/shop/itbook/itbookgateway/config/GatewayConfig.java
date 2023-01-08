package shop.itbook.itbookgateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 게이트웨이 라우팅을 위한 설정입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Configuration
public class GatewayConfig {

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

    @Value("${itbook.api.front}")
    private String frontApiPattern;

    @Value("${itbook.api.auth}")
    private String authApiPattern;

    @Value("${itbook.api.shop}")
    private String shopApiPattern;

    @Value("${itbook.api.delivery}")
    private String deliveryApiPattern;

    @Value("${itbook.api.batch}")
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
                .uri(shopServer))
            .route("delivery", r -> r.path(deliveryApiPattern)
                .uri(deliveryServer))
            .route("batch", r -> r.path(batchApiPattern)
                .uri(batchServer))
            .build();
    }
}
