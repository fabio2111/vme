package org.fao.fi.vme.test;

import org.fao.fi.figis.domain.RefVme;

public class RefVmeMock {

	public static RefVme create() {

		RefVme r = new RefVme();
		r.setId(456132l);
		r.setMeta(456);
		return r;

	}
}