package org.vme.dao.impl.jpa;

import java.util.Calendar;

/**
 * 
 * @author Erik van Ingen
 *
 */

public class VmeRelevantYears {

	public static final int START_YEAR = 2006;

	public long[] calculate() {
		int endYear = Calendar.getInstance().get(Calendar.YEAR);
		int currentMonth = Calendar.getInstance().get(Calendar.MONTH);

		if (currentMonth < Calendar.APRIL) {
			endYear--;
		}

		int nrOfYears = endYear - START_YEAR + 1;

		long years[] = new long[nrOfYears];
		int i = 0;
		for (long year = endYear; year >= START_YEAR; year--) {
			years[i++] = year;
		}
		return years;
	}
}
