package com.fintest.testifi.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
	info = @Info(
		title = "Fintest financial Rest Api (fintestifi.io)",
		description = "Fintest is a financial service and bank application that allows the user to deposit, "
				+ "withdraw money from their bank account. It also allows transfer of money from one customer bank account to another.",
	contact = @Contact(
		email = "info@fintestifi.io",
		url = "festifi.io",
		name = "Fintestifi"
			),
	license = @License(
		name = "MIT License",
		url = "http://github.com/fintestifi"
			)
		),
	servers = @Server(url = "http://localhost:8088")
	)
@SecurityScheme(
	name = "api",
	scheme = "basic",
	type = SecuritySchemeType.HTTP,
	in = SecuritySchemeIn.HEADER
)
public class FintestApiConfiguration {

}
