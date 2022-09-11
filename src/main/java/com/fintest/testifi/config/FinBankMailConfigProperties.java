package com.fintest.testifi.config;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "mail")
public class FinBankMailConfigProperties {

	@NotBlank
	private String host;

	@NotNull
	@Min(500)
	@Max(65536)
	private Integer port;

	@NotBlank
	private String username;

	@NotBlank
	private String password;

	@NotBlank
	private String protocol;

	@NotNull
	private String auth;

	@NotNull
	private String tls;

	@NotNull
	private String debug;

}
