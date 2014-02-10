package org.vme.web.service;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import javax.inject.Inject;

import org.fao.fi.vme.domain.model.Authority;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.test.VmeMock;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.service.dao.JpaDaoFactory;
import org.vme.service.dao.VmeSearchDao;
import org.vme.service.dao.config.figis.FigisDataBaseProducer;
import org.vme.service.dao.config.vme.VmeDataBaseProducer;
import org.vme.service.dao.sources.vme.VmeDao;
import org.vme.web.service.io.ObservationsRequest;
import org.vme.web.service.io.ServiceResponse;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ FigisDataBaseProducer.class, VmeDataBaseProducer.class, JpaDaoFactory.class })
public class ServiceInvokerTest {

	@Inject
	VmeSearchDao service;

	@Inject
	private VmeDao vmeDao;
	MultiLingualStringUtil u = new MultiLingualStringUtil();

	@Test
	public void testInvokeObservationDAOObservationsRequest() throws Exception {

		String text = "fiets";
		Vme vme = VmeMock.generateVme(1);

		Authority a = new Authority();
		a.setAcronym(vme.getRfmo().getId());
		vmeDao.persist(a);

		vme.getSpecificMeasureList().get(0).setVmeSpecificMeasure((u.english(text)));
		vmeDao.saveVme(vme);

		ObservationsRequest request = new ObservationsRequest(UUID.randomUUID());
		request.setText(text);
		ServiceResponse<?> response = ServiceInvoker.invoke(service, request);
		assertEquals(1, response.getTotalResult());

	}

	@Test
	public void testInvokeReferenceDAOReferencesRequest() {
		// fail("Not yet implemented");
	}

}
