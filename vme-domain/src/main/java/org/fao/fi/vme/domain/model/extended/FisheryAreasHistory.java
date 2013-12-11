/**
 * (c) 2013 FAO / UN (project: vme-domain)
 */
package org.fao.fi.vme.domain.model.extended;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.fao.fi.vme.domain.interfaces.Year;
import org.fao.fi.vme.domain.model.History;
import org.fao.fi.vme.domain.model.MultiLingualString;
import org.fao.fi.vme.domain.model.Rfmo;
import org.fao.fi.vme.domain.support.MultiLingualStringConverter;
import org.gcube.application.rsg.support.compiler.bridge.annotations.RSGReferenceReport;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGConverter;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGIdentifier;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGName;
import org.gcube.application.rsg.support.compiler.bridge.converters.impl.IntegerDataConverter;
import org.gcube.application.rsg.support.compiler.bridge.converters.impl.LongDataConverter;

/**
 * Place your class / interface description here.
 * 
 * History:
 * 
 * ------------- --------------- ----------------------- Date Author Comment
 * ------------- --------------- ----------------------- 13/nov/2013 Fabio
 * Creation.
 * 
 * @version 1.0
 * @since 13/nov/2013
 */
@RSGReferenceReport(name = "Fishery Areas History")
@Entity(name = "FISHERY_AREAS_HISTORY")
public class FisheryAreasHistory implements History, Year<History> {

	/** 
	 * 
	 */
	@RSGIdentifier
	@RSGConverter(LongDataConverter.class)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * FisheryAreasHistory has 0,1 Rfmo
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	private Rfmo rfmo;

	/**
	 *  
	 */
	@RSGName("Year")
	@RSGConverter(IntegerDataConverter.class)
	private Integer year;

	/**
	 *  
	 */
	@RSGName("History")
	@RSGConverter(MultiLingualStringConverter.class)
	@OneToOne(cascade = { CascadeType.ALL })
	private MultiLingualString history;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fao.fi.vme.domain.model.IHistory#getId()
	 */
	@Override
	public Long getId() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fao.fi.vme.domain.model.IHistory#setId(java.lang.Long)
	 */
	@Override
	public void setId(Long id) {
		this.id = id;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fao.fi.vme.domain.model.IHistory#getYear()
	 */
	@Override
	public Integer getYear() {
		return year;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fao.fi.vme.domain.model.IHistory#setYear(java.lang.Integer)
	 */
	@Override
	public void setYear(Integer year) {
		this.year = year;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fao.fi.vme.domain.model.IHistory#getHistory()
	 */
	@Override
	public MultiLingualString getHistory() {
		return history;
	}

	public Rfmo getRfmo() {
		return rfmo;
	}

	public void setRfmo(Rfmo rfmo) {
		this.rfmo = rfmo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fao.fi.vme.domain.model.IHistory#setHistory(org.fao.fi.vme.domain
	 * .model.MultiLingualString)
	 */
	@Override
	public void setHistory(MultiLingualString history) {
		this.history = history;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.history == null) ? 0 : this.history.hashCode());
		result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
		result = prime * result + ((this.year == null) ? 0 : this.year.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		FisheryAreasHistory other = (FisheryAreasHistory) obj;
		if (this.history == null) {
			if (other.history != null)
				return false;
		} else if (!this.history.equals(other.history))
			return false;
		if (this.id == null) {
			if (other.id != null)
				return false;
		} else if (!this.id.equals(other.id))
			return false;
		if (this.year == null) {
			if (other.year != null)
				return false;
		} else if (!this.year.equals(other.year))
			return false;
		return true;
	}
}