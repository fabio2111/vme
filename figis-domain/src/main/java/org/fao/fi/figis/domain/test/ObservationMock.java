package org.fao.fi.figis.domain.test;

import org.fao.fi.figis.domain.Observation;
import org.fao.fi.figis.domain.rule.Figis;

public class ObservationMock {

	private ObservationMock() {

	}

	public static Observation create() {
		Observation o = new Observation();
		o.setOrder(Figis.ORDER);
		o.setCollection(Figis.RFMO_COLLECTION.get("NAFO"));
		return o;
	}

}
