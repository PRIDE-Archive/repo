package uk.ac.ebi.pride.archive.repo.assay;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.pride.archive.dataprovider.param.CvParamProvider;
import uk.ac.ebi.pride.archive.dataprovider.param.ParamProvider;
import uk.ac.ebi.pride.archive.dataprovider.person.Title;
import uk.ac.ebi.pride.archive.repo.assay.instrument.*;
import uk.ac.ebi.pride.archive.repo.assay.software.Software;
import uk.ac.ebi.pride.archive.repo.assay.software.SoftwareCvParam;
import uk.ac.ebi.pride.archive.repo.assay.software.SoftwareUserParam;
import uk.ac.ebi.pride.archive.repo.param.CvParam;
import uk.ac.ebi.pride.archive.repo.param.CvParamRepository;

import java.util.*;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

/**
 * @author Jose A. Dianes
 * @version $Id$
 */
@ContextConfiguration(locations = {"/test-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class AssayPersistenceTest {

    private static final long ASSAY_1_ID = 44444;
    private static final long ASSAY_2_ID = 444440;
    private static final String ASSAY_1_ACCESSION = "000001";
    private static final String ASSAY_2_ACCESSION = "000002";
    private static final long PROJECT_1_ID = 11111;
    private static final int NUM_ASSAY_PROJECT_1 = 2;
    private static final String ASSAY_1_TITLE = "Experiment title";
    private static final String ASSAY_2_TITLE = "Experiment 2 title";
    private static final String ASSAY_1_SHORT_LABEL = "Short label";
    private static final String ASSAY_2_SHORT_LABEL = "Short label 2";
    private static final int ASSAY_1_PROTEIN_COUNT = 1;
    private static final int ASSAY_2_PROTEIN_COUNT = 1;
    private static final int ASSAY_1_PEPTIDE_COUNT = 1;
    private static final int ASSAY_2_PEPTIDE_COUNT = 1;
    private static final int ASSAY_1_UNIQUE_PEPTIDE_COUNT = 1;
    private static final int ASSAY_2_UNIQUE_PEPTIDE_COUNT = 1;
    private static final int ASSAY_1_IDENTIFIED_SPECTRUM_COUNT = 1;
    private static final int ASSAY_2_IDENTIFIED_SPECTRUM_COUNT = 1;
    private static final int ASSAY_1_TOTAL_SPECTRUM_COUNT = 1;
    private static final int ASSAY_2_TOTAL_SPECTRUM_COUNT = 1;
    private static final boolean ASSAY_1_HAS_MS2_ANNOTATION = true;
    private static final boolean ASSAY_2_HAS_MS2_ANNOTATION = false;
    private static final boolean ASSAY_1_HAS_CHROMATOGRAM = true;
    private static final boolean ASSAY_2_HAS_CHROMATOGRAM = false;
    private static final String ASSAY_1_EXPERIMENT_FACTOR = "Experimental factor";
    private static final String ASSAY_2_EXPERIMENT_FACTOR = "Experimental 2 factor";
    private static final int NUM_PTM_ASSAY_1 = 1;
    private static final long PTM_1_ID = 1010101010;
    private static final long CV_PARAM_1_ID = 50005;
    private static final String CV_PARAM_2_LABEL = "Project Sample Param Label";
    private static final String CV_PARAM_2_ACCESSION = "Project Sample";
    private static final String CV_PARAM_2_NAME = "Project Sample Name";
    private static final long CV_PARAM_2_ID = 66666;
    private static final String CV_PARAM_1_LABEL = "MOD";
    private static final String CV_PARAM_1_ACCESSION = "MOD:00091";
    private static final String CV_PARAM_1_NAME = "L-arginine amide";
    private static final long CV_PARAM_3_ID = 1212121212;
    private static final String CV_PARAM_3_LABEL = "Exp Type CV Param Label";
    private static final String CV_PARAM_3_ACCESSION = "Exp Type CV Param Accession";
    private static final String CV_PARAM_3_NAME = "Exp Type CV Param Name";
    private static final long CV_PARAM_4_ID = 1313131313;
    private static final String CV_PARAM_4_LABEL = "Group Project CV Param Label";
    private static final String CV_PARAM_4_ACCESSION = "Group Project CV Param Accession";
    private static final String CV_PARAM_4_NAME = "Group Project CV Param Name";
    private static final int NUM_QUANTIFICATION_METHODS_ASSAY_1 = 1;
    private static final long QUANTIFICATION_METHOD_1_ID = 1515151515;
    private static final int NUM_ASSAY_SAMPLE_PARAM_PROJECT_1 = 1;
    private static final long ASSAY_SAMPLE_PARAM_1_ID = 1717171717;
    private static final long SOFTWARE_1_ID = 1111;
    private static final int NUM_PARAMS_SOFTWARE_1 = 2;
    private static final String SOFTWARE_1_CUSTOMIZATION = "customizations";
    private static final String SOFTWARE_1_NAME = "Mascot";
    private static final String SOFTWARE_1_VERSION = "1.2.3";
    private static final int NUM_SOFTWARES_ASSAY_1 = 1;
    private static final int NUM_CONTACTS_ASSAY_1 = 1;
    private static final String CONTACT_1_AFFILIATION = "EBI";
    private static final String CONTACT_1_EMAIL = "john.smith@dummy.ebi.com";
    private static final String CONTACT_1_FIRST_NAME = "John";
    private static final String CONTACT_1_LAST_NAME = "Smith";
    private static final long CONTACT_1_ID = 33333;
    private static final int NUM_GROUP_PARAMS_ASSAY_1 = 1;
    private static final long GROUP_PARAM_1_ID = 1818181818;
    private static final int NUM_INSTRUMENTS_ASSAY_1 = 1;
    private static final long INSTRUMENT_1_ID = 1111;
    private static final String MODEL_NAME = "FT_ICR";
    private static final String MODEL_VALUE = "icr";
    private static final long ASSAY_1_SOURCE_INSTRUMENT_COMPONENT_ID = 1111;
    private static final int ASSAY_1_SOURCE_INSTRUMENT_COMPONENT_ORDER = 1;
    private static final long ASSAY_1_ANALYZER_INSTRUMENT_COMPONENT_ID = 1112;
    private static final int ASSAY_1_ANALYZER_INSTRUMENT_COMPONENT_ORDER = 2;
    private static final long ASSAY_1_DETECTOR_INSTRUMENT_COMPONENT_ID = 1113;
    private static final int ASSAY_1_DETECTOR_INSTRUMENT_COMPONENT_ORDER = 3;
    private static final String ASSAY_1_SOURCE_INSTRUMENT_COMPONENT_NAME = "ESI";
    private static final String ASSAY_1_SOURCE_INSTRUMENT_COMPONENT_ACCESSION = "source";
    private static final String ASSAY_1_SOURCE_INSTRUMENT_COMPONENT_LABEL = "MS";
    private static final String ASSAY_1_ANALYZER1_INSTRUMENT_COMPONENT_NAME = "TOF";
    private static final String ASSAY_1_ANALYZER1_INSTRUMENT_COMPONENT_ACCESSION = "analyzer1";
    private static final String ASSAY_1_ANALYZER1_INSTRUMENT_COMPONENT_LABEL = "MS";
    private static final String ASSAY_1_ANALYZER2_INSTRUMENT_COMPONENT_NAME = "LTQ";
    private static final String ASSAY_1_ANALYZER2_INSTRUMENT_COMPONENT_ACCESSION = "analyzer2";
    private static final String ASSAY_1_ANALYZER2_INSTRUMENT_COMPONENT_LABEL = "MS";
    private static final String ASSAY_1_DETECTOR_INSTRUMENT_COMPONENT_NAME = "plate";
    private static final String ASSAY_1_DETECTOR_INSTRUMENT_COMPONENT_ACCESSION = "detector";
    private static final String ASSAY_1_DETECTOR_INSTRUMENT_COMPONENT_LABEL = "MS";

    private static final String ANOTHER_ASSAY_ACCESSION = "Another Assay Accession";
    private static final String ANOTHER_ASSAY_TITLE = "Another Assay Title";
    private static final long ANOTHER_ASSAY_PROJECT_ID = 11111;
    private static final String ANOTHER_ASSAY_SHORT_LABEL = "Another Assay Short label";
    private static final int ANOTHER_ASSAY_PROTEIN_COUNT = 1;
    private static final int ANOTHER_ASSAY_PEPTIDE_COUNT = 1;
    private static final int ANOTHER_ASSAY_UNIQUE_PEPTIDE_COUNT = 1;
    private static final int ANOTHER_ASSAY_IDENTIFIED_SPECTRUM_COUNT = 1;
    private static final int ANOTHER_ASSAY_TOTAL_SPECTRUM_COUNT = 1;
    private static final boolean ANOTHER_ASSAY_HAS_MS2_ANNOTATION = true;
    private static final boolean ANOTHER_ASSAY_HAS_CHROMATOGRAM = true;
    private static final String ANOTHER_ASSAY_EXPERIMENTAL_FACTOR = "Another Assay Experimental factor";
    private static final int NUM_PTM_OTHER_ASSAY = 1;
    private static final int NUM_GROUP_PARAMS_OTHER_ASSAY = 1;
    private static final int NUM_CONTACTS_OTHER_ASSAY = 1;
    private static final int NUM_QUANTIFICATION_METHODS_OTHER_ASSAY = 1;
    private static final int NUM_OTHER_ASSAY_SAMPLE_PARAM = 1;

    private static final int NUM_PARAMS_ANOTHER_SOFTWARE = 2;
    private static final int ANOTHER_SOFTWARE_ORDER = 0;
    private static final String ANOTHER_SOFTWARE_CUSTOMIZATION = "another customizations";
    private static final String ANOTHER_SOFTWARE_NAME = "Sequest";
    private static final String ANOTHER_SOFTWARE_VERSION = "1.0";
    private static final String ANOTHER_SOFTWARE_CV_PARAM_VALUE = "another software value";
    private static final String ANOTHER_CV_PARAM_LABEL = "Another Software CV Param Label";
    private static final String ANOTHER_CV_PARAM_ACCESSION = "Another Software CV Param Accession";
    private static final String ANOTHER_CV_PARAM_NAME = "Another Software CV Param Name";
    private static final String ANOTHER_SOFTWARE_USER_PARAM_NAME = "another software param name";
    private static final String ANOTHER_SOFTWARE_USER_PARAM_VALUE = "another software value";
    private static final int NUM_SOFTWARES_OTHER_ASSAY = 1;
    private static final int NUM_INSTRUMENTS_ANOTHER_ASSAY = 1;

    private static long newInstrumentId;

    @Autowired
    private AssayRepository assayRepository;

    @Autowired
    private CvParamRepository cvParamRepository;

    @Autowired
    private InstrumentRepository instrumentRepository;

    @Test
    @Transactional
    public void testGetById() throws Exception {
        Optional<Assay> assay = assayRepository.findById(ASSAY_1_ID);

        checkIsAssay1InDb(assay.get());

    }

    @Test
    @Transactional
    public void testGetByAccession() throws Exception {
        Assay assay = assayRepository.findByAccession(ASSAY_1_ACCESSION);

        checkIsAssay1InDb(assay);

    }

    @Test
    @Transactional
    public void testGetByProjectId() throws Exception {
        List<Assay> assays = assayRepository.findAllByProjectId(PROJECT_1_ID);

        assertNotNull(assays);
        assertThat(assays.size(), is(NUM_ASSAY_PROJECT_1));

        for (Assay assay: assays) {
            if (assay.getId() == ASSAY_1_ID)
                checkIsAssay1InDb(assay);
            else
                checkIsAssay2InDb(assay);
        }

    }

    @Test
    @Transactional
    public void testGetByProjectIdPage() throws Exception {
        Page<Assay> assays = assayRepository.findAllByProjectId(PROJECT_1_ID, new PageRequest(0,1));

        assertNotNull(assays);
        assertThat((int) assays.getTotalElements(), is(NUM_ASSAY_PROJECT_1));

        for (Assay assay: assays) {
            if (assay.getId() == ASSAY_1_ID)
                checkIsAssay1InDb(assay);
            else
                checkIsAssay2InDb(assay);
        }

    }

    @Test
    @Transactional
    public void testSaveAndGet() throws Exception {
        Assay assay = new Assay();
        assay.setAccession(ANOTHER_ASSAY_ACCESSION);
        assay.setTitle(ANOTHER_ASSAY_TITLE);
        assay.setProjectId(ANOTHER_ASSAY_PROJECT_ID);
        assay.setShortLabel(ANOTHER_ASSAY_SHORT_LABEL);
        assay.setProteinCount(ANOTHER_ASSAY_PROTEIN_COUNT);
        assay.setPeptideCount(ANOTHER_ASSAY_PEPTIDE_COUNT);
        assay.setUniquePeptideCount(ANOTHER_ASSAY_UNIQUE_PEPTIDE_COUNT);
        assay.setIdentifiedSpectrumCount(ANOTHER_ASSAY_IDENTIFIED_SPECTRUM_COUNT);
        assay.setTotalSpectrumCount(ANOTHER_ASSAY_TOTAL_SPECTRUM_COUNT);
        assay.setMs2Annotation(ANOTHER_ASSAY_HAS_MS2_ANNOTATION);
        assay.setChromatogram(ANOTHER_ASSAY_HAS_CHROMATOGRAM);
        assay.setExperimentalFactor(ANOTHER_ASSAY_EXPERIMENTAL_FACTOR);

        // add PTM
        AssayPTM assayPtm = new AssayPTM();
        assayPtm.setAssay(assay);
        assayPtm.setCvParam(cvParamRepository.findById(CV_PARAM_1_ID).get());
        LinkedList<AssayPTM> assayPtms = new LinkedList<AssayPTM>();
        assayPtms.add(assayPtm);
        assay.setPtms(assayPtms);

        // add group params
        AssayGroupCvParam assayGroupCvParam = new AssayGroupCvParam();
        assayGroupCvParam.setAssay(assay);
        assayGroupCvParam.setCvParam(cvParamRepository.findById(CV_PARAM_3_ID).get());
        LinkedList<AssayGroupCvParam> assayGroupCvParams = new LinkedList<AssayGroupCvParam>();
        assayGroupCvParams.add(assayGroupCvParam);
        assay.setAssayGroupCvParams(assayGroupCvParams);

        // Add contacts
        Contact contact = new Contact();
        contact.setAffiliation(CONTACT_1_AFFILIATION);
        contact.setAssay(assay);
        contact.setEmail(CONTACT_1_EMAIL);
        contact.setFirstName(CONTACT_1_FIRST_NAME);
        contact.setLastName(CONTACT_1_LAST_NAME);
        contact.setTitle(Title.Mr);
        LinkedList<Contact> contacts = new LinkedList<Contact>();
        contacts.add(contact);
        assay.setContacts(contacts);

        // add quantification methods
        AssayQuantificationMethodCvParam assayQuantificationMethod = new AssayQuantificationMethodCvParam();
        assayQuantificationMethod.setAssay(assay);
        assayQuantificationMethod.setCvParam(cvParamRepository.findById(CV_PARAM_3_ID).get());
        LinkedList<AssayQuantificationMethodCvParam> assayQuantificationMethods = new LinkedList<AssayQuantificationMethodCvParam>();
        assayQuantificationMethods.add(assayQuantificationMethod);
        assay.setQuantificationMethods(assayQuantificationMethods);

        // add quantification methods
        AssaySampleCvParam assaySample = new AssaySampleCvParam();
        assaySample.setAssay(assay);
        assaySample.setCvParam(cvParamRepository.findById(CV_PARAM_3_ID).get());
        LinkedList<AssaySampleCvParam> assaySamples = new LinkedList<AssaySampleCvParam>();
        assaySamples.add(assaySample);
        assay.setSamples(assaySamples);

        // set softwares
        setAnotherSoftwaresToAssay(assay);

        // set instruments
        Instrument newInstrument = new Instrument();
        newInstrument.setAssay(assay);

        CvParam instrParam = cvParamRepository.findById(13l).get();
        newInstrument.setCvParam(instrParam);
        newInstrument.setValue("icr");
        //source
        SourceInstrumentComponent source = new SourceInstrumentComponent();
        source.setInstrument(newInstrument);
        source.setOrder(1);
        Collection<InstrumentComponentCvParam> sourceParams = new ArrayList<InstrumentComponentCvParam>();
        InstrumentComponentCvParam cv1 = new InstrumentComponentCvParam();
        cv1.setCvParam(cvParamRepository.findById(14l).get());
        cv1.setInstrumentComponent(source);
        sourceParams.add(cv1);
        source.setInstrumentComponentCvParams(sourceParams);
        newInstrument.setSources(Collections.singleton(source));

        //analyser
        AnalyzerInstrumentComponent analyzer1 = new AnalyzerInstrumentComponent();
        analyzer1.setInstrument(newInstrument);
        analyzer1.setOrder(2);
        Collection<InstrumentComponentCvParam> analyzerParams = new ArrayList<InstrumentComponentCvParam>();
        InstrumentComponentCvParam cv2 = new InstrumentComponentCvParam();
        cv2.setCvParam(cvParamRepository.findById(15l).get());
        cv2.setInstrumentComponent(analyzer1);
        InstrumentComponentCvParam cv3 = new InstrumentComponentCvParam();
        cv3.setCvParam(cvParamRepository.findById(151l).get());
        cv3.setInstrumentComponent(analyzer1);
        analyzerParams.add(cv2);
        analyzerParams.add(cv3);
        analyzer1.setInstrumentComponentCvParams(analyzerParams);
        newInstrument.setAnalyzers(Collections.singleton(analyzer1));

        //detector
        DetectorInstrumentComponent detector = new DetectorInstrumentComponent();
        detector.setInstrument(newInstrument);
        detector.setOrder(3);
        Collection<InstrumentComponentCvParam> detectorParams = new ArrayList<InstrumentComponentCvParam>();
        InstrumentComponentCvParam cv4 = new InstrumentComponentCvParam();
        cv4.setCvParam(cvParamRepository.findById(16l).get());
        cv4.setInstrumentComponent(detector);
        detectorParams.add(cv4);
        detector.setInstrumentComponentCvParams(detectorParams);
        newInstrument.setDetectors(Collections.singleton(detector));

        LinkedList<Instrument> instruments = new LinkedList<Instrument>();
        instruments.add(newInstrument);
        assay.setInstruments(instruments);
        assayRepository.save(assay);

        //id set after save
        long newId = assay.getId();

        //update instrumentID
        newInstrumentId = newInstrument.getId();

        Assay other = assayRepository.findById(newId).get();
        checkIsAnotherAssayInDb(other);

        // delete the assay
        assayRepository.delete(other);

    }

    private void setAnotherSoftwaresToAssay(Assay assay) {
        Software software = new Software();
        software.setName(ANOTHER_SOFTWARE_NAME);
        software.setCustomization(ANOTHER_SOFTWARE_CUSTOMIZATION);
        software.setVersion(ANOTHER_SOFTWARE_VERSION);
        software.setOrder(ANOTHER_SOFTWARE_ORDER);
        software.setAssay(assay);

        LinkedList<SoftwareCvParam> softwareCvParams = new LinkedList<SoftwareCvParam>();

        CvParam cvParam = new CvParam();
        cvParam.setAccession(ANOTHER_CV_PARAM_ACCESSION);
        cvParam.setCvLabel(ANOTHER_CV_PARAM_LABEL);
        cvParam.setName(ANOTHER_CV_PARAM_NAME);
        cvParamRepository.save(cvParam);

        SoftwareCvParam softwareCvParam = new SoftwareCvParam();
        softwareCvParam.setCvParam(cvParam);
        softwareCvParam.setSoftware(software);
        softwareCvParam.setValue(ANOTHER_SOFTWARE_CV_PARAM_VALUE);
        softwareCvParams.add(softwareCvParam);
        software.setSoftwareCvParams(softwareCvParams);

        LinkedList<SoftwareUserParam> softwareUserParams = new LinkedList<SoftwareUserParam>();
        SoftwareUserParam softwareUserParam = new SoftwareUserParam();
        softwareUserParam.setName(ANOTHER_SOFTWARE_USER_PARAM_NAME);
        softwareUserParam.setSoftware(software);
        softwareUserParam.setValue(ANOTHER_SOFTWARE_USER_PARAM_VALUE);
        softwareUserParams.add(softwareUserParam);
        software.setSoftwareUserParams(softwareUserParams);
        LinkedList<Software> softwares = new LinkedList<Software>();
        softwares.add(software);
        assay.setSoftwares(softwares);

    }

    private void checkIsAnotherAssayInDb(Assay assay) {

        assertNotNull(assay);
        assertThat(assay.getAccession(), is(ANOTHER_ASSAY_ACCESSION));
        assertThat(assay.getTitle(), is(ANOTHER_ASSAY_TITLE));
        assertThat(assay.getShortLabel(), is(ANOTHER_ASSAY_SHORT_LABEL));
        assertThat(assay.getProteinCount(), is(ANOTHER_ASSAY_PROTEIN_COUNT));
        assertThat(assay.getPeptideCount(), is(ANOTHER_ASSAY_PEPTIDE_COUNT));
        assertThat(assay.getUniquePeptideCount(), is(ANOTHER_ASSAY_UNIQUE_PEPTIDE_COUNT));
        assertThat(assay.getIdentifiedSpectrumCount(), is(ANOTHER_ASSAY_IDENTIFIED_SPECTRUM_COUNT));
        assertThat(assay.getTotalSpectrumCount(), is(ANOTHER_ASSAY_TOTAL_SPECTRUM_COUNT));
        assertThat(assay.hasMs2Annotation(), is(ANOTHER_ASSAY_HAS_MS2_ANNOTATION));
        assertThat(assay.hasChromatogram(), is(ANOTHER_ASSAY_HAS_CHROMATOGRAM));
        assertThat(assay.getExperimentalFactor(), is(ANOTHER_ASSAY_EXPERIMENTAL_FACTOR));

        checkOtherPTMs(assay);
        checkOtherParams(assay);
        checkOtherContacts(assay);
        checkOtherQuantificationMethods(assay);
        checkOtherSamples(assay);
        checkOtherSoftwares(assay);
        checkOtherInstruments(assay);

    }

    private void checkOtherInstruments(Assay assay) {
        Collection<Instrument> instruments = assay.getInstruments();

        assertNotNull(instruments);
        assertThat(instruments.size(), is(NUM_INSTRUMENTS_ANOTHER_ASSAY));

        Instrument instrument = instruments.iterator().next();
        checkIsInstrument1InDb(instrument);
    }

    private void checkOtherSoftwares(Assay assay) {
        Collection<Software> softwares = assay.getSoftwares();
        assertNotNull(softwares);
        assertThat(softwares.size(), is(NUM_SOFTWARES_OTHER_ASSAY));

        checkIsAnotherSoftwareInDb(softwares.iterator().next());

    }

    private void checkOtherSamples(Assay assay) {
        Collection<AssaySampleCvParam> assaySamples = assay.getSamples();

        Assert.assertNotNull(assaySamples);
        assertEquals(assaySamples.size(), NUM_OTHER_ASSAY_SAMPLE_PARAM);
        AssaySampleCvParam assaySample = assaySamples.iterator().next();
        assertThat(assaySample.getAssay(), is(assay));

        CvParam cvParam = assaySample.getCvParam();
        Assert.assertNotNull(cvParam);
        Assert.assertThat(cvParam.getId(), is(CV_PARAM_3_ID));
        assertEquals(CV_PARAM_3_LABEL, cvParam.getCvLabel());
        assertEquals(CV_PARAM_3_ACCESSION, cvParam.getAccession());
        assertEquals(CV_PARAM_3_NAME, cvParam.getName());
    }

    private void checkOtherQuantificationMethods(Assay assay) {

        Collection<AssayQuantificationMethodCvParam> quantificationMethods = assay.getQuantificationMethods();

        Assert.assertNotNull(quantificationMethods);
        assertEquals(quantificationMethods.size(), NUM_QUANTIFICATION_METHODS_OTHER_ASSAY);
        AssayQuantificationMethodCvParam quantificationMethod = quantificationMethods.iterator().next();
        assertThat(quantificationMethod.getAssay(), is(assay));

        CvParam cvParam = quantificationMethod.getCvParam();
        Assert.assertNotNull(cvParam);
        Assert.assertThat(cvParam.getId(), is(CV_PARAM_3_ID));
        assertEquals(CV_PARAM_3_LABEL, cvParam.getCvLabel());
        assertEquals(CV_PARAM_3_ACCESSION, cvParam.getAccession());
        assertEquals(CV_PARAM_3_NAME, cvParam.getName());
    }

    private void checkOtherContacts(Assay assay) {
        Collection<Contact> contacts = assay.getContacts();

        assertNotNull(contacts);
        assertEquals(contacts.size(), NUM_CONTACTS_OTHER_ASSAY);
        Contact contact = contacts.iterator().next();

        assertThat(contact.getAssay(), is(assay));
        assertThat(contact.getAffiliation(), is(CONTACT_1_AFFILIATION));
        assertThat(contact.getEmail(), is(CONTACT_1_EMAIL));
        assertThat(contact.getFirstName(), is(CONTACT_1_FIRST_NAME));
        assertThat(contact.getLastName(), is(CONTACT_1_LAST_NAME));
        assertThat(contact.getTitle(), is(Title.Mr));
    }

    private void checkOtherParams(Assay assay) {
        Collection<ParamProvider> groupParams = assay.getParams();

        Assert.assertNotNull(groupParams);
        assertEquals(groupParams.size(), NUM_GROUP_PARAMS_OTHER_ASSAY);
        AssayCvParam assayCvParam = (AssayCvParam) groupParams.iterator().next();
        assertThat(assayCvParam.getAssay(), is(assay));

        CvParam cvParam = assayCvParam.getCvParam();
        Assert.assertNotNull(cvParam);
        Assert.assertThat(cvParam.getId(), is(CV_PARAM_3_ID));
        assertEquals(CV_PARAM_3_LABEL, cvParam.getCvLabel());
        assertEquals(CV_PARAM_3_ACCESSION, cvParam.getAccession());
        assertEquals(CV_PARAM_3_NAME, cvParam.getName());

    }

    private void checkOtherPTMs(Assay assay) {
        Collection<AssayPTM> ptms = assay.getPtms();

        Assert.assertNotNull(ptms);
        assertEquals(ptms.size(), NUM_PTM_OTHER_ASSAY);
        AssayPTM ptm = ptms.iterator().next();
        assertThat(ptm.getAssay(), is(assay));
        assertThat(ptm.getAccession(), is(CV_PARAM_1_ACCESSION));

        CvParam cvParam = ptm.getCvParam();
        Assert.assertNotNull(cvParam);
        Assert.assertThat(cvParam.getId(), is(CV_PARAM_1_ID));
        assertEquals(CV_PARAM_1_LABEL, cvParam.getCvLabel());
        assertEquals(CV_PARAM_1_ACCESSION, cvParam.getAccession());
        assertEquals(CV_PARAM_1_NAME, cvParam.getName());
    }

    private void checkIsAssay1InDb(Assay assay) {
        assertNotNull(assay);
        assertThat(assay.getId(), is(ASSAY_1_ID));
        assertThat(assay.getAccession(), is(ASSAY_1_ACCESSION));
        assertThat(assay.getTitle(), is(ASSAY_1_TITLE));
        assertThat(assay.getShortLabel(), is(ASSAY_1_SHORT_LABEL));
        assertThat(assay.getProteinCount(), is(ASSAY_1_PROTEIN_COUNT));
        assertThat(assay.getPeptideCount(), is(ASSAY_1_PEPTIDE_COUNT));
        assertThat(assay.getUniquePeptideCount(), is(ASSAY_1_UNIQUE_PEPTIDE_COUNT));
        assertThat(assay.getIdentifiedSpectrumCount(), is(ASSAY_1_IDENTIFIED_SPECTRUM_COUNT));
        assertThat(assay.getTotalSpectrumCount(), is(ASSAY_1_TOTAL_SPECTRUM_COUNT));
        assertThat(assay.hasMs2Annotation(), is(ASSAY_1_HAS_MS2_ANNOTATION));
        assertThat(assay.hasChromatogram(), is(ASSAY_1_HAS_CHROMATOGRAM));
        assertThat(assay.getExperimentalFactor(), is(ASSAY_1_EXPERIMENT_FACTOR));

        checkPTMs(assay);

        checkParams(assay);
        checkContacts(assay);
        checkQuantificationMethods(assay);
        checkSoftwares(assay);
        checkInstruments(assay);
        checkSamples(assay);
    }

    private void checkIsAssay2InDb(Assay assay) {
        assertNotNull(assay);
        assertThat(assay.getId(), is(ASSAY_2_ID));
        assertThat(assay.getAccession(), is(ASSAY_2_ACCESSION));
        assertThat(assay.getTitle(), is(ASSAY_2_TITLE));
        assertThat(assay.getShortLabel(), is(ASSAY_2_SHORT_LABEL));
        assertThat(assay.getProteinCount(), is(ASSAY_2_PROTEIN_COUNT));
        assertThat(assay.getPeptideCount(), is(ASSAY_2_PEPTIDE_COUNT));
        assertThat(assay.getUniquePeptideCount(), is(ASSAY_2_UNIQUE_PEPTIDE_COUNT));
        assertThat(assay.getIdentifiedSpectrumCount(), is(ASSAY_2_IDENTIFIED_SPECTRUM_COUNT));
        assertThat(assay.getTotalSpectrumCount(), is(ASSAY_2_TOTAL_SPECTRUM_COUNT));
        assertThat(assay.hasMs2Annotation(), is(ASSAY_2_HAS_MS2_ANNOTATION));
        assertThat(assay.hasChromatogram(), is(ASSAY_2_HAS_CHROMATOGRAM));
        assertThat(assay.getExperimentalFactor(), is(ASSAY_2_EXPERIMENT_FACTOR));
    }

    private void checkInstruments(Assay assay) {
        Collection<Instrument> instruments = assay.getInstruments();

        assertNotNull(instruments);
        assertThat(instruments.size(), is(NUM_INSTRUMENTS_ASSAY_1));

        Instrument instrument = instruments.iterator().next();
        checkIsInstrument1InDb(instrument);

    }

    public static void checkIsInstrument1InDb(Instrument instrument) {

        // check model
        CvParamProvider model = instrument.getModel();
        assertNotNull(model);
        assertThat(model.getName(), is(MODEL_NAME));
        assertThat(model.getValue(), is(MODEL_VALUE));

        // check components
        SourceInstrumentComponent sourceInstrumentComponent = instrument.getSources().iterator().next();
        assertNotNull(sourceInstrumentComponent);
        assertThat(sourceInstrumentComponent.getOrder(), is(ASSAY_1_SOURCE_INSTRUMENT_COMPONENT_ORDER));
        InstrumentComponentCvParam instrumentSourceComponentCvParam = (InstrumentComponentCvParam) sourceInstrumentComponent.getParams().iterator().next();
        assertNotNull(instrumentSourceComponentCvParam);
        CvParam sourceCvParam = instrumentSourceComponentCvParam.getCvParam();
        assertNotNull(sourceCvParam);
        assertThat(sourceCvParam.getName(), is(ASSAY_1_SOURCE_INSTRUMENT_COMPONENT_NAME));
        assertThat(sourceCvParam.getAccession(), is(ASSAY_1_SOURCE_INSTRUMENT_COMPONENT_ACCESSION));
        assertThat(sourceCvParam.getCvLabel(), is(ASSAY_1_SOURCE_INSTRUMENT_COMPONENT_LABEL));

        AnalyzerInstrumentComponent analyzerInstrumentComponent = instrument.getAnalyzers().iterator().next();
        assertNotNull(analyzerInstrumentComponent);
        assertThat(analyzerInstrumentComponent.getOrder(), is(ASSAY_1_ANALYZER_INSTRUMENT_COMPONENT_ORDER));
        Iterator<ParamProvider> params = analyzerInstrumentComponent.getParams().iterator();
        InstrumentComponentCvParam instrumentAnalyzer1ComponentCvParam = (InstrumentComponentCvParam) params.next();
        assertNotNull(instrumentAnalyzer1ComponentCvParam);
        CvParam analyzer1CvParam = instrumentAnalyzer1ComponentCvParam.getCvParam();
        assertNotNull(analyzer1CvParam);
        assertThat(analyzer1CvParam.getName(), is(ASSAY_1_ANALYZER1_INSTRUMENT_COMPONENT_NAME));
        assertThat(analyzer1CvParam.getAccession(), is(ASSAY_1_ANALYZER1_INSTRUMENT_COMPONENT_ACCESSION));
        assertThat(analyzer1CvParam.getCvLabel(), is(ASSAY_1_ANALYZER1_INSTRUMENT_COMPONENT_LABEL));
        InstrumentComponentCvParam instrumentAnalyzer2ComponentCvParam = (InstrumentComponentCvParam) params.next();
        assertNotNull(instrumentAnalyzer2ComponentCvParam);
        CvParam analyzer2CvParam = instrumentAnalyzer2ComponentCvParam.getCvParam();
        assertNotNull(analyzer2CvParam);
        assertThat(analyzer2CvParam.getName(), is(ASSAY_1_ANALYZER2_INSTRUMENT_COMPONENT_NAME));
        assertThat(analyzer2CvParam.getAccession(), is(ASSAY_1_ANALYZER2_INSTRUMENT_COMPONENT_ACCESSION));
        assertThat(analyzer2CvParam.getCvLabel(), is(ASSAY_1_ANALYZER2_INSTRUMENT_COMPONENT_LABEL));

        DetectorInstrumentComponent detectorInstrumentComponent = instrument.getDetectors().iterator().next();
        assertNotNull(detectorInstrumentComponent);
        assertThat(detectorInstrumentComponent.getOrder(), is(ASSAY_1_DETECTOR_INSTRUMENT_COMPONENT_ORDER));
        InstrumentComponentCvParam instrumentDetectorComponentCvParam = (InstrumentComponentCvParam) detectorInstrumentComponent.getParams().iterator().next();
        assertNotNull(instrumentDetectorComponentCvParam);
        CvParam detectorCvParam = instrumentDetectorComponentCvParam.getCvParam();
        assertNotNull(detectorCvParam);
        assertThat(detectorCvParam.getName(), is(ASSAY_1_DETECTOR_INSTRUMENT_COMPONENT_NAME));
        assertThat(detectorCvParam.getAccession(), is(ASSAY_1_DETECTOR_INSTRUMENT_COMPONENT_ACCESSION));
        assertThat(detectorCvParam.getCvLabel(), is(ASSAY_1_DETECTOR_INSTRUMENT_COMPONENT_LABEL));

    }

    private void checkSoftwares(Assay assay) {
        Collection<Software> softwares = assay.getSoftwares();
        assertNotNull(softwares);
        assertThat(softwares.size(), is(NUM_SOFTWARES_ASSAY_1));

        checkIsSoftware1InDb(softwares.iterator().next());
    }

    private void checkQuantificationMethods(Assay assay) {

        Collection<AssayQuantificationMethodCvParam> quantificationMethods = assay.getQuantificationMethods();

        Assert.assertNotNull(quantificationMethods);
        assertEquals(quantificationMethods.size(), NUM_QUANTIFICATION_METHODS_ASSAY_1);
        AssayQuantificationMethodCvParam quantificationMethod = quantificationMethods.iterator().next();
        assertThat(quantificationMethod.getId(), is(QUANTIFICATION_METHOD_1_ID));

        CvParam cvParam = quantificationMethod.getCvParam();
        Assert.assertNotNull(cvParam);
        Assert.assertThat(cvParam.getId(), is(CV_PARAM_3_ID));
        assertEquals(CV_PARAM_3_LABEL, cvParam.getCvLabel());
        assertEquals(CV_PARAM_3_ACCESSION, cvParam.getAccession());
        assertEquals(CV_PARAM_3_NAME, cvParam.getName());
    }

    private void checkContacts(Assay assay) {

        Collection<Contact> contacts = assay.getContacts();

        assertNotNull(contacts);
        assertEquals(contacts.size(), NUM_CONTACTS_ASSAY_1);
        Contact contact = contacts.iterator().next();

        assertThat(contact.getId(), is(CONTACT_1_ID));
        assertThat(contact.getAffiliation(), is(CONTACT_1_AFFILIATION));
        assertThat(contact.getEmail(), is(CONTACT_1_EMAIL));
        assertThat(contact.getFirstName(), is(CONTACT_1_FIRST_NAME));
        assertThat(contact.getLastName(), is(CONTACT_1_LAST_NAME));
        assertThat(contact.getTitle(), is(Title.Mr));

    }

    private void checkParams(Assay assay) {
        Collection<ParamProvider> groupParams = assay.getParams();

        Assert.assertNotNull(groupParams);
        assertEquals(groupParams.size(), NUM_GROUP_PARAMS_ASSAY_1);
        AssayCvParam assayCvParam = (AssayCvParam) groupParams.iterator().next();
        assertThat(assayCvParam.getId(), is(GROUP_PARAM_1_ID));

        CvParam cvParam = assayCvParam.getCvParam();
        Assert.assertNotNull(cvParam);
        Assert.assertThat(cvParam.getId(), is(CV_PARAM_3_ID));
        assertEquals(CV_PARAM_3_LABEL, cvParam.getCvLabel());
        assertEquals(CV_PARAM_3_ACCESSION, cvParam.getAccession());
        assertEquals(CV_PARAM_3_NAME, cvParam.getName());

    }

    private void checkSamples(Assay assay) {
        Collection<AssaySampleCvParam> assaySamples = assay.getSamples();

        Assert.assertNotNull(assaySamples);
        assertEquals(assaySamples.size(), NUM_ASSAY_SAMPLE_PARAM_PROJECT_1);
        AssaySampleCvParam assaySample = assaySamples.iterator().next();
        assertThat(assaySample.getId(), is(ASSAY_SAMPLE_PARAM_1_ID));

        CvParam cvParam = assaySample.getCvParam();
        Assert.assertNotNull(cvParam);
        Assert.assertThat(cvParam.getId(), is(CV_PARAM_3_ID));
        assertEquals(CV_PARAM_3_LABEL, cvParam.getCvLabel());
        assertEquals(CV_PARAM_3_ACCESSION, cvParam.getAccession());
        assertEquals(CV_PARAM_3_NAME, cvParam.getName());
    }

    private void checkPTMs(Assay assay) {
        Collection<AssayPTM> ptms = assay.getPtms();

        Assert.assertNotNull(ptms);
        assertEquals(ptms.size(), NUM_PTM_ASSAY_1);
        AssayPTM ptm = ptms.iterator().next();
        assertThat(ptm.getId(), is(PTM_1_ID));

        CvParam cvParam = ptm.getCvParam();
        Assert.assertNotNull(cvParam);
        Assert.assertThat(cvParam.getId(), is(CV_PARAM_1_ID));
        assertEquals(CV_PARAM_1_LABEL, cvParam.getCvLabel());
        assertEquals(CV_PARAM_1_ACCESSION, cvParam.getAccession());
        assertEquals(CV_PARAM_1_NAME, cvParam.getName());
    }

    private void checkIsSoftware1InDb(Software software) {
        assertThat(software.getId(), is(SOFTWARE_1_ID));
        assertThat(software.getCustomization(), is(SOFTWARE_1_CUSTOMIZATION));
        assertThat(software.getName(), is(SOFTWARE_1_NAME));
        assertThat(software.getVersion(), is(SOFTWARE_1_VERSION));

        checkParams(software);
    }

    private void checkParams(Software software) {
        Collection<ParamProvider> params = software.getParams();

        assertNotNull(params);
        assertThat(params.size(), is(NUM_PARAMS_SOFTWARE_1));
    }

    private void checkIsAnotherSoftwareInDb(Software software) {
        assertThat(software.getCustomization(), is(ANOTHER_SOFTWARE_CUSTOMIZATION));
        assertThat(software.getName(), is(ANOTHER_SOFTWARE_NAME));
        assertThat(software.getVersion(), is(ANOTHER_SOFTWARE_VERSION));

        checkAnotherSoftwareParams(software);
    }

    private void checkAnotherSoftwareParams(Software software) {
        Collection<ParamProvider> params = software.getParams();

        assertNotNull(params);
        assertThat(params.size(), is(NUM_PARAMS_ANOTHER_SOFTWARE));

        Iterator<ParamProvider> paramsIt = params.iterator();

        // check cv param
        SoftwareCvParam softwareCvParam = (SoftwareCvParam) paramsIt.next();
        assertNotNull(softwareCvParam);
        assertThat(softwareCvParam.getValue(), is(ANOTHER_SOFTWARE_CV_PARAM_VALUE));
        CvParam cvParam = softwareCvParam.getCvParam();
        assertNotNull(cvParam);
        assertThat(cvParam.getCvLabel(), is(ANOTHER_CV_PARAM_LABEL));
        assertThat(cvParam.getAccession(), is(ANOTHER_CV_PARAM_ACCESSION));
        assertThat(cvParam.getName(), is(ANOTHER_CV_PARAM_NAME));

        // check user param
        SoftwareUserParam softwareUserParam = (SoftwareUserParam) paramsIt.next();
        assertNotNull(softwareUserParam);
        assertThat(softwareUserParam.getName(), is(ANOTHER_SOFTWARE_USER_PARAM_NAME));
        assertThat(softwareUserParam.getValue(), is(ANOTHER_SOFTWARE_USER_PARAM_VALUE));
    }
}
