package uk.ac.ebi.pride.archive.repo.project;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.pride.archive.dataprovider.param.ParamProvider;
import uk.ac.ebi.pride.archive.dataprovider.person.Title;
import uk.ac.ebi.pride.archive.dataprovider.project.SubmissionType;
import uk.ac.ebi.pride.archive.repo.config.ArchiveOracleConfig;
import uk.ac.ebi.pride.archive.repo.repos.param.CvParam;
import uk.ac.ebi.pride.archive.repo.repos.param.CvParamRepository;
import uk.ac.ebi.pride.archive.repo.repos.project.*;
import uk.ac.ebi.pride.archive.repo.repos.user.User;
import uk.ac.ebi.pride.archive.repo.repos.user.UserRepository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Jose A. Dianes
 * @version $Id$
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ArchiveOracleConfig.class})
public class ProjectPersistenceTest {

  public static final long NUM_PROJECTS = 1;
  public static final long USER_1_ID = 11111;
  public static final long PROJECT_1_ID = 11111;
  public static final String PROJECT_1_ACCESSION = "PXD00001";
  public static final String PROJECT_2_ACCESSION = "PXD00002";
  public static final String PROJECT_1_DOI = "doi.doi";
  public static final String PROJECT_1_TITLE = "Project title";
  public static final String PROJECT_1_DESCRIPTION = "Project description";
  public static final String PROJECT_1_DATA_PROCESSING_PROTOCOL = "Data processing protocol";
  public static final String PROJECT_1_SAMPLE_PROCESSING_PROTOCOL = "Sample processing protocol";
  public static final String PROJECT_1_KEYWORDS = "some,keywords";
  public static final int PROJECT_1_NUM_ASSAYS = 3;
  public static final String PROJECT_1_REANALYSIS = "reanalysis";

  private static final int NUM_USERS_PROJECT_1 = 2;
  private static final int NUM_PTM_PROJECT_1 = 2;

  private static final long PTM_1_ID = 111;
  private static final long PTM_2_ID = 222;
  private static final String CV_PARAM_2_LABEL = "Project Sample Param Label";
  private static final String CV_PARAM_2_ACCESSION = "Project Sample";
  private static final String CV_PARAM_2_NAME = "Project Sample Name";
  private static final long CV_PARAM_2_ID = 66666;
  private static final long CV_PARAM_1_ID = 50005;
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
  private static final int NUM_EXP_TYPES_PROJECT_1 = 1;
  private static final long EXP_TYPE_1_ID = 1111111111;
  private static final int NUM_GROUP_PARAMS_PROJECT_1 = 1;
  private static final long GROUP_PARAM_1_ID = 1414141414;
  private static final int PROTEIN_COUNT_PROJECT_1 = 2;
  private static final int PEPTIDE_COUNT_PROJECT_1 = 2;
  private static final int UNIQUE_PEPTIDE_COUNT_PROJECT_1 = 2;
  private static final int IDENTIFIED_SPECTRUM_COUNT_PROJECT_1 = 2;
  private static final int TOTAL_SPECTRUM_COUNT_PROJECT_1 = 2;
  private static final int NUM_QUANTIFICATION_METHODS_PROJECT_1 = 1;
  private static final long QUANTIFICATION_METHOD_1_ID = 15151515;
  private static final long ANOTHER_USER_1_ID = 11113;
  private static final int NUM_INSTRUMENTS_PROJECT_1 = 1;
  private static final long REFERENCE_1_ID = 88888;
  private static final int NUM_PROJECT_SAMPLE_PARAM_PROJECT_1 = 1;
  private static final long ANOTHER_USER_ID = 123456;
  private static final String ANOTHER_USER_PASSWORD = "password";
  private static final String ANOTHER_USER_EMAIL = "chiquito@test.ebi.ac.uk";
  private static final Title ANOTHER_USER_TITLE = Title.Dr;
  private static final String ANOTHER_USER_FIRST_NAME = "Lucas";
  private static final String ANOTHER_USER_LAST_NAME = "Grijander";
  private static final String ANOTHER_USER_AFFILIATION = "EMBL-EBI";
  private int NUM_REFERENCE_PROJECT_1 = 1;
  private long PROJECT_SAMPLE_PARAM_1_ID = 77777;
  @Autowired private ProjectRepository projectRepository;

  @Autowired private UserRepository userRepository;

  @Autowired private CvParamRepository cvParamRepository;

  @Test
  @Transactional
  public void testSaveAndGet() throws Exception {
    Project project = new Project();
    project.setPublicProject(true);
    project.setAccession(PROJECT_2_ACCESSION);
    project.setSubmissionType(SubmissionType.COMPLETE);
    project.setSubmissionDate(Calendar.getInstance().getTime());
    project.setDoi("doi");
    project.setTitle("project title");
    project.setNumAssays(0);
    project.setUpdateDate(Calendar.getInstance().getTime());
    User submitter = new User();
    userRepository.save(submitter);
    project.setSubmitter(submitter);
    Collection<ProjectInstrumentCvParam> instruments = new ArrayList<>();
    ProjectInstrumentCvParam picvParam = new ProjectInstrumentCvParam();
    picvParam.setProject(project);
    picvParam.setCvParam(cvParamRepository.findById(13L).get());
    picvParam.setValue("icr");
    instruments.add(picvParam);
    project.setInstruments(instruments);

    projectRepository.save(project);

    // id will be set on save
    long newId = project.getId();
    Project other = projectRepository.findById(newId).get();

    assertEquals(other.getId(), project.getId());
    assertEquals(other.getAccession(), project.getAccession());
    assertEquals(other.getSubmissionType(), project.getSubmissionType());
    assertEquals(other.getSubmissionDate(), project.getSubmissionDate());
    assertEquals(other.getDoi(), project.getDoi());
    assertEquals(other.getSubmitter().getId(), project.getSubmitter().getId());

    // TODO: set the rest of the entities and try to persis the project and get it again

    projectRepository.delete(other);
  }

  @Test
  public void testFindByAccession() throws Exception {

    Project project = projectRepository.findByAccession(PROJECT_1_ACCESSION);

    checkIsProjectInDb(project);
  }

  private void checkIsProjectInDb(Project project) throws Exception {
    assertThat(project.getAccession(), is(PROJECT_1_ACCESSION));
    assertThat(project.getDoi(), is(PROJECT_1_DOI));
    assertThat(project.getTitle(), is(PROJECT_1_TITLE));
    assertThat(project.getProjectDescription(), is(PROJECT_1_DESCRIPTION));
    assertThat(project.getDataProcessingProtocol(), is(PROJECT_1_DATA_PROCESSING_PROTOCOL));
    assertThat(project.getSampleProcessingProtocol(), is(PROJECT_1_SAMPLE_PROCESSING_PROTOCOL));
    assertThat(project.getKeywords(), is(PROJECT_1_KEYWORDS));
    assertThat(project.getNumAssays(), is(PROJECT_1_NUM_ASSAYS));
    assertThat(project.getReanalysis(), is(PROJECT_1_REANALYSIS));
    assertThat(project.getSubmissionType(), is(SubmissionType.COMPLETE));

    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    assertThat(df.format(project.getSubmissionDate()), is("2010-05-22"));
    assertThat(df.format(project.getPublicationDate()), is("2010-10-21"));
    assertThat(df.format(project.getUpdateDate()), is("2010-07-15"));

    User submitter = project.getSubmitter();
    assertThat(submitter.getId(), is(USER_1_ID));
  }

  @Test
  public void testProjectCount() throws Exception {

    assertEquals(projectRepository.count(), NUM_PROJECTS);

    Project project = new Project();
    project.setPublicProject(true);
    project.setKeywords("keyword");
    project.setAccession(PROJECT_2_ACCESSION);
    project.setSubmissionType(SubmissionType.COMPLETE);
    project.setSubmissionDate(Calendar.getInstance().getTime());
    project.setDoi("doi");
    project.setTitle("project title");
    project.setNumAssays(0);
    project.setUpdateDate(Calendar.getInstance().getTime());
    project.setChanged(false);

    User submitter = new User();
    submitter.setEmail(ANOTHER_USER_EMAIL);
    submitter.setAffiliation(ANOTHER_USER_AFFILIATION);
    submitter.setFirstName(ANOTHER_USER_FIRST_NAME);
    submitter.setLastName(ANOTHER_USER_LAST_NAME);
    submitter.setPassword(ANOTHER_USER_PASSWORD);
    submitter.setTitle(ANOTHER_USER_TITLE);
    Date creationDate = Calendar.getInstance().getTime();
    submitter.setCreateAt(creationDate);
    Date updateDate = Calendar.getInstance().getTime();
    submitter.setUpdateAt(updateDate);
    userRepository.save(submitter);

    project.setSubmitter(submitter);

    projectRepository.save(project);

    assertEquals(projectRepository.count(), NUM_PROJECTS + 1);

    projectRepository.delete(project);
  }

  @Test
  public void testGetUsers() throws Exception {
    Project project = projectRepository.findById(PROJECT_1_ID).get();
    testUsers(project);
  }

  @Test
  public void testGetProjectAccession() throws Exception {
    List<String> projectAccessions = projectRepository.findAllAccessions();
    assertEquals(1, projectAccessions.size());
  }

  @Test
  public void testGetPTMs() throws Exception {
    Project project = projectRepository.findById(PROJECT_1_ID).get();
    testPTMs(project);
  }

  @Test
  public void testGetReferences() throws Exception {
    Project project = projectRepository.findById(PROJECT_1_ID).get();
    testReferences(project);
  }

  @Test
  public void testGetProjectSampleParams() throws Exception {
    Project project = projectRepository.findById(PROJECT_1_ID).get();
    testSampleParams(project);
  }

  @Test
  public void testGetExperimentTypes() throws Exception {
    Project project = projectRepository.findById(PROJECT_1_ID).get();
    testExperimentTypes(project);
  }

  @Test
  public void testGetFindAllBySubmitterId() throws Exception {
    List<Project> projects = projectRepository.findAllBySubmitterId(USER_1_ID);

    assertNotNull(projects);
    assertEquals(projects.size(), 1);

    Project project = projects.iterator().next();

    assertEquals(PROJECT_1_ID, (long) project.getId());
    assertEquals(PROJECT_1_ACCESSION, project.getAccession());

    testSampleParams(project);
    testReferences(project);
    testPTMs(project);
    testUsers(project);
    testExperimentTypes(project);
    testQuantificationMethods(project);
    testGroupParams(project);
    testInstruments(project);
  }

  private void testQuantificationMethods(Project project) {

    Collection<ProjectQuantificationMethodCvParam> quantificationMethods =
        project.getQuantificationMethods();

    assertNotNull(quantificationMethods);
    assertEquals(quantificationMethods.size(), NUM_QUANTIFICATION_METHODS_PROJECT_1);
    ProjectQuantificationMethodCvParam quantificationMethod =
        quantificationMethods.iterator().next();
    assertThat(quantificationMethod.getId(), is(QUANTIFICATION_METHOD_1_ID));

    CvParam cvParam = quantificationMethod.getCvParam();
    assertNotNull(cvParam);
    Assert.assertThat(cvParam.getId(), is(CV_PARAM_3_ID));
    assertEquals(CV_PARAM_3_LABEL, cvParam.getCvLabel());
    assertEquals(CV_PARAM_3_ACCESSION, cvParam.getAccession());
    assertEquals(CV_PARAM_3_NAME, cvParam.getName());
  }

  private void testSampleParams(Project project) {
    Collection<ProjectSampleCvParam> projectSamples = project.getSamples();

    assertNotNull(projectSamples);
    assertEquals(projectSamples.size(), NUM_PROJECT_SAMPLE_PARAM_PROJECT_1);
    ProjectSampleCvParam projectSample = projectSamples.iterator().next();
    assertThat(projectSample.getId(), is(PROJECT_SAMPLE_PARAM_1_ID));

    CvParam cvParam = projectSample.getCvParam();
    assertNotNull(cvParam);
    Assert.assertThat(cvParam.getId(), is(CV_PARAM_2_ID));
    assertEquals(CV_PARAM_2_LABEL, cvParam.getCvLabel());
    assertEquals(CV_PARAM_2_ACCESSION, cvParam.getAccession());
    assertEquals(CV_PARAM_2_NAME, cvParam.getName());
  }

  private void testExperimentTypes(Project project) {
    Collection<ProjectExperimentType> experimentTypes = project.getExperimentTypes();

    assertNotNull(experimentTypes);
    assertEquals(experimentTypes.size(), NUM_EXP_TYPES_PROJECT_1);
    ProjectExperimentType experimentType = experimentTypes.iterator().next();
    assertThat(experimentType.getId(), is(EXP_TYPE_1_ID));

    CvParam cvParam = experimentType.getCvParam();
    assertNotNull(cvParam);
    Assert.assertThat(cvParam.getId(), is(CV_PARAM_3_ID));
    assertEquals(CV_PARAM_3_LABEL, cvParam.getCvLabel());
    assertEquals(CV_PARAM_3_ACCESSION, cvParam.getAccession());
    assertEquals(CV_PARAM_3_NAME, cvParam.getName());
  }

  private void testGroupParams(Project project) {
    Collection<ParamProvider> groupParams = project.getParams();

    assertNotNull(groupParams);
    assertEquals(groupParams.size(), NUM_GROUP_PARAMS_PROJECT_1);
    ProjectCvParam projectCvParam = (ProjectCvParam) groupParams.iterator().next();
    assertThat(projectCvParam.getId(), is(GROUP_PARAM_1_ID));

    CvParam cvParam = projectCvParam.getCvParam();
    assertNotNull(cvParam);
    Assert.assertThat(cvParam.getId(), is(CV_PARAM_4_ID));
    assertEquals(CV_PARAM_4_LABEL, cvParam.getCvLabel());
    assertEquals(CV_PARAM_4_ACCESSION, cvParam.getAccession());
    assertEquals(CV_PARAM_4_NAME, cvParam.getName());
  }

  private void testReferences(Project project) {
    Collection<Reference> references = project.getReferences();

    assertNotNull(references);
    assertEquals(references.size(), NUM_REFERENCE_PROJECT_1);
    Reference reference = references.iterator().next();
    assertThat(reference.getId(), is(REFERENCE_1_ID));
  }

  private void testPTMs(Project project) {
    Collection<ProjectPTM> ptms = project.getPtms();

    assertNotNull(ptms);
    assertEquals(ptms.size(), NUM_PTM_PROJECT_1);
    ProjectPTM ptm = ptms.iterator().next();
    assertThat(ptm.getId(), is(PTM_1_ID));

    CvParam cvParam = ptm.getCvParam();
    assertNotNull(cvParam);
    Assert.assertThat(cvParam.getId(), is(CV_PARAM_1_ID));
    assertEquals(CV_PARAM_1_LABEL, cvParam.getCvLabel());
    assertEquals(CV_PARAM_1_ACCESSION, cvParam.getAccession());
    assertEquals(CV_PARAM_1_NAME, cvParam.getName());
  }

  private void testUsers(Project project) {
    Collection<User> users = project.getUsers();

    assertNotNull(users);
    assertEquals(users.size(), NUM_USERS_PROJECT_1);
    User user = users.iterator().next();
    assertThat(user.getId(), is(USER_1_ID));
  }

  private void testInstruments(Project project) {
    Collection<ProjectInstrumentCvParam> instruments = project.getInstruments();

    assertNotNull(instruments);
    assertThat(instruments.size(), is(NUM_INSTRUMENTS_PROJECT_1));
  }
}
