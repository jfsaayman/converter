package com.jo.converter.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jo.converter.domain.Formula;

public interface FormulaRepository extends JpaRepository<Formula, Long> {

	List<Formula> findByFromType(String fromType);
	
	Formula findByFromTypeAndToType(String fromType, String toType);

}
