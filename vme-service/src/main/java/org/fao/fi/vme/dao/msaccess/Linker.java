package org.fao.fi.vme.dao.msaccess;

import java.util.List;
import java.util.Map;

import org.fao.fi.vme.dao.msaccess.tables.Meetings;
import org.fao.fi.vme.dao.msaccess.tables.VME;
import org.fao.fi.vme.domain.Meeting;
import org.fao.fi.vme.domain.Rfmo;
import org.fao.fi.vme.domain.Vme;

public class Linker {

	/**
	 * Link the diffent domain objects, using the original MS-Access table objects.
	 * 
	 * 
	 * @param objectCollectionList
	 * @param tables
	 */
	public void link(List<ObjectCollection> objectCollectionList, List<Table> tables) {
		for (ObjectCollection o : objectCollectionList) {
			Map<Object, Object> domainTableMap = o.getDomainTableMap();
			List<Object> objectList = o.getObjectList();
			for (Object object : objectList) {
				linkObject(object, domainTableMap, objectCollectionList, tables);
			}

		}

	}

	private void linkObject(Object domainObject, Map<Object, Object> domainTableMap,
			List<ObjectCollection> objectCollectionList, List<Table> tables) {
		if (domainObject instanceof Vme) {
			linkVmeObject(domainObject, domainTableMap, objectCollectionList);
		}
		// if (domainObject instanceof Rfmo) {
		// linkRfmoObject(domainObject, objectCollectionList, tables);
		// }
		if (domainObject instanceof Meeting) {
			linkMeetingObject(domainObject, domainTableMap, objectCollectionList);
		}
		// if (domainObject instanceof SpecificMeasures) {
		// linkSpecificMeasuresObject(domainObject, objectCollectionList, tables);
		// }
		// if (domainObject instanceof GeneralMeasures) {
		// linkGeneralMeasuresObject(domainObject, objectCollectionList, tables);
		// }

	}

	/**
	 * filling up rfmo's with meeting
	 * 
	 * 
	 * @param meetingDomainObject
	 * @param domainTableMap
	 * @param objectCollectionList
	 */
	private void linkMeetingObject(Object meetingDomainObject, Map<Object, Object> domainTableMap,
			List<ObjectCollection> objectCollectionList) {
		Meeting meeting = (Meeting) meetingDomainObject;
		Meetings meetingsRecord = (Meetings) domainTableMap.get(meeting);
		for (ObjectCollection oc : objectCollectionList) {
			if (oc.getClazz() == Rfmo.class) {
				List<Object> objectList = oc.getObjectList();
				for (Object object : objectList) {
					Rfmo o = (Rfmo) object;
					int rfbId = (new Integer(meetingsRecord.getRFB_ID())).intValue();
					if (o.getId() == rfbId) {
						o.getMeetingList().add(meeting);
					}
				}
			}
		}

	}

	/**
	 * fills up Vme with a Rfmo and the other way around.
	 * 
	 * 
	 * @param vmeObject
	 * @param domainTableMap
	 * @param objectCollectionList
	 */
	private void linkVmeObject(Object vmeObject, Map<Object, Object> domainTableMap,
			List<ObjectCollection> objectCollectionList) {
		Vme vme = (Vme) vmeObject;
		VME vmeRecord = (VME) domainTableMap.get(vme);
		for (ObjectCollection oc : objectCollectionList) {
			if (oc.getClazz() == Rfmo.class) {
				List<Object> objectList = oc.getObjectList();
				for (Object object : objectList) {
					Rfmo o = (Rfmo) object;
					int rfbId = (new Integer(vmeRecord.getRFB_ID())).intValue();
					if (o.getId() == rfbId) {
						vme.setRfmo(o);
						if (!o.getManagedVmeList().contains(o)) {
							o.getManagedVmeList().add(vme);
						}
					}
				}
			}
		}
	}
}
