package org.fao.fi.vme.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.fao.fi.vme.dao.config.EntityManagerFactoryProducer;
import org.fao.fi.vme.dao.config.EntityManagerProducer;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ EntityManagerFactoryProducer.class, EntityManagerProducer.class })
public class CdiTest {

	@Inject
	EntityManagerFactory f;
	@Inject
	EntityManager e;

	@Test
	public void test() {
		assertNotNull(f);
		assertNotNull(e);

	}

}