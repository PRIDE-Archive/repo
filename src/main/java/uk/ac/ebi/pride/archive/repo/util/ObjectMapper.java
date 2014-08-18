package uk.ac.ebi.pride.archive.repo.util;

import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;
import uk.ac.ebi.pride.archive.dataprovider.param.ParamProvider;
import uk.ac.ebi.pride.archive.repo.assay.Assay;
import uk.ac.ebi.pride.archive.repo.assay.instrument.Instrument;
import uk.ac.ebi.pride.archive.repo.assay.instrument.InstrumentComponent;
import uk.ac.ebi.pride.archive.repo.assay.software.Software;
import uk.ac.ebi.pride.archive.repo.file.ProjectFile;
import uk.ac.ebi.pride.archive.repo.project.*;
import uk.ac.ebi.pride.archive.repo.user.User;
import uk.ac.ebi.pride.archive.repo.assay.service.AssaySummary;
import uk.ac.ebi.pride.archive.repo.assay.service.InstrumentComponentSummary;
import uk.ac.ebi.pride.archive.repo.assay.service.InstrumentSummary;
import uk.ac.ebi.pride.archive.repo.assay.service.SoftwareSummary;
import uk.ac.ebi.pride.archive.repo.file.service.FileSummary;
import uk.ac.ebi.pride.archive.repo.param.service.CvParamSummary;
import uk.ac.ebi.pride.archive.repo.param.service.ParamSummary;
import uk.ac.ebi.pride.archive.repo.user.service.ContactSummary;
import uk.ac.ebi.pride.archive.repo.user.service.UserSummary;
import uk.ac.ebi.pride.archive.repo.project.service.ProjectSummary;
import uk.ac.ebi.pride.archive.repo.project.service.ProjectTagSummary;
import uk.ac.ebi.pride.archive.repo.project.service.ReferenceSummary;

import java.util.ArrayList;
import java.util.Collection;
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
         * http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=8013608
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
        projectSummary.setExperimentTypes(mapProjectCvParamsToCvParamSummaries(project.getExperimentTypes()));
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
        projectSummary.setQuantificationMethods(mapProjectCvParamsToCvParamSummaries(project.getQuantificationMethods()));
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

    private static Collection<ProjectTagSummary> mapProjectTagsToProjectTagSummaries(Collection<ProjectTag> projectTags) {
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
        return user == null ? null : mapper.map(user, UserSummary.class);
    }

    public static AssaySummary mapAssayToAssaySummary(Assay assay) {
        return mapper.map(assay, AssaySummary.class);
    }

    public static Collection<FileSummary> mapProjectFileToFileSummaries(List<ProjectFile> projectFiles) {
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

    public static User mapUserSummaryToUser(UserSummary user) {
        return mapper.map(user, User.class);
    }

    public static SoftwareSummary mapSoftwareToSoftwareSummary(Software software) {
        return mapper.map(software, SoftwareSummary.class);
    }

    public static InstrumentSummary mapInstrumentToInstrumentSummary(Instrument instrument) {
        return mapper.map(instrument, InstrumentSummary.class);
    }

    public static InstrumentComponentSummary mapInstrumentComponentToInstrumentComponentSummary(InstrumentComponent instrumentComponent) {
        return mapper.map(instrumentComponent, InstrumentComponentSummary.class);
    }

    public static Collection<ReferenceSummary> mapReferencesToReferenceSummaries(Collection<Reference> references) {
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

    public static Collection<ContactSummary> mapLabHeadsToContactSummaries(Collection<LabHead> labHeads) {
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

    public static Collection<CvParamSummary> mapProjectCvParamsToCvParamSummaries(Collection<? extends ProjectCvParam> projectCvParams) {
        ArrayList<CvParamSummary> cvParamSummaries = new ArrayList<CvParamSummary>();

        if (projectCvParams != null) {
            for (ProjectCvParam projectCvParam : projectCvParams) {
                cvParamSummaries.add(mapProjectCvParamToCvParamSummary(projectCvParam));
            }
        }

        return cvParamSummaries;
    }

    public static CvParamSummary mapProjectCvParamToCvParamSummary(ProjectCvParam projectCvParam) {
        return projectCvParam == null ? null : mapper.map(projectCvParam, CvParamSummary.class);
    }

    public static Collection<CvParamSummary> mapProjectPTMsToCvParamSummaries(Collection<ProjectPTM> projectPTMs) {
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

    public static Collection<ParamSummary> mapParamProvidersToParamSummaries(Collection<? extends ParamProvider> params) {
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
