package com.fintest.testifi.domain.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class DeleteManyBankAccountDto {

	@NotEmpty(message = "{deleteIds.notEmpty}")
	@Schema(description = "The ids of the bank account to delete",
		name ="ids",
		required = true,
		example = "[1, 2, 3]")
	private List<Long> ids;
}
