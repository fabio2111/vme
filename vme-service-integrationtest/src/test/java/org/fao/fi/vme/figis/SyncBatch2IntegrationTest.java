package org.fao.fi.vme.figis;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.inject.Inject;

import org.fao.fi.figis.domain.RefVme;
import org.fao.fi.figis.domain.test.RefVmeMock;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.test.VmeMock;
import org.fao.fi.vme.sync2.SyncBatch2;
import org.fao.fi.vme.test.FigisDaoTestLogic;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.service.dao.config.figis.FigisDataBaseProducer;
import org.vme.service.dao.config.vme.VmeDataBaseProducer;
import org.vme.service.dao.sources.figis.FigisDao;
import org.vme.service.dao.sources.vme.VmeDao;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmeDataBaseProducer.class, FigisDataBaseProducer.class })
public class SyncBatch2IntegrationTest extends FigisDaoTestLogic {

	private static int INSERTED = 1;

	@Inject
	SyncBatch2 syncBatch2;

	@Inject
	VmeDao vmeDao;

	@Inject
	FigisDao figisDao;

	@Before
	public void testBefore() {
		clean();
	}

	@After
	public void testAfter() {
		clean();
	}

	// @Before
	// public void testBefore() {
	//
	// // Bizarre problem, see also FigisDaoIntegrationTest.testDeleteRefVme
	// RefVme r = (RefVme) figisDao.find(RefVme.class, VmeMock.VME_ID);
	// if (r != null) {
	// figisDao.getEm().refresh(r);
	// figisDao.remove(r);
	// }
	//
	// VmeDaoTestLogic l = new VmeDaoTestLogic();
	// l.mockAndSaveVme(vmeDao, 1);
	// RefVme refVme = RefVmeMock.create();
	// refVme.setId(VmeMock.VME_ID);
	// }

	@Test
	public void testSyncFigisWithVme() {

		List<RefVme> refVmeList = figisDao.selectFrom(figisDao.getEm(), RefVme.class);
		for (RefVme refVme : refVmeList) {
			figisDao.remove(refVme);
		}

		Vme vme = VmeMock.generateVme(1);
		vmeDao.saveVme(vme);

		RefVme refVme = RefVmeMock.create();
		refVme.setId(vme.getId());

		int c[] = count();
		int totalR = dao.count(RefVme.class).intValue();
		syncBatch2.syncFigisWithVme();
		totalR++;

		// apparently the algorithm generates 1 observation, not validated
		// further here.
		int observations = 1;

		checkCount(c, observations);

		// More important, a subsequent synch should return the same numbers
		syncBatch2.syncFigisWithVme();
		assertEquals(totalR, dao.count(RefVme.class).intValue());
		checkCount(c, observations);

	}
}
