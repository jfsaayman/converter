package com.jo.converter.domain;

import lombok.Data;

@Data
public class ConvertionResult {
	
	private Formula formula;
	private Double value;
	
	public ConvertionResult(Formula formula, Double value) {
		super();
		this.formula = formula;
		this.value = value;
	}
	
}
