/**
 * (c) 2014 FAO / UN (project: vme-service)
 */
package org.fao.fi.vme.sync.factsheets.listeners;

import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.Rfmo;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.model.extended.FisheryAreasHistory;
import org.fao.fi.vme.domain.model.extended.VMEsHistory;

/**
 * Place your class / interface description here.
 *
 * History:
 *
 * ------------- --------------- -----------------------
 * Date			 Author			 Comment
 * ------------- --------------- -----------------------
 * 20 Feb 2014   Fiorellato     Creation.
 *
 * @version 1.0
 * @since 20 Feb 2014
 */
public interface FactsheetChangeListener {
	void vmeChanged(Vme... changed) throws Exception;
	void vmeAdded(Vme... added) throws Exception;
	void vmeDeleted(Vme... deleted) throws Exception;
	
	void generalMeasureChanged(GeneralMeasure... changed) throws Exception;
	void generalMeasureAdded(GeneralMeasure... added) throws Exception;
	void generalMeasureDeleted(Rfmo owner, GeneralMeasure... deleted) throws Exception;
	
	void informationSourceChanged(InformationSource... changed) throws Exception;
	void informationSourceAdded(InformationSource... added) throws Exception;
	void informationSourceDeleted(Rfmo owner, InformationSource... deleted) throws Exception;
	
	void fishingFootprintChanged(FisheryAreasHistory... changed) throws Exception;
	void fishingFootprintAdded(FisheryAreasHistory... added) throws Exception;
	void fishingFootprintDeleted(Rfmo owner, FisheryAreasHistory... deleted) throws Exception;
	
	void regionalHistoryChanged(VMEsHistory... changed) throws Exception;
	void regionalHistoryAdded(VMEsHistory... added) throws Exception;
	void regionalHistoryDeleted(Rfmo owner, VMEsHistory... deleted) throws Exception;
}