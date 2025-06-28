package dev.application.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 設定クラス
 * @author shiraishitoshio
 *
 */
@Configuration
@ComponentScan(basePackages = {
	"dev.application.domain.service",
	"dev.application.constant",
	"dev.application.common",
	"dev.application.constant"
})
@MapperScan(basePackages = "dev.application.repository")
public class BookMakersConfig {
	
	
}
