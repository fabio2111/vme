package org.fao.fi.vme.dao.config;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmeDataBaseProducer.class })
public class VmeDataBaseProducerTest {

	@Inject
	EntityManagerFactory f;
	@Inject
	@Vme
	EntityManager e;

	@Test
	public void testProduceEntityManager() {
		assertNotNull(f);
		assertNotNull(e);
	}

}