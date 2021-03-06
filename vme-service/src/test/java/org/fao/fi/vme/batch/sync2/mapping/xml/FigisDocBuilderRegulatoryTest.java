package org.fao.fi.vme.batch.sync2.mapping.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.xml.bind.JAXBElement;

import org.fao.fi.figis.devcon.BiblioEntry;
import org.fao.fi.figis.devcon.FIGISDoc;
import org.fao.fi.figis.devcon.FisheryArea;
import org.fao.fi.figis.devcon.GeoForm;
import org.fao.fi.figis.devcon.HabitatBio;
import org.fao.fi.figis.devcon.History;
import org.fao.fi.figis.devcon.Management;
import org.fao.fi.figis.devcon.ManagementMethodEntry;
import org.fao.fi.figis.devcon.ManagementMethods;
import org.fao.fi.figis.devcon.Max;
import org.fao.fi.figis.devcon.Measure;
import org.fao.fi.figis.devcon.MeasureType;
import org.fao.fi.figis.devcon.Min;
import org.fao.fi.figis.devcon.ObjectFactory;
import org.fao.fi.figis.devcon.Range;
import org.fao.fi.figis.devcon.Sources;
import org.fao.fi.figis.devcon.Text;
import org.fao.fi.figis.devcon.VME;
import org.fao.fi.figis.devcon.VMEIdent;
import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.support.VmeSimpleDateFormat;
import org.fao.fi.vme.domain.test.VmeMock;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.purl.agmes._1.CreatorCorporate;
import org.purl.dc.elements._1.Identifier;
import org.purl.dc.terms.Abstrakt;
import org.purl.dc.terms.BibliographicCitation;
import org.purl.dc.terms.Created;
import org.vme.dao.config.figis.FigisDataBaseProducer;
import org.vme.dao.config.figis.FigisPersistenceUnitConfiguration;
import org.vme.dao.config.vme.VmeDataBaseProducerApplicationScope;
import org.vme.dao.config.vme.VmePersistenceUnitConfiguration;
import org.vme.dao.impl.jpa.ReferenceDaoImpl;
import org.vme.fimes.jaxb.JaxbMarshall;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ ReferenceDaoImpl.class, VmePersistenceUnitConfiguration.class,
		FigisPersistenceUnitConfiguration.class })
@AdditionalClasses({ VmeDataBaseProducerApplicationScope.class, FigisDataBaseProducer.class })
public class FigisDocBuilderRegulatoryTest {

	@Inject
	FigisDocBuilderRegulatory b;
	JaxbMarshall m;
	MultiLingualStringUtil u;
	int nrOfYears = 2;
	Vme vme;
	CdataUtil cu = new CdataUtil();

	ObjectFactory f = new ObjectFactory();
	private VmeSimpleDateFormat du = new VmeSimpleDateFormat();

	@Before
	public void prepareBefore() {
		m = new JaxbMarshall();
		u = new MultiLingualStringUtil();
		vme = VmeMock.generateVme(nrOfYears);
		vme.setId(67l);
	}

	@Test
	public void testHabitatBio() {
		FIGISDoc figisDoc = new FIGISDoc();
		figisDoc.setVME(new VME());

		GeoForm geoform = f.createGeoForm();
		CdataUtil ut = new CdataUtil();
		Text descriptionPhisical = ut.getCdataText(null);
		JAXBElement<Text> geoformJAXBElement = f.createGeoFormText(descriptionPhisical);
		// geoform.getContent().add(geoformJAXBElement);

		new AddWhenContentRule<Serializable>().check(descriptionPhisical).beforeAdding(geoformJAXBElement)
				.to(geoform.getContent());

		HabitatBio habitatBio = f.createHabitatBio();
		habitatBio.getClimaticZonesAndDepthZonesAndDepthBehavs().add(geoform); // geoForm
																				// is
																				// part
																				// of
																				// HabitatBio

		habitatBio.getClimaticZonesAndDepthZonesAndDepthBehavs();
		figisDoc.getVME().getOverviewsAndHabitatBiosAndImpacts().add(habitatBio);
		String xmlString = m.marshalToString(figisDoc);
		System.out.println(xmlString);
		assertFalse(xmlString.contains("<fi:Text xsi:nil="));

	}

	/**
	 * FishingArea_history fi:FIGISDoc/fi:VME/fi:FisheryArea/fi:Text
	 * 
	 * VME_history fi:FIGISDoc/fi:VME/fi:History/fi:Text
	 * 
	 */
	@Test
	public void testHistory() {
		FIGISDoc figisDoc = new FIGISDoc();
		figisDoc.setVME(new VME());
		b.fisheryArea(vme.getRfmo().getHasFisheryAreasHistory().get(0), figisDoc);
		b.vmesHistory(vme.getRfmo().getHasFisheryAreasHistory().get(0), figisDoc);
		assertNotNull(figisDoc.getVME().getOverviewsAndHabitatBiosAndImpacts().get(0));
		assertTrue(figisDoc.getVME().getOverviewsAndHabitatBiosAndImpacts().get(0) instanceof FisheryArea);
		assertTrue(figisDoc.getVME().getOverviewsAndHabitatBiosAndImpacts().get(1) instanceof History);

	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGeneralMeasures() {
		FIGISDoc figisDoc = new FIGISDoc();

		VME vmeJAXB = new VME();
		figisDoc.setVME(vmeJAXB);

		GeneralMeasure generalMeasure = vme.getRfmo().getGeneralMeasureList().get(0);
		b.generalMeasures(generalMeasure, figisDoc, VmeMock.YEAR);

		Management management = (Management) figisDoc.getVME().getOverviewsAndHabitatBiosAndImpacts().get(0);
		assertNotNull(management);

		ManagementMethods manMethods = (ManagementMethods) management.getTextsAndImagesAndTables().get(0);
		assertNotNull(manMethods);

		ManagementMethodEntry entry = (ManagementMethodEntry) manMethods.getManagementMethodEntriesAndTextsAndImages()
				.get(0);
		assertNotNull(entry);
		assertEquals("Vulnerable Marine Ecosystems", entry.getFocus());
		assertEquals("VME general measures", entry.getTitle().getContent());

		for (Object obj : entry.getTextsAndImagesAndTables()) {
			if (obj instanceof Measure) {
				MeasureType mType = (MeasureType) ((Measure) obj).getTextsAndImagesAndTables().get(0);
				if (mType.getValue().equals("Fishing_Areas")) {
					assertEquals(u.getEnglish(generalMeasure.getFishingArea()), ((Measure) obj)
							.getTextsAndImagesAndTables().get(1));
				}

			} else if (obj instanceof Range) {
				assertEquals("Time", ((Range) obj).getType());
				assertEquals(du.createUiString(generalMeasure.getValidityPeriod().getBeginDate()),
						((JAXBElement<Min>) ((Range) obj).getContent().get(0)).getValue().getContent());
				assertEquals(du.createUiString(generalMeasure.getValidityPeriod().getEndDate()),
						((JAXBElement<Max>) ((Range) obj).getContent().get(1)).getValue().getContent());

			} else if (obj instanceof Sources) {
				List<InformationSource> infoSourceList = generalMeasure.getInformationSourceList();
				for (int i = 0; i < infoSourceList.size(); i++) {
					BiblioEntry biblioEntry = (BiblioEntry) ((Sources) obj).getTextsAndImagesAndTables().get(i);
					for (Object bObj : biblioEntry.getContent()) {
						if (bObj instanceof BibliographicCitation) {

							assertEquals(u.getEnglish(infoSourceList.get(i).getCitation()),
									((BibliographicCitation) bObj).getContent());
						} else if (bObj instanceof Identifier) {
							assertEquals("URI", ((Identifier) bObj).getType());
							assertEquals(infoSourceList.get(i).getUrl().toString(), ((Identifier) bObj).getContent());
						}
					}
				}
			}

		}

	}

	@Test
	public void testYear() {
		String reportingYear = Integer.toString(2013);
		FIGISDoc figisDoc = new FIGISDoc();

		figisDoc.setVME(new VME());
		figisDoc.getVME().setVMEIdent(new VMEIdent());

		for (Object obj : figisDoc.getVME().getVMEIdent().getFigisIDsAndForeignIDsAndWaterAreaReves()) {
			if (obj instanceof String) {
				assertEquals(reportingYear, obj);
			}
		}

	}

	@Test
	@Ignore("because InformationSource are generic resources, should be mocked different")
	public void testInformationSource() {
		FIGISDoc figisDoc = new FIGISDoc();
		figisDoc.setVME(new VME());

		List<InformationSource> infoSourceList = vme.getRfmo().getInformationSourceList();
		b.informationSource(infoSourceList, VmeMock.YEAR, figisDoc);

		Sources sources = (Sources) figisDoc.getVME().getOverviewsAndHabitatBiosAndImpacts().get(0);
		assertNotNull(sources);

		assertTrue(infoSourceList.size() > 0);

		for (int i = 0; i < sources.getTextsAndImagesAndTables().size(); i++) {

			BiblioEntry biblioEntry = (BiblioEntry) sources.getTextsAndImagesAndTables().get(i);
			assertNotNull(biblioEntry);

			for (Object obj : biblioEntry.getContent()) {
				if (obj instanceof CreatorCorporate) {
					assertEquals(u.getEnglish(infoSourceList.get(i).getCommittee()),
							((CreatorCorporate) obj).getContent());
				} else if (obj instanceof Created) {
					assertEquals(infoSourceList.get(i).getPublicationYear(), new Integer(((Created) obj).getContent()),
							1);
				} else if (obj instanceof Abstrakt) {
					assertEquals(u.getEnglish(infoSourceList.get(i).getReportSummary()), ((Abstrakt) obj).getContent());
				} else if (obj instanceof BibliographicCitation) {
					assertEquals(u.getEnglish(infoSourceList.get(i).getCitation()),
							((BibliographicCitation) obj).getContent());
				} else if (obj instanceof Identifier) {
					assertEquals("URI", ((Identifier) obj).getType());
					assertEquals(infoSourceList.get(i).getUrl().toString(), ((Identifier) obj).getContent());
				}
			}
		}
	}

}
