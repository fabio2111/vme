package org.fao.fi.figis.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.fao.fi.dao.Dao;
import org.fao.fi.figis.domain.RefVme;
import org.fao.fi.vme.dao.config.FigisDB;

public class FigisDao extends Dao {

	@Inject
	@FigisDB
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<RefVme> loadRefVmes() {
		return (List<RefVme>) this.generateTypedQuery(em, RefVme.class).getResultList();
	}

	public RefVme loadRefVme(int id) {
		return em.find(RefVme.class, id);
	}

	public void merge(Object object) {
		em.getTransaction().begin();
		em.merge(object);
		em.getTransaction().commit();
	}

	public void persist(Object object) {
		em.getTransaction().begin();
		em.persist(object);
		em.getTransaction().commit();
	}

}
