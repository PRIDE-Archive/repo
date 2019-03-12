package uk.ac.ebi.pride.archive.repo.util;

import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;
import uk.ac.ebi.pride.archive.dataprovider.param.CvParamProvider;
import uk.ac.ebi.pride.archive.dataprovider.param.ParamProvider;
import uk.ac.ebi.pride.archive.repo.repos.assay.Assay;
import uk.ac.ebi.pride.archive.repo.repos.assay.AssayCvParam;
import uk.ac.ebi.pride.archive.repo.repos.assay.AssaySampleCvParam;
import uk.ac.ebi.pride.archive.repo.repos.assay.Contact;
import uk.ac.ebi.pride.archive.repo.repos.assay.instrument.Instrument;
import uk.ac.ebi.pride.archive.repo.repos.assay.instrument.InstrumentComponent;
import uk.ac.ebi.pride.archive.repo.repos.assay.software.Software;
import uk.ac.ebi.pride.archive.repo.repos.file.ProjectFile;
import uk.ac.ebi.pride.archive.repo.repos.project.*;
import uk.ac.ebi.pride.archive.repo.repos.user.User;
import uk.ac.ebi.pride.archive.repo.repos.user.UserAAP;
import uk.ac.ebi.pride.archive.repo.services.assay.AssaySummary;
import uk.ac.ebi.pride.archive.repo.services.assay.InstrumentComponentSummary;
import uk.ac.ebi.pride.archive.repo.services.assay.InstrumentSummary;
import uk.ac.ebi.pride.archive.repo.services.assay.SoftwareSummary;
import uk.ac.ebi.pride.archive.repo.services.file.FileSummary;
import uk.ac.ebi.pride.archive.repo.services.param.CvParamSummary;
import uk.ac.ebi.pride.archive.repo.services.param.ParamSummary;
import uk.ac.ebi.pride.archive.repo.services.project.ProjectSummary;
import uk.ac.ebi.pride.archive.repo.services.project.ProjectTagSummary;
import uk.ac.ebi.pride.archive.repo.services.project.ReferenceSummary;
import uk.ac.ebi.pride.archive.repo.services.user.ContactSummary;
import uk.ac.ebi.pride.archive.repo.services.user.UserSummary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * @author Rui Wang
 * @version $Id$
 */
public final class ObjectMapper {
  public static final Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();

  public static ProjectSummary mapProjectToProjectSummary(Project project) {

    if (project == null) {
      return null;
    }

    /**
     * Manual mapping required for overcoming a bug in jdk, casuing dozer to fail overtime
     *
     * <p>http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=8013608
     */
    ProjectSummary projectSummary = new ProjectSummary();

    projectSummary.setAccession(project.getAccession());
    projectSummary.setDoi(project.getDoi());
    projectSummary.setTitle(project.getTitle());
    projectSummary.setProjectDescription(project.getProjectDescription());
    projectSummary.setSampleProcessingProtocol(project.getSampleProcessingProtocol());
    projectSummary.setDataProcessingProtocol(project.getDataProcessingProtocol());
    projectSummary.setOtherOmicsLink(project.getOtherOmicsLink());
    projectSummary.setSubmitter(mapUserToUserSummary(project.getSubmitter()));
    projectSummary.setUsers(mapUsersToUserSummaries(project.getUsers()));
    projectSummary.setKeywords(project.getKeywords());
    projectSummary.setNumAssays(project.getNumAssays());
    projectSummary.setReanalysis(project.getReanalysis());
    projectSummary.setExperimentTypes(
            mapProjectCvParamsToCvParamSummaries(project.getExperimentTypes()));
    projectSummary.setSubmissionType(project.getSubmissionType());
    projectSummary.setSubmissionDate(project.getSubmissionDate());
    projectSummary.setPublicationDate(project.getPublicationDate());
    projectSummary.setUpdateDate(project.getUpdateDate());
    projectSummary.setReferences(mapReferencesToReferenceSummaries(project.getReferences()));
    projectSummary.setLabHeads(mapLabHeadsToContactSummaries(project.getLabHeads()));
    projectSummary.setPtms(mapProjectPTMsToCvParamSummaries(project.getPtms()));
    projectSummary.setSamples(mapProjectCvParamsToCvParamSummaries(project.getSamples()));
    projectSummary.setInstruments(mapProjectCvParamsToCvParamSummaries(project.getInstruments()));
    projectSummary.setSoftware(mapProjectCvParamsToCvParamSummaries(project.getSoftware()));
    projectSummary.setQuantificationMethods(
            mapProjectCvParamsToCvParamSummaries(project.getQuantificationMethods()));
    projectSummary.setPublicProject(project.isPublicProject());
    projectSummary.setId(project.getId());
    projectSummary.setChanged(project.isChanged());
    projectSummary.setParams(mapParamProvidersToParamSummaries(project.getParams()));
    projectSummary.setProjectTags(mapProjectTagsToProjectTagSummaries(project.getProjectTags()));
    Collection<ParamSummary> params = projectSummary.getParams();
    params.addAll(mapParamProvidersToParamSummaries(project.getProjectGroupCvParams()));
    params.addAll(mapParamProvidersToParamSummaries(project.getProjectGroupUserParams()));

    return projectSummary;
  }

  private static Collection<ProjectTagSummary> mapProjectTagsToProjectTagSummaries(
          Collection<ProjectTag> projectTags) {
    ArrayList<ProjectTagSummary> projectTagSummaries = new ArrayList<ProjectTagSummary>();

    if (projectTags != null) {
      for (ProjectTag projectTag : projectTags) {
        projectTagSummaries.add(mapper.map(projectTag, ProjectTagSummary.class));
      }
    }

    return projectTagSummaries;
  }

  public static Collection<UserSummary> mapUsersToUserSummaries(Collection<User> users) {
    ArrayList<UserSummary> userSummaries = new ArrayList<UserSummary>();

    if (users != null) {
      for (User user : users) {
        userSummaries.add(mapUserToUserSummary(user));
      }
    }

    return userSummaries;
  }

  public static UserSummary mapUserToUserSummary(User user) {
    UserSummary result = new UserSummary();
    result.setId(user.getId());
    result.setEmail(user.getEmail());
    result.setPassword(user.getPassword());
    result.setTitle(user.getTitle());
    result.setFirstName(user.getFirstName());
    result.setLastName(user.getLastName());
    result.setAffiliation(user.getAffiliation());
    result.setUserAuthorities(new HashSet<>(user.getUserAuthorities()));
    result.setCreateAt(user.getCreateAt());
    result.setUpdateAt(user.getUpdateAt());
    result.setCountry(user.getCountry());
    result.setUserRef(user.getUserRef());
    result.setOrcid(user.getOrcid());
    result.setAcceptedTermsOfUse(
            user.getAcceptedTermsOfUse() != null && (user.getAcceptedTermsOfUse() == 1));
    result.setAcceptedTermsOfUseAt(user.getAcceptedTermsOfUseAt());
    return result;
  }

  public static UserAAP mapUsertoUserAAP(User user){
      UserAAP userAAP = new UserAAP();
      userAAP.setEmail(user.getEmail());
      userAAP.setUsername(user.getEmail());//email is the username for pride hence using same for AAP
      userAAP.setPassword(user.getPassword());
      userAAP.setName(user.getFirstName()+" "+user.getLastName());
      userAAP.setOrganization(user.getAffiliation().substring(0, user.getAffiliation().length()<=255?user.getAffiliation().length():255));//AAP limits org size to 255bytes
      return userAAP;
  }

  public static AssaySummary mapAssayToAssaySummary(Assay assay) {
    if (assay == null) {
      return null;
    }

    final AssaySummary assaySummary = new AssaySummary();

    assaySummary.setId(assay.getId());
    assaySummary.setProjectId(assay.getProjectId());
    assaySummary.setAccession(assay.getAccession());
    assaySummary.setTitle(assay.getTitle());
    assaySummary.setShortLabel(assay.getShortLabel());
    assaySummary.setExperimentalFactor(assay.getExperimentalFactor());
    assaySummary.setProteinCount(assay.getProteinCount());
    assaySummary.setPeptideCount(assay.getPeptideCount());
    assaySummary.setUniquePeptideCount(assay.getUniquePeptideCount());
    assaySummary.setIdentifiedSpectrumCount(assay.getIdentifiedSpectrumCount());
    assaySummary.setTotalSpectrumCount(assay.getTotalSpectrumCount());
    assaySummary.setMs2Annotation(assay.hasMs2Annotation());
    assaySummary.setChromatogram(assay.hasChromatogram());
    final Collection<AssaySampleCvParam> samples = assay.getSamples();
    assaySummary.setSamples(mapAssayCvParamsToCvParamSummaries(samples));
    final Collection<Instrument> instruments = assay.getInstruments();
    assaySummary.setInstruments(mapInstrumentsToInstrumentSummaries(instruments));
    final Collection<Software> softwares = assay.getSoftwares();
    assaySummary.setSoftwares(mapSoftwaresToSoftwareSummaries(softwares));
    assaySummary.setPtms(mapCvParamProvidersToCvParamSummaries(assay.getPtms()));
    assaySummary.setQuantificationMethods(
            mapAssayCvParamsToCvParamSummaries(assay.getQuantificationMethods()));
    assaySummary.setContacts(mapContactsToContactSummaries(assay.getContacts()));
    assaySummary.setParams(mapParamProvidersToParamSummaries(assay.getParams()));

    return assaySummary;
  }

  public static Collection<FileSummary> mapProjectFileToFileSummaries(
          List<ProjectFile> projectFiles) {
    Collection<FileSummary> fileSummaries = new ArrayList<FileSummary>();

    if (projectFiles != null) {
      for (ProjectFile projectFile : projectFiles) {
        fileSummaries.add(mapper.map(projectFile, FileSummary.class));
      }
    }

    return fileSummaries;
  }

  public static FileSummary mapProjectFileToFileSummary(ProjectFile projectFile) {
    return mapper.map(projectFile, FileSummary.class);
  }

  public static User mapUserSummaryToUser(UserSummary userSummary) {
    User result = new User();
    result.setId(userSummary.getId());
    result.setEmail(userSummary.getEmail());
    result.setPassword(userSummary.getPassword());
    result.setTitle(userSummary.getTitle());
    result.setFirstName(userSummary.getFirstName());
    result.setLastName(userSummary.getLastName());
    result.setAffiliation(userSummary.getAffiliation());
    result.setUserAuthorities(new HashSet<>(userSummary.getUserAuthorities()));
    result.setCreateAt(userSummary.getCreateAt());
    result.setUpdateAt(userSummary.getUpdateAt());
    result.setCountry(userSummary.getCountry());
    result.setOrcid(userSummary.getOrcid());
    result.setUserRef(userSummary.getUserRef());
    result.setAcceptedTermsOfUse(
            userSummary.getAcceptedTermsOfUse() != null
                    ? (userSummary.getAcceptedTermsOfUse() ? 1 : 0)
                    : 0);
    result.setAcceptedTermsOfUseAt(userSummary.getAcceptedTermsOfUseAt());
    return result;
  }

  public static Collection<SoftwareSummary> mapSoftwaresToSoftwareSummaries(
          Collection<Software> softwares) {
    final ArrayList<SoftwareSummary> softwareSummaries = new ArrayList<SoftwareSummary>();

    if (softwares != null) {
      for (Software software : softwares) {
        softwareSummaries.add(mapSoftwareToSoftwareSummary(software));
      }
    }

    return softwareSummaries;
  }

  public static SoftwareSummary mapSoftwareToSoftwareSummary(Software software) {
    final SoftwareSummary softwareSummary = new SoftwareSummary();

    softwareSummary.setId(software.getId());
    softwareSummary.setOrder(software.getOrder());
    softwareSummary.setCustomization(software.getCustomization());
    softwareSummary.setName(software.getName());
    softwareSummary.setVersion(software.getVersion());
    softwareSummary.setParams(mapParamProvidersToParamSummaries(software.getParams()));

    return softwareSummary;
  }

  public static Collection<InstrumentSummary> mapInstrumentsToInstrumentSummaries(
          Collection<Instrument> instruments) {
    final ArrayList<InstrumentSummary> instrumentSummaries = new ArrayList<InstrumentSummary>();

    if (instruments != null) {
      for (Instrument instrument : instruments) {
        instrumentSummaries.add(mapInstrumentToInstrumentSummary(instrument));
      }
    }

    return instrumentSummaries;
  }

  public static InstrumentSummary mapInstrumentToInstrumentSummary(Instrument instrument) {
    final InstrumentSummary instrumentSummary = new InstrumentSummary();

    instrumentSummary.setId(instrument.getId());
    final CvParamSummary model = mapper.map(instrument.getCvParam(), CvParamSummary.class);
    model.setValue(instrument.getValue());
    instrumentSummary.setModel(model);
    instrumentSummary.setSources(
            mapInstrumentComponentsToInstrumentComponentSummaries(instrument.getSources()));
    instrumentSummary.setAnalyzers(
            mapInstrumentComponentsToInstrumentComponentSummaries(instrument.getAnalyzers()));
    instrumentSummary.setDetectors(
            mapInstrumentComponentsToInstrumentComponentSummaries(instrument.getDetectors()));

    return instrumentSummary;
  }

  public static Collection<InstrumentComponentSummary>
  mapInstrumentComponentsToInstrumentComponentSummaries(
          Collection<? extends InstrumentComponent> instrumentComponents) {
    final ArrayList<InstrumentComponentSummary> instrumentComponentSummaries =
            new ArrayList<InstrumentComponentSummary>();

    if (instrumentComponents != null) {
      for (InstrumentComponent instrumentComponent : instrumentComponents) {
        instrumentComponentSummaries.add(
                mapInstrumentComponentToInstrumentComponentSummary(instrumentComponent));
      }
    }

    return instrumentComponentSummaries;
  }

  public static InstrumentComponentSummary mapInstrumentComponentToInstrumentComponentSummary(
          InstrumentComponent instrumentComponent) {
    final InstrumentComponentSummary instrumentComponentSummary = new InstrumentComponentSummary();

    instrumentComponentSummary.setId(instrumentComponent.getId());
    instrumentComponentSummary.setOrder(instrumentComponent.getOrder());
    instrumentComponentSummary.setParams(
            mapParamProvidersToParamSummaries(instrumentComponent.getParams()));

    return instrumentComponentSummary;
  }

  public static Collection<ReferenceSummary> mapReferencesToReferenceSummaries(
          Collection<Reference> references) {
    ArrayList<ReferenceSummary> referenceSummaries = new ArrayList<ReferenceSummary>();

    if (references != null) {
      for (Reference reference : references) {
        referenceSummaries.add(mapReferenceSummaryToReference(reference));
      }
    }

    return referenceSummaries;
  }

  public static ReferenceSummary mapReferenceSummaryToReference(Reference reference) {
    return reference == null ? null : mapper.map(reference, ReferenceSummary.class);
  }

  public static Collection<ContactSummary> mapLabHeadsToContactSummaries(
          Collection<LabHead> labHeads) {
    ArrayList<ContactSummary> contactSummaries = new ArrayList<ContactSummary>();

    if (labHeads != null) {
      for (LabHead labHead : labHeads) {
        contactSummaries.add(mapLabHeadToContactSummary(labHead));
      }
    }

    return contactSummaries;
  }

  public static ContactSummary mapLabHeadToContactSummary(LabHead labHead) {
    return labHead == null ? null : mapper.map(labHead, ContactSummary.class);
  }

  public static Collection<CvParamSummary> mapProjectCvParamsToCvParamSummaries(
          Collection<? extends ProjectCvParam> projectCvParams) {
    ArrayList<CvParamSummary> cvParamSummaries = new ArrayList<CvParamSummary>();

    if (projectCvParams != null) {
      for (ProjectCvParam projectCvParam : projectCvParams) {
        cvParamSummaries.add(mapProjectCvParamToCvParamSummary(projectCvParam));
      }
    }

    return cvParamSummaries;
  }

  public static Collection<ContactSummary> mapContactsToContactSummaries(
          Collection<Contact> contacts) {
    final ArrayList<ContactSummary> contactSummaries = new ArrayList<ContactSummary>();

    if (contacts != null) {
      for (Contact contact : contacts) {
        contactSummaries.add(mapContactToContactSummary(contact));
      }
    }

    return contactSummaries;
  }

  public static ContactSummary mapContactToContactSummary(Contact contact) {
    return contact == null ? null : mapper.map(contact, ContactSummary.class);
  }

  public static CvParamSummary mapProjectCvParamToCvParamSummary(ProjectCvParam projectCvParam) {
    return projectCvParam == null ? null : mapper.map(projectCvParam, CvParamSummary.class);
  }

  public static Collection<CvParamSummary> mapAssayCvParamsToCvParamSummaries(
          Collection<? extends AssayCvParam> assayCvParams) {
    ArrayList<CvParamSummary> cvParamSummaries = new ArrayList<CvParamSummary>();

    if (assayCvParams != null) {
      for (AssayCvParam assayCvParam : assayCvParams) {
        cvParamSummaries.add(mapAssayCvParamToCvParamSummary(assayCvParam));
      }
    }

    return cvParamSummaries;
  }

  public static CvParamSummary mapAssayCvParamToCvParamSummary(AssayCvParam assayCvParam) {
    return assayCvParam == null ? null : mapper.map(assayCvParam, CvParamSummary.class);
  }

  public static Collection<CvParamSummary> mapProjectPTMsToCvParamSummaries(
          Collection<ProjectPTM> projectPTMs) {
    ArrayList<CvParamSummary> cvParamSummaries = new ArrayList<CvParamSummary>();

    if (projectPTMs != null) {
      for (ProjectPTM projectPTM : projectPTMs) {
        cvParamSummaries.add(mapProjectPTMToCvParamSummary(projectPTM));
      }
    }

    return cvParamSummaries;
  }

  public static CvParamSummary mapProjectPTMToCvParamSummary(ProjectPTM ptm) {
    return ptm == null ? null : mapper.map(ptm, CvParamSummary.class);
  }

  public static Collection<CvParamSummary> mapCvParamProvidersToCvParamSummaries(
          Collection<? extends CvParamProvider> cvParamProviders) {
    final ArrayList<CvParamSummary> cvParamSummaries = new ArrayList<CvParamSummary>();

    if (cvParamProviders != null) {
      for (CvParamProvider cvParamProvider : cvParamProviders) {
        cvParamSummaries.add(mapCvParamProviderToCvParamSummary(cvParamProvider));
      }
    }

    return cvParamSummaries;
  }

  public static CvParamSummary mapCvParamProviderToCvParamSummary(CvParamProvider cvParamProvider) {
    return cvParamProvider == null ? null : mapper.map(cvParamProvider, CvParamSummary.class);
  }

  public static Collection<ParamSummary> mapParamProvidersToParamSummaries(
          Collection<? extends ParamProvider> params) {
    ArrayList<ParamSummary> paramSummaries = new ArrayList<ParamSummary>();

    if (params != null) {
      for (ParamProvider param : params) {
        paramSummaries.add(mapParamProviderToParamSummary(param));
      }
    }

    return paramSummaries;
  }

  public static ParamSummary mapParamProviderToParamSummary(ParamProvider param) {
    return param == null ? null : mapper.map(param, ParamSummary.class);
  }
}
