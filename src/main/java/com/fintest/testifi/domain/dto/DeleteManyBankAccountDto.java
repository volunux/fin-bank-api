package com.fintest.testifi.domain.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;

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
	private List<Long> ids;
}
