package org.fao.fi.vme.batch.sync2.mapping;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.fao.fi.figis.devcon.FIGISDoc;
import org.fao.fi.figis.devcon.RelatedFisheries;
import org.fao.fi.figis.domain.ObservationDomain;
import org.fao.fi.figis.domain.ObservationXml;
import org.fao.fi.figis.domain.VmeObservationDomain;
import org.fao.fi.figis.domain.rule.Figis;
import org.fao.fi.vme.VmeException;
import org.fao.fi.vme.batch.sync2.mapping.xml.DefaultObservationXml;
import org.fao.fi.vme.batch.sync2.mapping.xml.FigisDocBuilderRegulatory;
import org.fao.fi.vme.batch.sync2.mapping.xml.FigisDocBuilderVme;
import org.fao.fi.vme.domain.model.Vme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vme.dao.sources.figis.PrimaryRule;
import org.vme.dao.sources.figis.PrimaryRuleValidator;
import org.vme.dao.test.DefaultObservationDomain;
import org.vme.fimes.jaxb.JaxbMarshall;

/**
 * Stage A: domain objects without the year dimension: Vme and Rfmo.
 * 
 * Stage B: domain objects with the year dimension:
 * 
 * Vme trough History, SpecificMeasures, Profile
 * 
 * Vme-Rfmo through History, GeneralMesaures-InformationSource.
 * 
 * Stage B has only YearObject objects.
 * 
 * Algorithm steps: (1) Collect all YearObject objects. Generate the Figis
 * objects per year.
 * 
 * 
 * 
 * ME data stored in the database will be exposed in XML format for being
 * disseminated in form of FIGIS fact sheets. Here follows the list of db fields
 * matching XPaths. VME fact sheets are of two types: VME and Regulatory (see
 * Scope attribute in VMEIdent).
 * 
 * When Scope= VME, XML output will contain:
 * 
 * fi:HabitatBio, fi:Impacts, Specific Measure
 * {fi:ManagementMethods/fi:ManagementMethodEntry
 * 
 * @Focus="Vulnerable Marine Ecosystems"/dc:Title[VME-specific measures]}
 * 
 *                    When Scope= Regulatory, XML output will contain:
 * 
 *                    General Measure
 *                    {fi:ManagementMethods/fi:ManagementMethodEntry@Focus=
 *                    "Vulnerable Marine Ecosystems"/dc:Title[VME-specific
 *                    measures]} , fi:FisheryArea, fi:History, fi:Sources
 * 
 * 
 * 
 * @author Erik van Ingen
 * 
 */
public class ObjectMapping {

	@Inject
	private FigisDocBuilderVme figisDocBuilderVme;
	@Inject
	private FigisDocBuilderRegulatory figisDocBuilderRegulatory;

	private final JaxbMarshall marshall = new JaxbMarshall();
	private final PeriodGrouping grouping = new PeriodGrouping();
	private final SliceDuplicateFilter filter = new SliceDuplicateFilter();
	private final PrimaryRule primaryRule = new PrimaryRule();
	private final PrimaryRuleValidator primaryRuleValidator = new PrimaryRuleValidator();
	private static final Logger LOG = LoggerFactory.getLogger(ObjectMapping.class);

	public VmeObservationDomain mapVme2Figis2(Vme vme) {

		List<DisseminationYearSlice> slices = grouping.collect(vme);

		// filter out the duplicates.
		filter.filter(slices);

		List<ObservationDomain> odList = new ArrayList<ObservationDomain>();

		for (DisseminationYearSlice disseminationYearSlice : slices) {
			ObservationDomain od = new DefaultObservationDomain().defineDefaultObservation();
			od.setCollection(Figis.RFMO_COLLECTION.get(vme.getRfmo().getId()));
			List<ObservationXml> observationsPerLanguage = new ArrayList<ObservationXml>();
			od.setObservationsPerLanguage(observationsPerLanguage);
			od.setReportingYear(String.valueOf(disseminationYearSlice.getYear()));
			odList.add(od);

			FIGISDoc figisDoc = new FIGISDoc();

			if (disseminationYearSlice.getGeoRef() == null) {
				LOG.info("disseminationYearSlice.getGeoRef() == null");
			}

			if (vme.getScope() == 10) {
				figisDocBuilderVme.docIt(vme, disseminationYearSlice, figisDoc);
			}
			if (vme.getScope() == 20) {
				figisDocBuilderRegulatory.docIt(vme, disseminationYearSlice, figisDoc);
			}

			ObservationXml xml = new DefaultObservationXml().define();

			// TODO http://figisapps.fao.org/jira/browse/VME-54
			// marshall.validate(figisDoc);

			String xmlString = marshall.marshalToString(figisDoc);

			if (xmlString.contains(RelatedFisheries.class.getSimpleName()) || xmlString.contains("<fi:Text xsi:nil=")
					|| xmlString.contains("<fi:Impacts/>")) {
				throw new VmeException(
						"Vme XML contains RelatedFisheries, <fi:Text xsi:nil=,  or <fi:Impacts/> is not correct.");

			}
			xml.setXml(xmlString);
			observationsPerLanguage.add(xml);
		}
		VmeObservationDomain vod = new VmeObservationDomain();
		vod.setObservationDomainList(odList);
		primaryRule.apply(vod);
		primaryRuleValidator.validate(vod);

		return vod;
	}

}
