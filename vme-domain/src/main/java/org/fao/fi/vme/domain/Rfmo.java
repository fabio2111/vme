package org.fao.fi.vme.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 * 
 * @author Erik van Ingen
 * 
 */
@Entity
public class Rfmo {

	/**
	 * The id comes from RMTS
	 */
	@Id
	private String id;

	/**
	 * Rfmo
	 * 
	 */
	@OneToMany(mappedBy = "rfmo")
	private List<GeneralMeasure> generalMeasureList;

	/**
	 * 
	 * This RFMO manages this list of Vmes
	 * 
	 */
	@OneToMany(mappedBy = "rfmo")
	private List<Vme> listOfManagedVmes = new ArrayList<Vme>();

	/**
	 * An RFMO has a number of information sources
	 */
	@ManyToMany
	private List<InformationSource> informationSourceList = new ArrayList<InformationSource>();

	@OneToMany(cascade = { CascadeType.ALL })
	private List<History> hasFisheryAreasHistory;

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(nullable = true)
	private List<History> hasVmesHistory;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<GeneralMeasure> getGeneralMeasureList() {
		return generalMeasureList;
	}

	public void setGeneralMeasureList(List<GeneralMeasure> generalMeasureList) {
		this.generalMeasureList = generalMeasureList;
	}

	public List<Vme> getListOfManagedVmes() {
		return listOfManagedVmes;
	}

	public void setListOfManagedVmes(List<Vme> listOfManagedVmes) {
		this.listOfManagedVmes = listOfManagedVmes;
	}

	public List<InformationSource> getInformationSourceList() {
		return informationSourceList;
	}

	public void setInformationSourceList(List<InformationSource> informationSourceList) {
		this.informationSourceList = informationSourceList;
	}

	public List<History> getHasFisheryAreasHistory() {
		return hasFisheryAreasHistory;
	}

	public void setHasFisheryAreasHistory(List<History> hasFisheryAreasHistory) {
		this.hasFisheryAreasHistory = hasFisheryAreasHistory;
	};

	public List<History> getHasVmesHistory() {
		return hasVmesHistory;
	}

	public void setHasVmesHistory(List<History> hasVmesHistory) {
		this.hasVmesHistory = hasVmesHistory;
	}

	// @Override
	// public int hashCode() {
	// final int prime = 31;
	// int result = 1;
	// result = prime * result + ((generalMeasureList == null) ? 0 : generalMeasureList.hashCode());
	// result = prime * result + ((hasFisheryAreasHistory == null) ? 0 : hasFisheryAreasHistory.hashCode());
	// result = prime * result + ((hasVmesHistory == null) ? 0 : hasVmesHistory.hashCode());
	// result = prime * result + ((id == null) ? 0 : id.hashCode());
	// result = prime * result + ((informationSourceList == null) ? 0 : informationSourceList.hashCode());
	// result = prime * result + ((listOfManagedVmes == null) ? 0 : listOfManagedVmes.hashCode());
	// return result;
	// }

	// @Override
	// public boolean equals(Object obj) {
	// if (this == obj) {
	// return true;
	// }
	// if (obj == null) {
	// return false;
	// }
	// if (!(obj instanceof Rfmo)) {
	// return false;
	// }
	// Rfmo other = (Rfmo) obj;
	// if (generalMeasureList == null) {
	// if (other.generalMeasureList != null) {
	// return false;
	// }
	// } else if (!generalMeasureList.equals(other.generalMeasureList)) {
	// return false;
	// }
	// if (hasFisheryAreasHistory == null) {
	// if (other.hasFisheryAreasHistory != null) {
	// return false;
	// }
	// } else if (!hasFisheryAreasHistory.equals(other.hasFisheryAreasHistory)) {
	// return false;
	// }
	// if (hasVmesHistory == null) {
	// if (other.hasVmesHistory != null) {
	// return false;
	// }
	// } else if (!hasVmesHistory.equals(other.hasVmesHistory)) {
	// return false;
	// }
	// if (id == null) {
	// if (other.id != null) {
	// return false;
	// }
	// } else if (!id.equals(other.id)) {
	// return false;
	// }
	// if (informationSourceList == null) {
	// if (other.informationSourceList != null) {
	// return false;
	// }
	// } else if (!informationSourceList.equals(other.informationSourceList)) {
	// return false;
	// }
	// if (listOfManagedVmes == null) {
	// if (other.listOfManagedVmes != null) {
	// return false;
	// }
	// } else if (!listOfManagedVmes.equals(other.listOfManagedVmes)) {
	// return false;
	// }
	// return true;
	// }

}
