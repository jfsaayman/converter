package com.jo.converter.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Formula {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String fromType;
	private String toType;
	private Double numerator;
	private Double denominator;
	private Double toOffset;

	protected Formula() {
	}

	public Formula(String fromType, String toType, Double numerator, Double denominator, Double toOffset) {
		this.fromType = fromType;
		this.toType = toType;
		this.numerator = numerator;
		this.denominator = denominator;
		this.toOffset = toOffset;
	}

	@Override
	public String toString() {
		return String.format(
				"Formula[id=%d, fromType='%s', toType='%s', numerator='%numerator', denominator='%denominator', offset='%offset']",
				id, fromType, toType, numerator, denominator, toOffset);
	}

}