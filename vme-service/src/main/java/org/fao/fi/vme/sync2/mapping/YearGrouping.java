package org.fao.fi.vme.sync2.mapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fao.fi.vme.VmeException;
import org.fao.fi.vme.domain.GeoRef;
import org.fao.fi.vme.domain.Vme;
import org.fao.fi.vme.domain.interfacee.Year;

public class YearGrouping {

	/**
	 * 
	 * Group all the yearObjects of the Vme per year.
	 * 
	 * 
	 * @param vme
	 * @return
	 */
	public Map<Integer, List<Year<?>>> collect(Vme vme) {
		Map<Integer, List<Year<?>>> map = new HashMap<Integer, List<Year<?>>>();

		add2Map(map, vme.getHistoryList());
		add2Map(map, vme.getSpecificMeasuresList());
		add2Map(map, vme.getProfileList());
		add2Map(map, vme.getGeoRefList());

		add2Map(map, vme.getRfmo().getHasFisheryAreasHistory());
		add2Map(map, vme.getRfmo().getGeneralMeasuresList());

		// add2Map(map, vme.getRfmo().getInformationSourceList());
		// the InformationSource is not processed here because that works with date instead of year.

		return map;
	}

	private void add2Map(Map<Integer, List<Year<?>>> map, List<? extends Year<?>> yearObjectList) {
		if (yearObjectList != null) {
			for (Year<?> yearObject : yearObjectList) {
				add2Map(map, yearObject);
			}
		}
	}

	private void add2Map(Map<Integer, List<Year<?>>> map, Year<?> yearObject) {

		if (yearObject == null || yearObject.getYear() == null) {
			String className = "";
			if (yearObject != null) {
				className = yearObject.getClass().getSimpleName();
			}
			if (yearObject instanceof GeoRef) {
				GeoRef geoRef = (GeoRef) yearObject;
				className = className + " geoRef id = " + geoRef.getId() + ", year = " + geoRef.getYear();
			}
			throw new VmeException("The year in the yearObject is null. Class is " + className);
		}

		if (yearObject != null) {
			if (map.containsKey(yearObject.getYear())) {
				map.get(yearObject.getYear()).add(yearObject);
			} else {
				List<Year<?>> list = new ArrayList<Year<?>>();
				list.add(yearObject);
				if (yearObject.getYear() == null) {
					throw new VmeException("yearObject.year is null, should not be at this point.");
				}
				map.put(yearObject.getYear(), list);
			}
		}

	}
}
