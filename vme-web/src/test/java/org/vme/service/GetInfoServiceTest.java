package org.vme.service;

import javax.inject.Inject;

import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.test.VmeMock;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.figis.FigisDataBaseProducer;
import org.vme.dao.config.figis.FigisTestPersistenceUnitConfiguration;
import org.vme.dao.config.vme.VmeDataBaseProducerApplicationScope;
import org.vme.dao.config.vme.VmeTestPersistenceUnitConfiguration;
import org.vme.dao.impl.jpa.ReferenceDaoImpl;
import org.vme.dao.impl.jpa.VmeSearchDaoImpl;
import org.vme.service.dto.VmeSmResponse;

@RunWith(CdiRunner.class)
@AdditionalClasses({ ReferenceDaoImpl.class, VmeSearchDaoImpl.class })
@ActivatedAlternatives({ FigisTestPersistenceUnitConfiguration.class, FigisDataBaseProducer.class,
	VmeTestPersistenceUnitConfiguration.class, VmeDataBaseProducerApplicationScope.class })
public class GetInfoServiceTest {
	
	@Inject
	GetInfoService service;
	
	private Vme vme;
	
	@Before
	public void before(){
		vme = VmeMock.generateVme(3);
	}
	
	@Test
	public void testFindInfoStringInt() {
		
		VmeSmResponse vmeSmResponse1 = service.findInfo(vme.getInventoryIdentifier(), 0);
		System.out.println(vmeSmResponse1.getLocalName());
	
	}

}
