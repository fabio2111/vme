package org.fao.fi.vme.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * 
 * @author Erik van Ingen
 * 
 */
@Entity(name = "GEO_REF")
public class GeoRef implements YearObject<GeoRef> {

	@Id
	// @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * 
	 */
	private Integer year;

	/**
	 * 
	 */
	private BigDecimal geographicFeatureID;

	/**
	 * 
	 */
	@OneToOne
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

	public BigDecimal getGeographicFeatureID() {
		return geographicFeatureID;
	}

	public void setGeographicFeatureID(BigDecimal geographicFeatureID) {
		this.geographicFeatureID = geographicFeatureID;
	}

	public Vme getVme() {
		return vme;
	}

	public void setVme(Vme vme) {
		this.vme = vme;
	}

}
