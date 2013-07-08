package org.fao.fi.vme.sync2;

import java.util.List;

import javax.inject.Inject;

import org.fao.fi.figis.dao.FigisDao;
import org.fao.fi.figis.domain.RefVme;
import org.fao.fi.vme.dao.VmeDao;
import org.fao.fi.vme.domain.Vme;
import org.fao.fi.vme.msaccess.component.VmeDaoException;

/**
 * 
 * map the REF_VME and the FS_OBSERVATION
 * 
 * 
 * @author Erik van Ingen
 * 
 */

public class XmlSync implements Sync {

	public static final Short ORDER = -1;
	public static final Integer COLLECTION = 7300;

	@Inject
	FigisDao figisDao;

	@Inject
	VmeDao vmeDao;

	// @Override
	@Override
	public void sync() {
		List<Vme> objects = vmeDao.loadVmes();
		for (Vme vme : objects) {
			RefVme object = (RefVme) figisDao.find(RefVme.class, vme.getId());

			if (object != null && object.getId() <= 0) {
				throw new VmeDaoException("object found in DB withough id");
			}
			if (object == null) {
				// do the new stuff
				object = generateNewRefVme();

				// map it
				map(vme, object);

				// and store it
				figisDao.persist(object);
			} else {
				map(vme, object);
				figisDao.merge(object);
			}
		}
	}

	private RefVme generateNewRefVme() {
		RefVme r = new RefVme();

		return r;
	}

	private void map(Vme vme, RefVme object) {
		object.setId(vme.getId());
		object.setMeta(172000);
	}
}