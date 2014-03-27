package org.vme.dao.config.figis;

import javax.enterprise.inject.Alternative;

import org.vme.dao.config.AbstractPersistenceUnitConfiguration;

/**
 * 
 * Produces the link to the figis DB.
 * 
 * TODO investigate whether this one should be moved to vme-configuration.
 * 
 * @author Erik van Ingen
 * 
 */
@Alternative @FigisDB
public class FigisTestPersistenceUnitConfiguration extends AbstractPersistenceUnitConfiguration {
	/* (non-Javadoc)
	 * @see org.vme.dao.config.AbstractDataBaseConfiguration#doGetPersistanceUnitName()
	 */
	@Override
	protected String doGetPersistenceUnitName() {
		return "figis-persistence-test";
	}
}