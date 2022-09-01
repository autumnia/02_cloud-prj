package com.autumnia.shop.userservice.common.confiruration;

import com.autumnia.shop.userservice.common.properties.KakaoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableConfigurationProperties({KakaoProperties.class})
@PropertySource({"classpath:properties/kakao.properties",
        "classpath:properties/facebook.properties"})
public class PropertiesConfig {

}
