package org.vme.service.tabular.record;

import java.lang.reflect.Method;
import java.util.List;

import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.Rfmo;
import org.vme.service.tabular.Empty;
import org.vme.service.tabular.RecordGenerator;

public class InformationSourceRecord extends AbstractRecord implements RecordGenerator<Rfmo, InformationSource, Empty> {

	@Override
	public void doFirstLevel(Rfmo p, List<Object> nextRecord) {
		/*
		 * Unusued method
		 */
	}

	@Override
	public void doSecondLevel(InformationSource p, List<Object> nextRecord) {
		nextRecord.add(p.getPublicationYear());
		nextRecord.add(p.getMeetingStartDate());
		nextRecord.add(p.getMeetingEndDate());
		nextRecord.add(u.getEnglish(p.getCommittee()));
		nextRecord.add(u.getEnglish(p.getCitation()));
		nextRecord.add(u.getEnglish(p.getReportSummary()));

		if (p.getUrl() == null) {
			nextRecord.add("");
		} else {
			nextRecord.add(p.getUrl().toString());
		}

		nextRecord.add(p.getSourceType().getName());

		if (p.getId() == null) {
			nextRecord.add("");
		} else {
			nextRecord.add(p.getId());
		}
	}

	@Override
	public Method getSecondLevelMethod() {
		return getMethod(Rfmo.class, "getInformationSourceList");
	}

	@Override
	public String[] getHeaders() {
		return new String[] { "Year", "Meeting Start Date", "Meeting End Date", "Committee", "Citation",
				"Report Summary", "URL", "Type of publication", "Source ID" };
	}

	@Override
	public void doThirdLevel(Empty p, List<Object> nextRecord) {
		/*
		 * Unusued method
		 */
	}

	@Override
	public Method getThirdLevelMethod() {
		return null;
	}

}
