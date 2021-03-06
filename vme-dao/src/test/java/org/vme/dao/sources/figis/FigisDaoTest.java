package org.vme.dao.sources.figis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.fao.fi.figis.domain.Observation;
import org.fao.fi.figis.domain.ObservationXml;
import org.fao.fi.figis.domain.RefVme;
import org.fao.fi.figis.domain.VmeObservation;
import org.fao.fi.figis.domain.VmeObservationDomain;
import org.fao.fi.figis.domain.rule.DomainRule4ObservationXmlId;
import org.fao.fi.figis.domain.test.ObservationMock;
import org.fao.fi.figis.domain.test.ObservationXmlMock;
import org.fao.fi.figis.domain.test.RefVmeMock;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.figis.FigisDataBaseProducer;
import org.vme.dao.config.figis.FigisPersistenceUnitConfiguration;
import org.vme.dao.test.FigisDaoTestLogic;

@RunWith(CdiRunner.class)
@ActivatedAlternatives(FigisPersistenceUnitConfiguration.class)
@AdditionalClasses(FigisDataBaseProducer.class)
public class FigisDaoTest extends FigisDaoTestLogic {

	/**
	 * Changing the year should result in the deletion of the old year and the
	 * creation of the new year.
	 */
	@Test
	public void testSyncVmeObservationDomainWithDifferentYears() {
		RefVme refVme = RefVmeMock.create();
		if (dao.find(RefVme.class, refVme.getId()) == null) {
			dao.persist(refVme);
		}
		int count[] = count();
		checkCount(count, 0);
		VmeObservationDomain vod = createVmeObservationDomain(1);
		vod.setRefVme(refVme);
		dao.syncVmeObservationDomain(vod);
		checkCount(count, 1);
		checkPrimaries();

		vod = createVmeObservationDomain(1);
		vod.setRefVme(refVme);
		vod.getObservationDomainList().get(0).setReportingYear(Integer.toString(FigisDaoTestLogic.REPORTING_YEAR + 1));
		dao.syncVmeObservationDomain(vod);
		checkCount(count, 1);
		checkPrimaries();

	}

	private void checkPrimaries() {
		List<Observation> l = dao.loadObjects(Observation.class);
		int primaries = 0;
		for (Observation observation : l) {
			if (observation.isPrimary()) {
				primaries++;
			}
		}
		assertEquals(1, primaries);

	}

	@Test
	public void testSyncVmeObservationDomain() {
		RefVme refVme = RefVmeMock.create();
		if (dao.find(RefVme.class, refVme.getId()) == null) {
			dao.persist(refVme);
		}
		int count[] = count();
		checkCount(count, 0);
		VmeObservationDomain vod = createVmeObservationDomain(1);
		vod.setRefVme(refVme);

		dao.syncVmeObservationDomain(vod);
		checkCount(count, 1);

		int obses = 6;

		vod = createVmeObservationDomain(obses);
		vod.setRefVme(refVme);
		dao.syncVmeObservationDomain(vod);

		checkCount(count, obses);
		checkPrimaries();

	}

	@Test
	public void testFindVmeObservationByVme() {
		RefVme refVme = RefVmeMock.create();
		if (dao.find(RefVme.class, refVme.getId()) == null) {
			dao.persist(refVme);
		}
		VmeObservationDomain vod = createVmeObservationDomain(1);
		vod.setRefVme(refVme);
		dao.syncVmeObservationDomain(vod);

		System.out.println(vod.getRefVme().getId());

		List<VmeObservation> l = dao.loadObjects(VmeObservation.class);
		for (VmeObservation vmeObservation : l) {
			System.out.println("getVmeId");
			System.out.println(vmeObservation.getId().getVmeId());
			System.out.println("getObservationId");
			System.out.println(vmeObservation.getId().getObservationId());
			System.out.println("year");
			System.out.println(vmeObservation.getId().getReportingYear());
		}

		VmeObservation vo = dao.findExactVmeObservation(vod.getRefVme().getId(), FigisDaoTestLogic.REPORTING_YEAR);
		assertEquals(vo.getId().getVmeId(), refVme.getId());
		assertEquals(vo.getId().getReportingYear(), Integer.toString(FigisDaoTestLogic.REPORTING_YEAR));

	}

	@Test
	public void testFindVod() {
		RefVme refVme = RefVmeMock.create();
		if (dao.find(RefVme.class, refVme.getId()) == null) {
			dao.persist(refVme);
		}
		int count[] = count();
		VmeObservationDomain vod = createVmeObservationDomain(1);

		assertEquals(1, vod.getObservationDomainList().size());
		vod.setRefVme(refVme);
		checkCount(count, 0);
		dao.syncVmeObservationDomain(vod);
		checkCount(count, 1);
		VmeObservationDomain vodFound = dao.findVod(refVme.getId());
		assertEquals(vod.getObservationDomainList().size(), vodFound.getObservationDomainList().size());
		assertEquals(vod.getObservationDomainList().get(0), vodFound.getObservationDomainList().get(0));
		assertEquals(vod.getObservationDomainList().get(0).getId(), vodFound.getObservationDomainList().get(0).getId());
		assertEquals(vod.getObservationDomainList().get(0).getReportingYear(),
				vodFound.getObservationDomainList().get(0).getReportingYear());
		assertEquals(vod.getObservationDomainList().get(0).getCollection(), vodFound.getObservationDomainList().get(0)
				.getCollection());
		assertEquals(vod.getObservationDomainList().get(0).getOrder(), vodFound.getObservationDomainList().get(0)
				.getOrder());
		assertEquals(vod.getObservationDomainList().get(0).getObservationsPerLanguage().size(), vodFound
				.getObservationDomainList().get(0).getObservationsPerLanguage().size());

	}

	@Test
	public void testsyncRefVme() {
		RefVme r = createRefVme();
		assertNull(dao.find(RefVme.class, r.getId()));
		dao.syncRefVme(r);
		assertNotNull(dao.find(RefVme.class, r.getId()));
		dao.syncRefVme(r);
		assertNotNull(dao.find(RefVme.class, r.getId()));

	}

	@Test
	public void testLoadRefVmes() {
		int number = 50;
		for (int i = 0; i < number; i++) {
			RefVme o = new RefVme();

			o.setId(new Long(i));
			dao.persist(o);
		}

		assertEquals(number, dao.count(RefVme.class).intValue());

	}

	@Test
	public void testLoadRefVme() {
		Long id = new Long(4354);
		RefVme o = new RefVme();
		o.setId(id);
		dao.persist(o);

		RefVme found = (RefVme) dao.find(RefVme.class, o.getId());
		assertNotNull(found);
		assertEquals(id, found.getId());

	}

	@Test
	public void testObservationXml() {
		ObservationXml xml = ObservationXmlMock.create();
		Observation o = ObservationMock.create();
		dao.persist(o);
		xml.setObservation(o);
		DomainRule4ObservationXmlId r = new DomainRule4ObservationXmlId();
		r.composeId(xml);
		dao.persist(xml);
		assertNotNull(dao.find(ObservationXml.class, xml.getId()));
	}

	@Test
	public void testObservation() {
		Observation o = ObservationMock.create();
		dao.persist(o);
		assertNotNull(dao.find(Observation.class, o.getId()));
	}

	/**
	 * return null when no object is found.
	 */
	@Test
	public void testLoadRefVmeNull() {
		RefVme found = (RefVme) dao.find(RefVme.class, 4561l);
		assertNull(found);
	}

	@Test
	public void testRemoveVme() {
		RefVme refVme = RefVmeMock.create();
		if (dao.find(RefVme.class, refVme.getId()) == null) {
			dao.persist(refVme);
		}

		delegateCheckOnNumberOfObjectsInModel(0);
		VmeObservationDomain vod = createVmeObservationDomain(1);
		vod.setRefVme(refVme);
		dao.syncVmeObservationDomain(vod);
		delegateCheckOnNumberOfObjectsInModel(1);
		dao.removeVme(vod.getRefVme().getId());
		delegateCheckOnNumberOfObjectsInModel(0);
	}

}
