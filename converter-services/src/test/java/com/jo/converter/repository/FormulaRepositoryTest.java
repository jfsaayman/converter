package com.jo.converter.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.jo.converter.domain.Formula;
import com.jo.converter.domain.repository.FormulaRepository;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest
public class FormulaRepositoryTest {
	@Autowired
    TestEntityManager entityManager;

    @Autowired
    FormulaRepository formulaRepository;

    @Test
    public void saveformula() {
        Formula formula = new Formula("mile", "km", 5D, 8D, 0D);

        formula = entityManager.persistAndFlush(formula);

        Formula storedformula = formulaRepository.findById(formula.getId()).get();
		assertThat(storedformula, equalTo(formula));
    }
    
    @Test
    public void findByFromType() {
		List<Formula> storedformulas = formulaRepository.findByFromType("kg");
		assertNotNull(storedformulas);
		assertEquals(1, storedformulas.size());
		Formula storedformula = storedformulas.get(0);
		assertThat(storedformula.getFromType(), equalTo("kg"));
		assertThat(storedformula.getToType(), equalTo("lb"));
		assertThat(storedformula.getNumerator(), equalTo(2.2046));
		assertThat(storedformula.getDenominator(), equalTo(1D));
		assertThat(storedformula.getToOffset(), equalTo(0D));
    }
    
    @Test
    public void findByFromType_no_results() {
		List<Formula> storedformulas = formulaRepository.findByFromType("kms");
		assertNotNull(storedformulas);
		assertEquals(0, storedformulas.size());
    }
    
    @Test
    public void findByFromTypeAndToType_no_results() {
    	Formula storedformula = formulaRepository.findByFromTypeAndToType("kms", "mile");
		assertNull(storedformula);
    }

    @Test
    public void findByFromTypeAndToType() {
    	Formula storedformula = formulaRepository.findByFromTypeAndToType("km", "mi");
		assertNotNull(storedformula);
		assertThat(storedformula.getNumerator(), equalTo(5D));
		assertThat(storedformula.getDenominator(), equalTo(8D));
		assertThat(storedformula.getToOffset(), equalTo(0D));
    }
    
}
