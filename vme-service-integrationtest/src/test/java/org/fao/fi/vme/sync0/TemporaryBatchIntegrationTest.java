package org.fao.fi.vme.sync0;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.fao.fi.figis.domain.Observation;
import org.fao.fi.figis.domain.VmeObservation;
import org.fao.fi.vme.domain.model.GeoRef;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.msaccess.component.FilesystemMsAccessConnectionProvider;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.service.dao.config.figis.FigisDataBaseProducer;
import org.vme.service.dao.config.vme.VmeDataBaseProducer;
import org.vme.service.dao.sources.figis.FigisDao;
import org.vme.service.dao.sources.vme.VmeDao;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmeDataBaseProducer.class, FigisDataBaseProducer.class,
		FilesystemMsAccessConnectionProvider.class })
public class TemporaryBatchIntegrationTest {

	@Inject
	TemporaryBatch temporaryBatch;

	@Inject
	VmeDao vmeDao;

	@Inject
	FigisDao figisDao;

	@Test
	public void testRun() {
		temporaryBatch.run();

		System.out.println(vmeDao.count(Vme.class));
		System.out.println(vmeDao.count(GeoRef.class));
		System.out.println(figisDao.count(Observation.class));

		assertEquals(98, vmeDao.count(Vme.class).intValue());
		assertEquals(197, vmeDao.count(GeoRef.class).intValue());

		/*
		 * ID RFB_ID VME_ID VME_Inventory_Identifier VME_Feature_ID
		 * VME_Area_Type Year_ID VME_Validity_Start VME_Validity_End VME_Geoform
		 * VME_GeogArea1 VME_GeogArea2 VME_GeogAreaFAO VME_Coord
		 * VME_Description_Physical VME_Description_Biology
		 * VME_Description_Impact 241 SEAFO Schmidt-Ott Seamount SEAFO_164
		 * VME_SEAFO_164_2011 VME 2010 2011 9999 Southeast Atlantic
		 * 
		 * This record has 9999 as endvalue. This would mean that in 2012 there
		 * are 2 years to consider. In this case there is additional data for
		 * the Fisheries history. Therefore it will generate more than 1
		 * observation.
		 */
		// TODO, why is there a difference of 1 between Oracle and H2?
		assertEquals(208, figisDao.count(VmeObservation.class).intValue(), 1);

	}

}
