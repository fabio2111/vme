package org.fao.fi.vme.sync.factsheets.listeners.impl.vmeweb;

import java.io.IOException;

import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.Rfmo;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.model.extended.FisheryAreasHistory;
import org.fao.fi.vme.domain.model.extended.VMEsHistory;
import org.fao.fi.vme.sync.factsheets.listeners.FactsheetChangeListener;
import org.fao.fi.vme.sync.factsheets.listeners.impl.SyncFactsheetChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ning.http.client.AsyncHttpClient;

public class VmeWebSearchCache implements FactsheetChangeListener {

	static final protected Logger LOG = LoggerFactory.getLogger(SyncFactsheetChangeListener.class);
	static final private String errorMessage = "Did not manage to clear the cach in vme-web search properly";

	void clear() {
		AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
		Handler handler = new Handler(asyncHttpClient);
		try {
			asyncHttpClient.prepareGet("http://localhost:8081/vme-web/webservice/cache-delete/").execute(handler);
		} catch (IOException e) {
			LOG.error(errorMessage, e);
		}
	}

	@Override
	public void VMEChanged(Vme... changed) throws Exception {
		clear();
	}

	@Override
	public void VMEAdded(Vme... added) throws Exception {
		clear();

	}

	@Override
	public void VMEDeleted(Vme... deleted) throws Exception {
		clear();

	}

	@Override
	public void generalMeasureChanged(GeneralMeasure... changed) throws Exception {
		clear();
	}

	@Override
	public void generalMeasureAdded(GeneralMeasure... added) throws Exception {
		clear();

	}

	@Override
	public void generalMeasureDeleted(Rfmo owner, GeneralMeasure... deleted) throws Exception {
		clear();

	}

	@Override
	public void informationSourceChanged(InformationSource... changed) throws Exception {
		clear();

	}

	@Override
	public void informationSourceAdded(InformationSource... added) throws Exception {
		clear();

	}

	@Override
	public void informationSourceDeleted(Rfmo owner, InformationSource... deleted) throws Exception {
		clear();

	}

	@Override
	public void fishingFootprintChanged(FisheryAreasHistory... changed) throws Exception {
		clear();

	}

	@Override
	public void fishingFootprintAdded(FisheryAreasHistory... added) throws Exception {
		clear();

	}

	@Override
	public void fishingFootprintDeleted(Rfmo owner, FisheryAreasHistory... deleted) throws Exception {
		clear();

	}

	@Override
	public void regionalHistoryChanged(VMEsHistory... changed) throws Exception {
		clear();

	}

	@Override
	public void regionalHistoryAdded(VMEsHistory... added) throws Exception {
		clear();

	}

	@Override
	public void regionalHistoryDeleted(Rfmo owner, VMEsHistory... deleted) throws Exception {
		clear();

	}

}
