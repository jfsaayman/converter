package com.jo.converter.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.jo.converter.domain.ConvertionResult;
import com.jo.converter.domain.Formula;
import com.jo.converter.domain.repository.FormulaRepository;
import com.jo.converter.service.exception.ConvertionException;

@RunWith(SpringRunner.class)
public class ConverterTest {

	@MockBean
	private FormulaRepository formulaRepository;
	
	private Converter converter;

	@Before
	public void setup() {
		this.converter = new Converter(formulaRepository);
		reset(formulaRepository);
	}

	@Test
	public void getAllFormula() {
		List<Formula> formulas = new ArrayList<Formula>();
		Formula formula = new Formula("mile", "km", 8D, 5D, 0D);
		formulas.add(formula);
		Mockito.when(formulaRepository.findAll()).thenReturn(formulas);

		List<Formula> results = converter.getAllFormula();
		assertNotNull(results);
		assertEquals(1, results.size());
	}

	@Test
	public void convert() throws ConvertionException {
		Formula formula = new Formula("mile", "km", 8D, 5D, 0D);
		Mockito.when(formulaRepository.findByFromTypeAndToType("mile", "km")).thenReturn(formula);

		ConvertionResult result = converter.convert("mile", "km", 5D);
		assertNotNull(result);
		assertEquals(8D, result.getValue(), 0.0001D);
	}

	@Test(expected = IllegalArgumentException.class)
	public void convert_null_value() throws ConvertionException {
		Formula formula = new Formula("mile", "km", 8D, 5D, 0D);
		Mockito.when(formulaRepository.findByFromTypeAndToType("mile", "km")).thenReturn(formula);

		converter.convert("mile", "km", null);
		verifyZeroInteractions(formulaRepository);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void convert_null_fromType() throws ConvertionException {
		Formula formula = new Formula("mile", "km", 8D, 5D, 0D);
		Mockito.when(formulaRepository.findByFromTypeAndToType("mile", "km")).thenReturn(formula);

		converter.convert(null, "km", 1D);
		verifyZeroInteractions(formulaRepository);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void convert_null_toType() throws ConvertionException {
		Formula formula = new Formula("mile", "km", 8D, 5D, 0D);
		Mockito.when(formulaRepository.findByFromTypeAndToType("mile", "km")).thenReturn(formula);

		converter.convert("mile", null, 1D);
		verifyZeroInteractions(formulaRepository);
	}
	
	@Test
	public void convert_zero_value() throws ConvertionException {
		Formula formula = new Formula("mile", "km", 8D, 5D, 0D);
		Mockito.when(formulaRepository.findByFromTypeAndToType("mile", "km")).thenReturn(formula);

		ConvertionResult result = converter.convert("mile", "km", 0D);
		assertNotNull(result);
		assertEquals(0D, result.getValue(), 0.0001D);
	}

	@Test(expected = ConvertionException.class)
	public void convert_formula_not_found() throws ConvertionException {
		Mockito.when(formulaRepository.findByFromTypeAndToType("mile", "km")).thenReturn(null);
		converter.convert("mile", "km", 5D);
		verifyNoMoreInteractions(formulaRepository);
	}
	
	@Test(expected = ConvertionException.class)
	public void convert_formula_exception() throws ConvertionException {
		Mockito.when(formulaRepository.findByFromTypeAndToType("mile", "km")).thenThrow(new RuntimeException());
		converter.convert("mile", "km", 5D);
		verifyNoMoreInteractions(formulaRepository);
	}

	@Test
	public void getAllFormula_repositoryException() {
		Mockito.when(formulaRepository.findAll()).thenThrow(new RuntimeException());

		List<Formula> results = converter.getAllFormula();
		assertNull(results);
	}

	@Test(expected = ConvertionException.class)
	public void convert_denominator_0() throws ConvertionException {
		converter.convert(100D, 5D, 0D, -32D);
	}
	
	@Test
	public void convert_fahrenheit_to_celcius() throws ConvertionException {
		double result = converter.convert(100D, 5D, 9D, -32D);
		assertEquals(37.778D, result, 0.001);
		verifyZeroInteractions(formulaRepository);
	}

	@Test
	public void convert_celcius_to_fahrenheit() throws ConvertionException {
		double result = converter.convert(24D, 9D, 5D, 32D);
		assertEquals(75.2D, result, 0.001);
		verifyZeroInteractions(formulaRepository);
	}
}
