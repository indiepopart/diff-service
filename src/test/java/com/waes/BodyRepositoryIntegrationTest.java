package com.waes;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.waes.model.Body;
import com.waes.model.BodyRepository;
import com.waes.model.DiffSide;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BodyRepositoryIntegrationTest {

	@Autowired
    private TestEntityManager entityManager;
	
	@Autowired
    private BodyRepository bodyRepository;
	
	@Test
	public void whenFindById_thenReturnBody() {
		// given
		Body body = new Body(1L, "asdasd", DiffSide.LEFT);
		entityManager.persist(body);
		entityManager.flush();
		
		// when
		Body found = bodyRepository.getOne(1L);
		
		// then
		assertThat(found.getSide().equals(DiffSide.LEFT));
		assertThat(found.getBody().equals("asdasd"));
	}
}
