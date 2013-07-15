package org.fao.fi.vme.sync2.mapping.xml;

import javax.xml.bind.JAXBElement;

import org.fao.fi.figis.devcon.FIGISDoc;
import org.fao.fi.figis.devcon.FigisID;
import org.fao.fi.figis.devcon.ForeignID;
import org.fao.fi.figis.devcon.GeoForm;
import org.fao.fi.figis.devcon.HabitatBio;
import org.fao.fi.figis.devcon.Impacts;
import org.fao.fi.figis.devcon.Max;
import org.fao.fi.figis.devcon.Min;
import org.fao.fi.figis.devcon.ObjectFactory;
import org.fao.fi.figis.devcon.Range;
import org.fao.fi.figis.devcon.Text;
import org.fao.fi.figis.devcon.VME;
import org.fao.fi.figis.devcon.VMECriteria;
import org.fao.fi.figis.devcon.VMEIdent;
import org.fao.fi.figis.devcon.WaterAreaRef;
import org.fao.fi.figis.domain.rule.Figis;
import org.fao.fi.vme.domain.GeneralMeasures;
import org.fao.fi.vme.domain.Profile;
import org.fao.fi.vme.domain.SpecificMeasures;
import org.fao.fi.vme.domain.Vme;
import org.fao.fi.vme.domain.util.Lang;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.fao.fi.vme.sync2.mapping.RfmoHistory;
import org.fao.fi.vme.sync2.mapping.VmeHistory;
import org.purl.dc.elements._1.Title;

public class FigisDocBuilder {

	ObjectFactory f = new ObjectFactory();
	MultiLingualStringUtil u = new MultiLingualStringUtil();

	public void specificMeasures(SpecificMeasures yearObject, FIGISDoc figisDoc) {
		// TODO Auto-generated method stub

	}

	public void vmeHistory(VmeHistory yearObject, FIGISDoc figisDoc) {
		// TODO Auto-generated method stub

	}

	public void rfmoHistory(RfmoHistory yearObject, FIGISDoc figisDoc) {
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 * descriptionBiological fi:FIGISDoc/fi:VME/fi:HabitatBio/fi:Text
	 * 
	 * descriptionPhysical fi:FIGISDoc/fi:VME/fi:HabitatBio/fi:GeoForm/fi:Text
	 * 
	 * descriptionImpact fi:FIGISDoc/fi:VME/fi:Impacts/fi:Text
	 * 
	 * TODO finish this one.
	 * 
	 * 
	 * @param figisDoc
	 */
	public void profile(Profile profile, FIGISDoc figisDoc) {
		HabitatBio habitatBio = f.createHabitatBio();

		Text text1 = f.createText();
		text1.getContent().add(u.getEnglish(profile.getDescriptionBiological()));
		habitatBio.getClimaticZonesAndDepthZonesAndDepthBehavs().add(text1);
		figisDoc.getVME().getOverviewsAndHabitatBiosAndImpacts().add(habitatBio);

		GeoForm geoForm = f.createGeoForm();
		Text text2 = f.createText();
		text2.getContent().add(u.getEnglish(profile.getDescriptionPhisical()));

		habitatBio.getClimaticZonesAndDepthZonesAndDepthBehavs().add(geoForm);

		Impacts impacts = f.createImpacts();
		Text text3 = f.createText();
		text3.getContent().add(u.getEnglish(profile.getDescriptionBiological()));
		impacts.getTextsAndImagesAndTables().add(text3);
		figisDoc.getVME().getOverviewsAndHabitatBiosAndImpacts().add(impacts);

	}

	public void generalMeasures(GeneralMeasures yearObject, FIGISDoc figisDoc) {
		// TODO Auto-generated method stub

	}

	/**
	 * VME Identifier fi:FIGISDoc/fi:VME/fi:VMEIdent/fi:FigisID
	 * 
	 * name fi:FIGISDoc/fi:VME/fi:VMEIdent/dc:Title
	 * 
	 * geographicLayerId fi:FIGISDoc/fi:VME/fi:VMEIdent/fi:WaterAreaRef/fi:ForeignID@CodeSystem="vme"/@Code
	 * 
	 * TODO areaType fi:FIGISDoc/fi:VME/fi:VMEIdent/VMEType/@Value
	 * 
	 * criteria fi:FIGISDoc/fi:VME/fi:VMEIdent/VMECriteria/@Value
	 * 
	 * ValidityPeriod/beginYear fi:FIGISDoc/fi:VME/fi:VMEIdent/fi:Range@Type="Time"/fi:Min
	 * 
	 * ValidityPeriod/endYear fi:FIGISDoc/fi:VME/fi:VMEIdent/fi:Range@Type="Time"/fi:Max
	 * 
	 * @param vme
	 *            *
	 * 
	 * @param figisDoc
	 */
	public void vme(Vme vmeDomain, FIGISDoc figisDoc) {
		VMEIdent vmeIdent = new VMEIdent();

		FigisID figisID = new FigisID();
		figisID.setContent(vmeDomain.getId().toString());

		Title title = new Title();
		title.setContent(vmeDomain.getName().getStringMap().get(Lang.EN));

		WaterAreaRef waterAreaRef = new WaterAreaRef();

		ForeignID foreignID = new ForeignID();
		foreignID.setCodeSystem(Figis.CODE_SYSTEM);
		foreignID.setCode(vmeDomain.getGeoRefList().get(0).getGeographicFeatureID());
		waterAreaRef.getFigisIDsAndForeignIDs().add(foreignID);

		Min min = f.createMin();
		min.setContent(vmeDomain.getValidityPeriod().getBeginYear().toString());
		JAXBElement<Min> minJAXBElement = f.createRangeMin(f.createMin());

		Max max = f.createMax();
		max.setContent(vmeDomain.getValidityPeriod().getEndYear().toString());
		JAXBElement<Max> maxJAXBElement = f.createRangeMax(f.createMax());

		Range range = f.createRange();

		range.getContent().add(minJAXBElement);
		range.getContent().add(maxJAXBElement);

		VMECriteria vmeCriteria = new VMECriteria();
		vmeCriteria.setValue(vmeDomain.getCriteria());

		vmeIdent.getFigisIDsAndWaterAreaRevesAndOrgReves().add(figisID);
		vmeIdent.getFigisIDsAndWaterAreaRevesAndOrgReves().add(title);
		vmeIdent.getFigisIDsAndWaterAreaRevesAndOrgReves().add(waterAreaRef);
		vmeIdent.getFigisIDsAndWaterAreaRevesAndOrgReves().add(vmeCriteria);
		// vmeIdent.getFigisIDsAndWaterAreaRevesAndOrgReves().add(range);

		VME vme = new VME();
		vme.setVMEIdent(vmeIdent);
		figisDoc.setVME(vme);

	}

	public void year(FIGISDoc figisDoc, Object year) {
		figisDoc.getVME().getVMEIdent().getFigisIDsAndWaterAreaRevesAndOrgReves().add(year);
	}

}
