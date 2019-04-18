package com.jo.converter.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jo.converter.domain.ConvertionResult;
import com.jo.converter.domain.Formula;
import com.jo.converter.service.Converter;
import com.jo.converter.service.exception.ConvertionException;

@RestController
public class ConverterController {

	private final Converter converter;

	@Autowired
	public ConverterController(Converter converter) {
		this.converter = converter;
	}

	@CrossOrigin(origins = "*")
	@RequestMapping("/formula")
	public List<Formula> getAllFormula() {
		return converter.getAllFormula();
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping("/convert/{fromType}/{toType}/{value}")
	public ConvertionResult convert(@PathVariable(value = "fromType") String fromType,
			@PathVariable(value = "toType") String toType, @PathVariable(value = "value") Double value) throws ConvertionException {
		return converter.convert(fromType, toType, value);
	}
}