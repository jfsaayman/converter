package com.jo.converter.service;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.jo.converter.domain.ConvertionResult;
import com.jo.converter.domain.Formula;
import com.jo.converter.domain.repository.FormulaRepository;
import com.jo.converter.service.exception.ConvertionException;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Converter {

	private final FormulaRepository formulaRepository;

	public Converter(FormulaRepository formulaRepository) {
		this.formulaRepository = formulaRepository;
	}

	public List<Formula> getAllFormula() {
		List<Formula> result;
		try {
			result = formulaRepository.findAll();
		} catch (Exception e) {
			result = null;
			log.error("Unexpected exception retrieving all formula. ", e);
		}
		return result;
	}

	public ConvertionResult convert(String fromType, String toType, Double inputValue) throws ConvertionException {
		Assert.hasText(fromType, "From type is required");
		Assert.hasText(toType, "To type is required");
		Assert.notNull(inputValue, "Input value is required");
		Formula formula;
		try {
			formula = formulaRepository.findByFromTypeAndToType(fromType, toType);
		} catch (Exception e) {
			log.error("Unexpected exception finding formula");
			formula = null;
		}
		Double convertedValue;
		if (formula != null) {
			convertedValue = convert(inputValue, formula.getNumerator(), formula.getDenominator(),
					formula.getToOffset());
		} else {
			throw new ConvertionException("No Formula found. Contact your administrator");
		}
		ConvertionResult result = new ConvertionResult(formula, convertedValue);
		return result;
	}

	protected Double convert(Double inputValue, Double numerator, Double denominator, Double offset)
			throws ConvertionException {
		if (denominator == 0) {
			throw new ConvertionException("Invalid Formula found. Contact your administrator");
		}
		if (offset < 0) {
			return (inputValue + offset) * (numerator / denominator);
		} else {
			return (inputValue * (numerator / denominator)) + offset;
		}
	}
}
