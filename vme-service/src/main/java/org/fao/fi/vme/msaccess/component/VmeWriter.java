package org.fao.fi.vme.msaccess.component;

import java.util.List;

import javax.inject.Inject;

import org.fao.fi.vme.msaccess.model.ObjectCollection;

/**
 * Writes the data to the vme DB.
 * 
 * @author Erik van Ingen
 * 
 */
public class VmeWriter {
	@Inject
	TableWriter tableWriter;

	@Inject
	VmeDao4Msaccess vmeDao4Msaccess;

	public void persistNew(List<ObjectCollection> objectCollectionList) {
		vmeDao4Msaccess.persistObjectCollection(objectCollectionList);
	}

	// public void write(List<ObjectCollection> objectCollectionList) {
	// for (ObjectCollection objectCollection : objectCollectionList) {
	// // System.out.println(objectCollection.getClazz().getSimpleName());
	// tableWriter.write(objectCollection);
	// }
	// }
	//
	// public void merge(List<ObjectCollection> objectCollectionList) {
	// for (ObjectCollection objectCollection : objectCollectionList) {
	// LOG.debug("========================");
	// LOG.debug(objectCollection.getClazz().getSimpleName());
	// tableWriter.merge(objectCollection);
	// }
	// }

}
