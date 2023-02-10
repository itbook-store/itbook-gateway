package shop.itbook.itbookgateway.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import shop.itbook.itbookgateway.token.dto.TokenDto;

/**
 * 토큰에 대한 토큰 검증과, 복호화를 담당하는 클래스 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Slf4j
@Setter
@ConfigurationProperties(prefix = "jwt")
@Configuration
public class TokenUtil {

    /* TODO -> SecureKeyManager 을 통한 secretKey 관리 */
    private String secretKey;

    private static final String SIGNATURE_EXCEPTION_MESSAGE = "잘못된 토큰 형식입니다.";

    /**
     * 토큰의 검증과, 암호화된 토큰을 복호화 하여, DTO 클래스로 변환하는 메서드 입니다.
     *
     * @param token the token
     * @return the TokenDto
     */
    public TokenDto validateAndExtract(String token) {

        Jws<Claims> claimsJws = Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token);

        if (!checkTokenValidTime(claimsJws.getBody())) {
            throw new SignatureException(SIGNATURE_EXCEPTION_MESSAGE);
        }

        return new TokenDto(
            String.valueOf(claimsJws.getBody().get("memberNo")),
            getAuthorityList(claimsJws)
        );
    }

    private boolean checkTokenValidTime(Claims claims) {
        return ((Integer) claims.get("exp") - (Integer) claims.get("iat")) > 0;
    }

    private List<String> getAuthorityList(Jws<Claims> claimsJws) {
        return ((List<Map<?, ?>>) claimsJws.getBody().get("role")).stream()
            .map(o -> o.get("authority").toString())
            .collect(Collectors.toList());
    }
}
