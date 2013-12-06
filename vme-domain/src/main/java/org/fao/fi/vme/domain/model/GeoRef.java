package org.fao.fi.vme.domain.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.fao.fi.vme.domain.interfaces.Year;
import org.gcube.application.rsg.support.compiler.bridge.annotations.RSGReferenced;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGConverter;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGIdentifier;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGName;
import org.gcube.application.rsg.support.compiler.bridge.converters.impl.IntegerDataConverter;
import org.gcube.application.rsg.support.compiler.bridge.converters.impl.LongDataConverter;

/**
 * 
 * @author Erik van Ingen
 * 
 */
@RSGReferenced
@Entity(name = "GEO_REF")
public class GeoRef implements Year<GeoRef> {

	@RSGIdentifier
	@RSGConverter(LongDataConverter.class)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * 
	 */
	@RSGName("Year")
	@RSGConverter(IntegerDataConverter.class)
	private Integer year;

	/**
	 *   
	 */
	@RSGName("Geographic feature ID")
	private String geographicFeatureID;

	/**
	 * 
	 */
	@OneToOne(cascade = CascadeType.MERGE)
	private Vme vme;

	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public Integer getYear() {
		return year;
	}

	@Override
	public void setYear(Integer year) {
		this.year = year;
	}

	public String getGeographicFeatureID() {
		return geographicFeatureID;
	}

	public void setGeographicFeatureID(String geographicFeatureID) {
		this.geographicFeatureID = geographicFeatureID;
	}

	public Vme getVme() {
		return vme;
	}

	public void setVme(Vme vme) {
		this.vme = vme;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.geographicFeatureID == null) ? 0 : this.geographicFeatureID.hashCode());
		result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
		result = prime * result + ((this.vme == null) ? 0 : this.vme.hashCode());
		result = prime * result + ((this.year == null) ? 0 : this.year.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GeoRef other = (GeoRef) obj;
		if (this.geographicFeatureID == null) {
			if (other.geographicFeatureID != null)
				return false;
		} else if (!this.geographicFeatureID.equals(other.geographicFeatureID))
			return false;
		if (this.id == null) {
			if (other.id != null)
				return false;
		} else if (!this.id.equals(other.id))
			return false;
		if (this.vme == null) {
			if (other.vme != null)
				return false;
		} else if (!this.vme.equals(other.vme))
			return false;
		if (this.year == null) {
			if (other.year != null)
				return false;
		} else if (!this.year.equals(other.year))
			return false;
		return true;
	}
}