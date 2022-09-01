package com.autumnia.shop.userservice.common.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties
@Data
@NoArgsConstructor
public class KakaoProperties {
    private String restapi;
    private String javascript;
    private String clientSecret;
}
