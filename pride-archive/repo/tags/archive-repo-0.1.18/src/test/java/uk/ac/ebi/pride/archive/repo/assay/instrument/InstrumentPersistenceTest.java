package uk.ac.ebi.pride.archive.repo.assay.instrument;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.pride.archive.repo.param.CvParam;
import uk.ac.ebi.pride.archive.repo.param.CvParamRepository;
import uk.ac.ebi.pride.archive.dataprovider.param.CvParamProvider;
import uk.ac.ebi.pride.archive.dataprovider.param.ParamProvider;
import uk.ac.ebi.pride.archive.repo.assay.Assay;
import uk.ac.ebi.pride.archive.repo.assay.AssayRepository;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Jose A. Dianes
 * @version $Id$
 */
@ContextConfiguration(locations = {"/test-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class InstrumentPersistenceTest {

    private static final long INSTRUMENT_1_ID = 1111;
    private static final long PROJECT_1_ID = 11111;
    private static final long ASSAY_1_ID = 44444;
    private static final int NUM_INSTRUMENTS_PROJECT_1 = 1;
    private static final int NUM_INSTRUMENTS_ASSAY_1 = 1;
    private static final String MODEL_NAME = "FT_ICR";
    private static final String MODEL_VALUE = "icr";
    private static final long INSTRUMENT_1_SOURCE_INSTRUMENT_COMPONENT_ID = 1111;
    private static final int INSTRUMENT_1_SOURCE_INSTRUMENT_COMPONENT_ORDER = 1;
    private static final long INSTRUMENT_1_ANALYZER_INSTRUMENT_COMPONENT_ID = 1112;
    private static final int INSTRUMENT_1_ANALYZER_INSTRUMENT_COMPONENT_ORDER = 2;
    private static final long INSTRUMENT_1_DETECTOR_INSTRUMENT_COMPONENT_ID = 1113;
    private static final int INSTRUMENT_1_DETECTOR_INSTRUMENT_COMPONENT_ORDER = 3;
    private static final String INSTRUMENT_1_SOURCE_INSTRUMENT_COMPONENT_NAME = "ESI";
    private static final String INSTRUMENT_1_SOURCE_INSTRUMENT_COMPONENT_ACCESSION = "source";
    private static final String INSTRUMENT_1_SOURCE_INSTRUMENT_COMPONENT_LABEL = "MS";
    private static final String INSTRUMENT_1_ANALYZER1_INSTRUMENT_COMPONENT_NAME = "TOF";
    private static final String INSTRUMENT_1_ANALYZER1_INSTRUMENT_COMPONENT_ACCESSION = "analyzer1";
    private static final String INSTRUMENT_1_ANALYZER1_INSTRUMENT_COMPONENT_LABEL = "MS";
    private static final String INSTRUMENT_1_ANALYZER2_INSTRUMENT_COMPONENT_NAME = "LTQ";
    private static final String INSTRUMENT_1_ANALYZER2_INSTRUMENT_COMPONENT_ACCESSION = "analyzer2";
    private static final String INSTRUMENT_1_ANALYZER2_INSTRUMENT_COMPONENT_LABEL = "MS";
    private static final String INSTRUMENT_1_DETECTOR_INSTRUMENT_COMPONENT_NAME = "plate";
    private static final String INSTRUMENT_1_DETECTOR_INSTRUMENT_COMPONENT_ACCESSION = "detector";
    private static final String INSTRUMENT_1_DETECTOR_INSTRUMENT_COMPONENT_LABEL = "MS";

    private static final String ANOTHER_INSTRUMENT_MODEL_NAME = "FT_ICR";
    private static final String ANOTHER_INSTRUMENT_MODEL_VALUE = "icr";
    private static final String ANOTHER_INSTRUMENT_MODEL_LABEL = "Instrument Model Cv Param Label";
    private static final String ANOTHER_INSTRUMENT_MODEL_ACCESSION = "Instrument Model Cv Param Accession";
    private static final int ANOTHER_INSTRUMENT_SOURCE_INSTRUMENT_COMPONENT_ORDER = 1;
    private static final int ANOTHER_INSTRUMENT_ANALYZER_INSTRUMENT_COMPONENT_ORDER = 2;
    private static final int ANOTHER_INSTRUMENT_DETECTOR_INSTRUMENT_COMPONENT_ORDER = 3;
    private static final String ANOTHER_INSTRUMENT_SOURCE_INSTRUMENT_COMPONENT_NAME = "ESI";
    private static final String ANOTHER_INSTRUMENT_SOURCE_INSTRUMENT_COMPONENT_ACCESSION = "another source";
    private static final String ANOTHER_INSTRUMENT_SOURCE_INSTRUMENT_COMPONENT_LABEL = "MS";
    private static final String ANOTHER_INSTRUMENT_ANALYZER_INSTRUMENT_COMPONENT_NAME = "TOF";
    private static final String ANOTHER_INSTRUMENT_ANALYZER_INSTRUMENT_COMPONENT_ACCESSION = "analyzer3";
    private static final String ANOTHER_INSTRUMENT_ANALYZER_INSTRUMENT_COMPONENT_LABEL = "MS";
    private static final String ANOTHER_INSTRUMENT_DETECTOR_INSTRUMENT_COMPONENT_NAME = "plate";
    private static final String ANOTHER_INSTRUMENT_DETECTOR_INSTRUMENT_COMPONENT_ACCESSION = "another detector";
    private static final String ANOTHER_INSTRUMENT_DETECTOR_INSTRUMENT_COMPONENT_LABEL = "MS";
    private static final String ANOTHER_INSTRUMENT_SOURCE_INSTRUMENT_COMPONENT_VALUE = "source component value";
    private static final String ANOTHER_INSTRUMENT_ANALYZER_INSTRUMENT_COMPONENT_VALUE = "analyzer component value";
    private static final String ANOTHER_INSTRUMENT_DETECTOR_INSTRUMENT_COMPONENT_VALUE = "detector component value";

    @Autowired
    private InstrumentRepository instrumentRepository;

    @Autowired
    private AssayRepository assayRepository;

    @Autowired
    private CvParamRepository cvParamRepository;

    @Test
    @Transactional
    public void testGetById() throws Exception {

        Instrument instrument = instrumentRepository.findOne(INSTRUMENT_1_ID);

        checkIsInstrument1InDb(instrument);

    }

    @Test
    @Transactional
    public void testGetByAssayId() throws Exception {

        List<Instrument> instruments = instrumentRepository.findAllByAssayId(ASSAY_1_ID);

        assertNotNull(instruments);

        boolean containsInstrument1InDb = false;
        for (Instrument theInstrument: instruments) {
            containsInstrument1InDb = (theInstrument.getId() == INSTRUMENT_1_ID);
            if (containsInstrument1InDb)
                checkIsInstrument1InDb(theInstrument);
        }

        assertTrue(containsInstrument1InDb);

    }

    @Test
    @Transactional
    public void testSaveAndGet() throws Exception {

        Instrument instrument = new Instrument();

        CvParam instrumentModel = new CvParam();
        instrumentModel.setName(ANOTHER_INSTRUMENT_MODEL_NAME);
        instrumentModel.setCvLabel(ANOTHER_INSTRUMENT_MODEL_LABEL);
        instrumentModel.setAccession(ANOTHER_INSTRUMENT_MODEL_ACCESSION);
        cvParamRepository.save(instrumentModel);
        instrument.setCvParam(instrumentModel);
        instrument.setValue(ANOTHER_INSTRUMENT_MODEL_VALUE);

        SourceInstrumentComponent sourceInstrumentComponent = new SourceInstrumentComponent();
        sourceInstrumentComponent.setInstrument(instrument);
        sourceInstrumentComponent.setOrder(ANOTHER_INSTRUMENT_SOURCE_INSTRUMENT_COMPONENT_ORDER);
        InstrumentComponentCvParam sourceInstrumentComponentCvParam = new InstrumentComponentCvParam();
        sourceInstrumentComponentCvParam.setInstrumentComponent(sourceInstrumentComponent);
        sourceInstrumentComponentCvParam.setValue(ANOTHER_INSTRUMENT_SOURCE_INSTRUMENT_COMPONENT_VALUE);

        CvParam sourceInstrumentComponentCvParamCvParam = new CvParam();
        sourceInstrumentComponentCvParamCvParam.setAccession(ANOTHER_INSTRUMENT_SOURCE_INSTRUMENT_COMPONENT_ACCESSION);
        sourceInstrumentComponentCvParamCvParam.setCvLabel(ANOTHER_INSTRUMENT_SOURCE_INSTRUMENT_COMPONENT_LABEL);
        sourceInstrumentComponentCvParamCvParam.setName(ANOTHER_INSTRUMENT_SOURCE_INSTRUMENT_COMPONENT_NAME);
        sourceInstrumentComponentCvParam.setCvParam(sourceInstrumentComponentCvParamCvParam);
        cvParamRepository.save(sourceInstrumentComponentCvParamCvParam);

        LinkedList<InstrumentComponentCvParam> sourceInstrumentComponentCvParams = new LinkedList<InstrumentComponentCvParam>();
        sourceInstrumentComponentCvParams.add(sourceInstrumentComponentCvParam);
        sourceInstrumentComponent.setInstrumentComponentCvParams(sourceInstrumentComponentCvParams);
        LinkedList<SourceInstrumentComponent> sourceInstrumentComponents = new LinkedList<SourceInstrumentComponent>();
        sourceInstrumentComponents.add(sourceInstrumentComponent);
        instrument.setSources(sourceInstrumentComponents);

        AnalyzerInstrumentComponent analyzerInstrumentComponent = new AnalyzerInstrumentComponent();
        analyzerInstrumentComponent.setInstrument(instrument);
        analyzerInstrumentComponent.setOrder(ANOTHER_INSTRUMENT_ANALYZER_INSTRUMENT_COMPONENT_ORDER);
        InstrumentComponentCvParam analyzerInstrumentComponentCvParam = new InstrumentComponentCvParam();
        analyzerInstrumentComponentCvParam.setInstrumentComponent(analyzerInstrumentComponent);
        analyzerInstrumentComponentCvParam.setValue(ANOTHER_INSTRUMENT_ANALYZER_INSTRUMENT_COMPONENT_VALUE);

        CvParam analyzerInstrumentComponentCvParamCvParam = new CvParam();
        analyzerInstrumentComponentCvParamCvParam.setAccession(ANOTHER_INSTRUMENT_ANALYZER_INSTRUMENT_COMPONENT_ACCESSION);
        analyzerInstrumentComponentCvParamCvParam.setCvLabel(ANOTHER_INSTRUMENT_ANALYZER_INSTRUMENT_COMPONENT_LABEL);
        analyzerInstrumentComponentCvParamCvParam.setName(ANOTHER_INSTRUMENT_ANALYZER_INSTRUMENT_COMPONENT_NAME);
        analyzerInstrumentComponentCvParam.setCvParam(analyzerInstrumentComponentCvParamCvParam);
        cvParamRepository.save(analyzerInstrumentComponentCvParamCvParam);

        LinkedList<InstrumentComponentCvParam> analyzerInstrumentComponentCvParams = new LinkedList<InstrumentComponentCvParam>();
        analyzerInstrumentComponentCvParams.add(analyzerInstrumentComponentCvParam);
        analyzerInstrumentComponent.setInstrumentComponentCvParams(analyzerInstrumentComponentCvParams);
        LinkedList<AnalyzerInstrumentComponent> analyzerInstrumentComponents = new LinkedList<AnalyzerInstrumentComponent>();
        analyzerInstrumentComponents.add(analyzerInstrumentComponent);
        instrument.setAnalyzers(analyzerInstrumentComponents);

        DetectorInstrumentComponent detectorInstrumentComponent = new DetectorInstrumentComponent();
        detectorInstrumentComponent.setInstrument(instrument);
        detectorInstrumentComponent.setOrder(ANOTHER_INSTRUMENT_DETECTOR_INSTRUMENT_COMPONENT_ORDER);
        InstrumentComponentCvParam detectorInstrumentComponentCvParam = new InstrumentComponentCvParam();
        detectorInstrumentComponentCvParam.setInstrumentComponent(detectorInstrumentComponent);
        detectorInstrumentComponentCvParam.setValue(ANOTHER_INSTRUMENT_DETECTOR_INSTRUMENT_COMPONENT_VALUE);

        CvParam detectorInstrumentComponentCvParamCvParam = new CvParam();
        detectorInstrumentComponentCvParamCvParam.setAccession(ANOTHER_INSTRUMENT_DETECTOR_INSTRUMENT_COMPONENT_ACCESSION);
        detectorInstrumentComponentCvParamCvParam.setCvLabel(ANOTHER_INSTRUMENT_DETECTOR_INSTRUMENT_COMPONENT_LABEL);
        detectorInstrumentComponentCvParamCvParam.setName(ANOTHER_INSTRUMENT_DETECTOR_INSTRUMENT_COMPONENT_NAME);
        detectorInstrumentComponentCvParam.setCvParam(detectorInstrumentComponentCvParamCvParam);
        cvParamRepository.save(detectorInstrumentComponentCvParamCvParam);

        LinkedList<InstrumentComponentCvParam> detectorInstrumentComponentCvParams = new LinkedList<InstrumentComponentCvParam>();
        detectorInstrumentComponentCvParams.add(detectorInstrumentComponentCvParam);
        detectorInstrumentComponent.setInstrumentComponentCvParams(detectorInstrumentComponentCvParams);
        LinkedList<DetectorInstrumentComponent> detectorInstrumentComponents = new LinkedList<DetectorInstrumentComponent>();
        detectorInstrumentComponents.add(detectorInstrumentComponent);
        instrument.setDetectors(detectorInstrumentComponents);

        Assay assay = assayRepository.findOne(ASSAY_1_ID);
        instrument.setAssay(assay);

        instrumentRepository.save(instrument);

        //instrument id set on save
        long newId = instrument.getId();

        instrument = instrumentRepository.findOne(newId);

        checkIsAnotherInstrumentInDb(instrument);


        // delete instrument from DB
        instrumentRepository.delete(instrument);

    }

    private void checkIsInstrument1InDb(Instrument instrument) {
        assertThat(instrument.getId(), is(INSTRUMENT_1_ID));

        // check model
        CvParamProvider model = instrument.getModel();
        assertNotNull(model);
        assertThat(model.getName(), is(MODEL_NAME));
        assertThat(model.getValue(), is(MODEL_VALUE));

        // check components
        SourceInstrumentComponent sourceInstrumentComponent = instrument.getSources().iterator().next();
        assertNotNull(sourceInstrumentComponent);
        assertThat(sourceInstrumentComponent.getId(), is(INSTRUMENT_1_SOURCE_INSTRUMENT_COMPONENT_ID));
        assertThat(sourceInstrumentComponent.getOrder(), is(INSTRUMENT_1_SOURCE_INSTRUMENT_COMPONENT_ORDER));
        InstrumentComponentCvParam instrumentSourceComponentCvParam = (InstrumentComponentCvParam) sourceInstrumentComponent.getParams().iterator().next();
        assertNotNull(instrumentSourceComponentCvParam);
        CvParam sourceCvParam = instrumentSourceComponentCvParam.getCvParam();
        assertNotNull(sourceCvParam);
        assertThat(sourceCvParam.getName(), is(INSTRUMENT_1_SOURCE_INSTRUMENT_COMPONENT_NAME));
        assertThat(sourceCvParam.getAccession(), is(INSTRUMENT_1_SOURCE_INSTRUMENT_COMPONENT_ACCESSION));
        assertThat(sourceCvParam.getCvLabel(), is(INSTRUMENT_1_SOURCE_INSTRUMENT_COMPONENT_LABEL));

        AnalyzerInstrumentComponent analyzerInstrumentComponent = instrument.getAnalyzers().iterator().next();
        assertNotNull(analyzerInstrumentComponent);
        assertThat(analyzerInstrumentComponent.getId(), is(INSTRUMENT_1_ANALYZER_INSTRUMENT_COMPONENT_ID));
        assertThat(analyzerInstrumentComponent.getOrder(), is(INSTRUMENT_1_ANALYZER_INSTRUMENT_COMPONENT_ORDER));
        Iterator<ParamProvider> params = analyzerInstrumentComponent.getParams().iterator();
        InstrumentComponentCvParam instrumentAnalyzer1ComponentCvParam = (InstrumentComponentCvParam) params.next();
        assertNotNull(instrumentAnalyzer1ComponentCvParam);
        CvParam analyzer1CvParam = instrumentAnalyzer1ComponentCvParam.getCvParam();
        assertNotNull(analyzer1CvParam);
        assertThat(analyzer1CvParam.getName(), is(INSTRUMENT_1_ANALYZER1_INSTRUMENT_COMPONENT_NAME));
        assertThat(analyzer1CvParam.getAccession(), is(INSTRUMENT_1_ANALYZER1_INSTRUMENT_COMPONENT_ACCESSION));
        assertThat(analyzer1CvParam.getCvLabel(), is(INSTRUMENT_1_ANALYZER1_INSTRUMENT_COMPONENT_LABEL));
        InstrumentComponentCvParam instrumentAnalyzer2ComponentCvParam = (InstrumentComponentCvParam) params.next();
        assertNotNull(instrumentAnalyzer2ComponentCvParam);
        CvParam analyzer2CvParam = instrumentAnalyzer2ComponentCvParam.getCvParam();
        assertNotNull(analyzer2CvParam);
        assertThat(analyzer2CvParam.getName(), is(INSTRUMENT_1_ANALYZER2_INSTRUMENT_COMPONENT_NAME));
        assertThat(analyzer2CvParam.getAccession(), is(INSTRUMENT_1_ANALYZER2_INSTRUMENT_COMPONENT_ACCESSION));
        assertThat(analyzer2CvParam.getCvLabel(), is(INSTRUMENT_1_ANALYZER2_INSTRUMENT_COMPONENT_LABEL));

        DetectorInstrumentComponent detectorInstrumentComponent = instrument.getDetectors().iterator().next();
        assertNotNull(detectorInstrumentComponent);
        assertThat(detectorInstrumentComponent.getId(), is(INSTRUMENT_1_DETECTOR_INSTRUMENT_COMPONENT_ID));
        assertThat(detectorInstrumentComponent.getOrder(), is(INSTRUMENT_1_DETECTOR_INSTRUMENT_COMPONENT_ORDER));
        InstrumentComponentCvParam instrumentDetectorComponentCvParam = (InstrumentComponentCvParam) detectorInstrumentComponent.getParams().iterator().next();
        assertNotNull(instrumentDetectorComponentCvParam);
        CvParam detectorCvParam = instrumentDetectorComponentCvParam.getCvParam();
        assertNotNull(detectorCvParam);
        assertThat(detectorCvParam.getName(), is(INSTRUMENT_1_DETECTOR_INSTRUMENT_COMPONENT_NAME));
        assertThat(detectorCvParam.getAccession(), is(INSTRUMENT_1_DETECTOR_INSTRUMENT_COMPONENT_ACCESSION));
        assertThat(detectorCvParam.getCvLabel(), is(INSTRUMENT_1_DETECTOR_INSTRUMENT_COMPONENT_LABEL));

    }

    private void checkIsAnotherInstrumentInDb(Instrument instrument) {

        // check model
        InstrumentModel model = instrument.getModel();
        assertNotNull(model);
        assertThat(model.getName(), is(ANOTHER_INSTRUMENT_MODEL_NAME));
        assertThat(model.getValue(), is(ANOTHER_INSTRUMENT_MODEL_VALUE));
        assertThat(model.getAccession(), is(ANOTHER_INSTRUMENT_MODEL_ACCESSION));
        assertThat(model.getCvLabel(), is(ANOTHER_INSTRUMENT_MODEL_LABEL));

        // check components
        SourceInstrumentComponent sourceInstrumentComponent = instrument.getSources().iterator().next();
        assertNotNull(sourceInstrumentComponent);
        assertThat(sourceInstrumentComponent.getOrder(), is(ANOTHER_INSTRUMENT_SOURCE_INSTRUMENT_COMPONENT_ORDER));
        InstrumentComponentCvParam instrumentSourceComponentCvParam = (InstrumentComponentCvParam) sourceInstrumentComponent.getParams().iterator().next();
        assertNotNull(instrumentSourceComponentCvParam);
        CvParam sourceCvParam = instrumentSourceComponentCvParam.getCvParam();
        assertNotNull(sourceCvParam);
        assertThat(sourceCvParam.getName(), is(ANOTHER_INSTRUMENT_SOURCE_INSTRUMENT_COMPONENT_NAME));
        assertThat(sourceCvParam.getAccession(), is(ANOTHER_INSTRUMENT_SOURCE_INSTRUMENT_COMPONENT_ACCESSION));
        assertThat(sourceCvParam.getCvLabel(), is(ANOTHER_INSTRUMENT_SOURCE_INSTRUMENT_COMPONENT_LABEL));

        AnalyzerInstrumentComponent analyzerInstrumentComponent = instrument.getAnalyzers().iterator().next();
        assertNotNull(analyzerInstrumentComponent);
        assertThat(analyzerInstrumentComponent.getOrder(), is(ANOTHER_INSTRUMENT_ANALYZER_INSTRUMENT_COMPONENT_ORDER));
        Iterator<ParamProvider> params = analyzerInstrumentComponent.getParams().iterator();
        InstrumentComponentCvParam instrumentAnalyzer1ComponentCvParam = (InstrumentComponentCvParam) params.next();
        assertNotNull(instrumentAnalyzer1ComponentCvParam);
        CvParam analyzer1CvParam = instrumentAnalyzer1ComponentCvParam.getCvParam();
        assertNotNull(analyzer1CvParam);
        assertThat(analyzer1CvParam.getName(), is(ANOTHER_INSTRUMENT_ANALYZER_INSTRUMENT_COMPONENT_NAME));
        assertThat(analyzer1CvParam.getAccession(), is(ANOTHER_INSTRUMENT_ANALYZER_INSTRUMENT_COMPONENT_ACCESSION));
        assertThat(analyzer1CvParam.getCvLabel(), is(ANOTHER_INSTRUMENT_ANALYZER_INSTRUMENT_COMPONENT_LABEL));


        DetectorInstrumentComponent detectorInstrumentComponent = instrument.getDetectors().iterator().next();
        assertNotNull(detectorInstrumentComponent);
        assertThat(detectorInstrumentComponent.getOrder(), is(ANOTHER_INSTRUMENT_DETECTOR_INSTRUMENT_COMPONENT_ORDER));
        InstrumentComponentCvParam instrumentDetectorComponentCvParam = (InstrumentComponentCvParam) detectorInstrumentComponent.getParams().iterator().next();
        assertNotNull(instrumentDetectorComponentCvParam);
        CvParam detectorCvParam = instrumentDetectorComponentCvParam.getCvParam();
        assertNotNull(detectorCvParam);
        assertThat(detectorCvParam.getName(), is(ANOTHER_INSTRUMENT_DETECTOR_INSTRUMENT_COMPONENT_NAME));
        assertThat(detectorCvParam.getAccession(), is(ANOTHER_INSTRUMENT_DETECTOR_INSTRUMENT_COMPONENT_ACCESSION));
        assertThat(detectorCvParam.getCvLabel(), is(ANOTHER_INSTRUMENT_DETECTOR_INSTRUMENT_COMPONENT_LABEL));

    }

}
