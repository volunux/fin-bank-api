package com.fintest.testifi.domain.dto;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class DeleteManyBankAccountDto {

	private List<Long> ids;
}
