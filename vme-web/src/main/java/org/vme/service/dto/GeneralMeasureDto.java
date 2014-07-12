package org.vme.service.dto;


public class GeneralMeasureDto {

	public int year;
	public int validityPeriodStart;
	public int validityPeriodEnd;
	public String fishingArea;
	public String exploratoryFishingProtocol;
	public String vmeEncounterProtocol;
	public String vmeIndicatorSpecies;
	public String threshold;
	public String factsheetURL;
	
	public GeneralMeasureDto(){
		
	}	

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getValidityPeriodStart() {
		return validityPeriodStart;
	}

	public void setValidityPeriodStart(int validityPeriodStart) {
		this.validityPeriodStart = validityPeriodStart;
	}

	public int getValidityPeriodEnd() {
		return validityPeriodEnd;
	}

	public void setValidityPeriodEnd(int validityPeriodEnd) {
		this.validityPeriodEnd = validityPeriodEnd;
	}

	public String getFishingArea() {
		return fishingArea;
	}

	public void setFishingArea(String fishingArea) {
		this.fishingArea = fishingArea;
	}

	public String getExploratoryFishingProtocol() {
		return exploratoryFishingProtocol;
	}

	public void setExploratoryFishingProtocol(String exploratoryFishingProtocol) {
		this.exploratoryFishingProtocol = exploratoryFishingProtocol;
	}

	public String getVmeEncounterProtocol() {
		return vmeEncounterProtocol;
	}

	public void setVmeEncounterProtocol(String vmeEncounterProtocol) {
		this.vmeEncounterProtocol = vmeEncounterProtocol;
	}

	public String getVmeIndicatorSpecies() {
		return vmeIndicatorSpecies;
	}

	public void setVmeIndicatorSpecies(String vmeIndicatorSpecies) {
		this.vmeIndicatorSpecies = vmeIndicatorSpecies;
	}

	public String getThreshold() {
		return threshold;
	}

	public void setThreshold(String threshold) {
		this.threshold = threshold;
	}

	public String getFactsheetURL() {
		return factsheetURL;
	}

	public void setFactsheetURL(String factsheetURL) {
		this.factsheetURL = factsheetURL;
	}
	
}
