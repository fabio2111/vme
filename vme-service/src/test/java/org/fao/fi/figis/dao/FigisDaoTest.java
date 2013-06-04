package org.fao.fi.figis.dao;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.fao.fi.figis.domain.Observation;
import org.fao.fi.figis.domain.ObservationXml;
import org.fao.fi.figis.domain.RefVme;
import org.fao.fi.figis.domain.VmeObservation;
import org.fao.fi.vme.dao.config.FigisDataBaseProducer;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ FigisDataBaseProducer.class })
public class FigisDaoTest {

	@Inject
	private FigisDao dao;

	@Test
	public void testLoadRefVmes() {
		int number = 50;
		for (int i = 0; i < number; i++) {
			RefVme o = new RefVme();
			o.setId(i);
			dao.persist(o);
		}
		assertEquals(number, dao.loadRefVmes().size());

	}

	@Test
	public void testLoadRefVme() {
		int id = 4354;
		RefVme o = new RefVme();
		o.setId(id);
		dao.persist(o);
		RefVme found = dao.findRefVme(id);
		assertNotNull(found);
		assertEquals(id, found.getId().intValue());

	}

	@Test
	public void testPersistVmeObservation() {

		delegateCheckOnNumberOfObjectsInModel(0);

		// create xml for a language
		ObservationXml xml = new ObservationXml();

		// create list of language xmls
		List<ObservationXml> observationsPerLanguage = new ArrayList<ObservationXml>();
		observationsPerLanguage.add(xml);

		// add this list to the observation
		Observation o = new Observation();
		o.setObservationsPerLanguage(observationsPerLanguage);

		// formalise it as a vme observation
		VmeObservation vo = new VmeObservation();
		vo.setObservation(o);
		dao.persist(vo);

		delegateCheckOnNumberOfObjectsInModel(1);
	}

	private void delegateCheckOnNumberOfObjectsInModel(int i) {
		assertEquals(i, dao.loadObjects(ObservationXml.class).size());
		assertEquals(i, dao.loadObjects(Observation.class).size());
		assertEquals(i, dao.loadObjects(VmeObservation.class).size());

	}

	/**
	 * return null when no object is found.
	 */

	@Test
	public void testLoadRefVmeNull() {
		RefVme object = dao.findRefVme(4561);
		assertNull(object);

	}
}
