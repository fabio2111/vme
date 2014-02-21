/**
 * (c) 2014 FAO / UN (project: vme-service)
 */
package org.fao.fi.vme.sync.factsheets.impl;

import javax.enterprise.inject.Alternative;

import org.fao.fi.vme.sync.factsheets.FactsheetUpdater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Place your class / interface description here.
 *
 * History:
 *
 * ------------- --------------- -----------------------
 * Date			 Author			 Comment
 * ------------- --------------- -----------------------
 * 21 Feb 2014   Fiorellato     Creation.
 *
 * @version 1.0
 * @since 21 Feb 2014
 */
@Alternative
public class DummyFactsheetUpdaterImpl implements FactsheetUpdater {
	static final private Logger LOG = LoggerFactory.getLogger(DummyFactsheetUpdaterImpl.class);

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.FactsheetUpdater#createFactsheets(java.lang.Long[])
	 */
	@Override
	public void createFactsheets(Long... vmeIDs) throws Exception {
		LOG.info("Creating factsheets for {} VMEs with ID {}", vmeIDs.length, vmeIDs);
		
		Thread.sleep(500);
		
		LOG.info("Created factsheets for {} VMEs with ID {}", vmeIDs.length, vmeIDs);
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.FactsheetUpdater#updateFactsheets(java.lang.Long[])
	 */
	@Override
	public void updateFactsheets(Long... vmeIDs) throws Exception {
		LOG.info("Updating factsheets for {} VMEs with ID {}", vmeIDs.length, vmeIDs);
		
		Thread.sleep(500);
		
		LOG.info("Updated factsheets for {} VMEs with ID {}", vmeIDs.length, vmeIDs);
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.FactsheetUpdater#deleteFactsheets(java.lang.Long[])
	 */
	@Override
	public void deleteFactsheets(Long... vmeIDs) throws Exception {
		LOG.info("Deleting factsheets for {} VMEs with ID {}", vmeIDs.length, vmeIDs);

		Thread.sleep(500);
		
		LOG.info("Deleted factsheets for {} VMEs with ID {}", vmeIDs.length, vmeIDs);
	}
}
