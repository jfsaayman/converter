package com.jo.converter.api;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.jo.converter.domain.ConvertionResult;
import com.jo.converter.domain.Formula;
import com.jo.converter.service.Converter;

@RunWith(SpringRunner.class)
@WebMvcTest(ConverterController.class)
public class ConverterControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private Converter converter;

	@Test
	public void getAllFormula() throws Exception {

		List<Formula> formulas = new ArrayList<Formula>();
		Formula formula = new Formula("mile", "km", 8D, 5D, 0D);
		formulas.add(formula);

		given(converter.getAllFormula()).willReturn(formulas);

		mvc.perform(get("/formula").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].fromType", equalTo(formula.getFromType())));
	}

	@Test
	public void convert() throws Exception {

		Formula formula = new Formula("mi", "km", 8D, 5D, 0D);
		ConvertionResult result = new ConvertionResult(formula, 8D);

		given(converter.convert("mi", "km", 5D)).willReturn(result);

		mvc.perform(get("/convert/mi/km/5").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.value", equalTo(8D)))
				.andExpect(jsonPath("$.formula.fromType", equalTo(formula.getFromType())));
	}
}