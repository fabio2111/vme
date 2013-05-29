package org.fao.fi.vme.msaccess.model;

import java.util.List;

public abstract class Collection {

	private Class<?> clazz;
	private List<Object> objectList;

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public List<Object> getObjectList() {
		return objectList;
	}

	public void setObjectList(List<Object> records) {
		this.objectList = records;
	}

}