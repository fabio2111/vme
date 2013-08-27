package org.fao.fi.vme.sync2;

import java.util.List;

import javax.inject.Inject;

import org.fao.fi.figis.dao.FigisDao;
import org.fao.fi.figis.domain.RefWaterArea;
import org.fao.fi.figis.domain.rule.Figis;
import org.fao.fi.vme.dao.VmeDao;
import org.fao.fi.vme.domain.GeoRef;

/**
 * 
 * Will the GeoRefs be taken from the geospatial tables? Will the GeoRefs be filled by SyncBatch1?
 * 
 * GeoRefSync pushes the water area refs from the vme DB to the figis DB.
 * 
 * 
 * TODO extract isolate code from GeoRefSync and VmeRefSync
 * 
 * @author Erik van Ingen
 * 
 */
public class GeoRefSync implements Sync {

	@Inject
	FigisDao figisDao;

	@Inject
	VmeDao vmeDao;

	@Override
	public void sync() {
		@SuppressWarnings("unchecked")
		List<GeoRef> objects = (List<GeoRef>) vmeDao.loadObjects(GeoRef.class);
		for (GeoRef geoRef : objects) {
			// do the new stuff
			RefWaterArea object = generateNewRefWaterArea();

			// map it
			map(geoRef, object);

			// and sync it
			figisDao.syncRefWaterArea(geoRef.getGeographicFeatureID(), object);
		}
	}

	private RefWaterArea generateNewRefWaterArea() {
		RefWaterArea r = new RefWaterArea();
		r.setMeta(Figis.META_WATER_AREA);
		return r;
	}

	private void map(GeoRef geoRef, RefWaterArea object) {
		object.setExternalId(geoRef.getGeographicFeatureID());
	}

}
