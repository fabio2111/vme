package org.fao.fi.vme.sync2.mapping;

import java.util.List;

import org.fao.fi.figis.devcon.FIGISDoc;
import org.fao.fi.figis.devcon.VMEIdent;
import org.fao.fi.figis.domain.ObservationDomain;
import org.fao.fi.figis.domain.ObservationXml;
import org.fao.fi.figis.domain.VmeObservationDomain;
import org.fao.fi.vme.domain.Vme;
import org.fao.fi.vme.test.VmeMock;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ObjectMappingTest {

	ObjectMapping om = new ObjectMapping();

	@Test
	public void testMapVme2Figis() {
		int nrOfYears = 3;
		Vme vme = VmeMock.generateVme(nrOfYears);

		VmeObservationDomain vod = om.mapVme2Figis(vme);

		List<ObservationDomain> odList = vod.getObservationDomainList();
		assertEquals(nrOfYears, odList.size());
		for (ObservationDomain od : odList) {
			assertNotNull(od.getReportingYear());
			List<ObservationXml> xmlList = od.getObservationsPerLanguage();
			for (ObservationXml observationXml : xmlList) {
				assertTrue(observationXml.getXml().contains(FIGISDoc.class.getSimpleName()));
				assertTrue(observationXml.getXml().contains(VMEIdent.class.getSimpleName()));
			}
			assertEquals(1, xmlList.size());
		}
	}
}
