package org.fao.fi.vme.rsg.test.service;

import org.fao.fi.vme.domain.model.FisheryAreasHistory;
import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.Rfmo;
import org.fao.fi.vme.domain.model.VMEsHistory;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.rsg.service.RsgServiceReadImplVme;
import org.fao.fi.vme.rsg.service.RsgServiceWriteImplVme;
import org.fao.fi.vme.sync.factsheets.listeners.impl.SyncFactsheetChangeListener;
import org.fao.fi.vme.sync.factsheets.updaters.impl.DummyFactsheetUpdater;
import org.gcube.application.rsg.support.builder.impl.ReportManagerReportBuilder;
import org.gcube.application.rsg.support.compiler.impl.AnnotationBasedReportCompiler;
import org.gcube.application.rsg.support.evaluator.impl.JEXLReportEvaluator;
import org.gcube.application.rsg.test.RsgAbstractServiceTest;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.runner.RunWith;
import org.vme.dao.config.vme.VmeDataBaseProducerApplicationScope;
import org.vme.dao.config.vme.VmePersistenceUnitConfiguration;
import org.vme.dao.impl.jpa.ReferenceDaoImpl;

//RSGServiceWriteMockImpl
@RunWith(CdiRunner.class)
@ActivatedAlternatives({ AnnotationBasedReportCompiler.class, JEXLReportEvaluator.class,
		ReportManagerReportBuilder.class, VmePersistenceUnitConfiguration.class, RsgServiceReadImplVme.class,
		SyncFactsheetChangeListener.class, DummyFactsheetUpdater.class, ReferenceDaoImpl.class,
		RsgServiceWriteImplVme.class })
@AdditionalClasses({ Vme.class, Rfmo.class, GeneralMeasure.class, InformationSource.class, FisheryAreasHistory.class,
		VMEsHistory.class, VmeDataBaseProducerApplicationScope.class })
public class RsgServiceImplTest extends RsgAbstractServiceTest {
}