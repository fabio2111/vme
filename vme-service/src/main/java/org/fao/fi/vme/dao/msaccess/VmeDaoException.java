package org.fao.fi.vme.dao.msaccess;

public class VmeDaoException extends RuntimeException {

	public VmeDaoException(Exception e) {
		super(e);
	}

	public VmeDaoException(String message) {
		super(message);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1359907165992410885L;

}
