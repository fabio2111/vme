package org.fao.fi.vme.sync2.mapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fao.fi.vme.domain.Vme;
import org.fao.fi.vme.domain.YearObject;

public class YearGrouping {

	/**
	 * 
	 * Group all the yearObjects of the Vme per year.
	 * 
	 * 
	 * @param vme
	 * @return
	 */
	public Map<Integer, List<YearObject<?>>> collect(Vme vme) {
		Map<Integer, List<YearObject<?>>> map = new HashMap<Integer, List<YearObject<?>>>();

		add2Map(map, vme.getHistoryList());
		add2Map(map, vme.getSpecificMeasureList());
		add2Map(map, vme.getProfileList());
		add2Map(map, vme.getGeoRefList());

		add2Map(map, vme.getRfmo().getFishingHistoryList());
		add2Map(map, vme.getRfmo().getGeneralMeasuresList());

		// add2Map(map, vme.getRfmo().getInformationSourceList());
		// the InformationSource is not processed here because that works with date instead of year.

		return map;
	}

	private void add2Map(Map<Integer, List<YearObject<?>>> map, List<? extends YearObject<?>> yearObjectList) {
		if (yearObjectList != null) {
			for (YearObject<?> yearObject : yearObjectList) {
				add2Map(map, yearObject);
			}
		}
	}

	private void add2Map(Map<Integer, List<YearObject<?>>> map, YearObject<?> yearObject) {
		if (yearObject != null) {
			if (map.containsKey(yearObject.getYear())) {
				map.get(yearObject.getYear()).add(yearObject);
			} else {
				List<YearObject<?>> list = new ArrayList<YearObject<?>>();
				list.add(yearObject);
				map.put(yearObject.getYear(), list);
			}
		}

	}
}