package org.fao.fi.figis.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * The persistent class for the FS_VME_OBSERVATION database table.
 * 
 */
@Entity
@Table(name = "FS_VME_OBSERVATION")
public class VmeObservation implements Serializable {
	private static final long serialVersionUID = 1L;

	// bi-directional many-to-one association to RefVme
	@ManyToOne
	// @PrimaryKeyJoinColumn(name = "CD_VME")
	@JoinColumn(name = "CD_VME", nullable = false)
	private RefVme refVme;

	// bi-directional one-to-one association to FsObservation
	@Id
	@OneToOne
	// @PrimaryKeyJoinColumn(name = "CD_OBSERVATION")
	@JoinColumn(name = "CD_OBSERVATION", nullable = false)
	private Observation fsObservation;

	@Column(name = "REPORTING_YEAR", nullable = true)
	private String reportingYear;

	public RefVme getRefVme() {
		return refVme;
	}

	public void setRefVme(RefVme refVme) {
		this.refVme = refVme;
	}

	public Observation getFsObservation() {
		return fsObservation;
	}

	public void setFsObservation(Observation fsObservation) {
		this.fsObservation = fsObservation;
	}

	public String getReportingYear() {
		return reportingYear;
	}

	public void setReportingYear(String reportingYear) {
		this.reportingYear = reportingYear;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}