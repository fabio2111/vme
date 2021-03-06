package org.fao.fi.vme.batch.sync2.mapping;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.inject.Inject;

import org.fao.fi.figis.devcon.FIGISDoc;
import org.fao.fi.figis.devcon.Management;
import org.fao.fi.figis.devcon.ManagementMethods;
import org.fao.fi.figis.devcon.RelatedFisheries;
import org.fao.fi.figis.devcon.VMEIdent;
import org.fao.fi.figis.domain.ObservationDomain;
import org.fao.fi.figis.domain.ObservationXml;
import org.fao.fi.figis.domain.VmeObservationDomain;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.test.VmeMock;
import org.fao.fi.vme.domain.test.VmeScopeMock;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.figis.FigisDataBaseProducer;
import org.vme.dao.config.figis.FigisPersistenceUnitConfiguration;
import org.vme.dao.config.vme.VmeDataBaseProducerApplicationScope;
import org.vme.dao.config.vme.VmePersistenceUnitConfiguration;
import org.vme.dao.sources.vme.VmeDao;
import org.vme.dao.test.ReferenceDaoMockImpl;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ ReferenceDaoMockImpl.class, VmePersistenceUnitConfiguration.class,
		FigisPersistenceUnitConfiguration.class })
@AdditionalClasses({ VmeDataBaseProducerApplicationScope.class, FigisDataBaseProducer.class })
public class ObjectMappingTest {

	@Inject
	ObjectMapping om;

	@Inject
	VmeDao vmeDao;

	@Test
	public void testMapVme2Figis() {

		int nrOfYears = 3;

		vmeDao.persist(VmeScopeMock.create());

		Vme vme = VmeMock.generateVme(nrOfYears);
		vme.setId(456l);

		VmeObservationDomain vod = om.mapVme2Figis2(vme);

		List<ObservationDomain> odList = vod.getObservationDomainList();
		assertEquals(nrOfYears, odList.size());
		for (ObservationDomain od : odList) {
			assertNotNull(od.getReportingYear());
			List<ObservationXml> xmlList = od.getObservationsPerLanguage();
			for (ObservationXml observationXml : xmlList) {
				assertTrue(observationXml.getXml().contains(FIGISDoc.class.getSimpleName()));
				assertTrue(observationXml.getXml().contains(VMEIdent.class.getSimpleName()));
				assertTrue(observationXml.getXml().contains(VMEIdent.class.getSimpleName()));
				assertTrue(observationXml.getXml().contains(ManagementMethods.class.getSimpleName()));
				assertTrue(observationXml.getXml().contains(Management.class.getSimpleName()));
				assertFalse(observationXml.getXml().contains(RelatedFisheries.class.getSimpleName()));
			}
			assertEquals(1, xmlList.size());
		}
	}

}
