package shop.itbook.itbookgateway.token.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author 강명관
 * @since 1.0
 */

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TokenDto {
    private String principal;
    private List<String> role;
}
