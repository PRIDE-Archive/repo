package uk.ac.ebi.pride.archive.repo.project.service;

import uk.ac.ebi.pride.archive.dataprovider.identification.PeptideSequenceProvider;
import uk.ac.ebi.pride.archive.dataprovider.identification.ProteinIdentificationProvider;
import uk.ac.ebi.pride.archive.dataprovider.project.ProjectProvider;
import uk.ac.ebi.pride.archive.dataprovider.project.SubmissionType;
import uk.ac.ebi.pride.archive.repo.param.service.CvParamSummary;
import uk.ac.ebi.pride.archive.repo.param.service.ParamSummary;
import uk.ac.ebi.pride.archive.repo.user.service.ContactSummary;
import uk.ac.ebi.pride.archive.repo.user.service.UserSummary;
import uk.ac.ebi.pride.archive.repo.util.CollectionUtils;
import uk.ac.ebi.pride.archive.utils.cv.Ontology;
import uk.ac.ebi.pride.archive.utils.tag.ProjectTag;
import uk.ac.ebi.pride.archive.utils.tag.ProjectTagType;

import java.util.*;

/**
 * @author Rui Wang
 * @version $Id$
 */
public class ProjectSummary implements ProjectProvider {

    private String accession;
    private String doi;
    private String title;
    private String projectDescription;
    private String sampleProcessingProtocol;
    private String dataProcessingProtocol;
    private String otherOmicsLink;
    private UserSummary submitter;
    private final Collection<UserSummary> users;
    private String keywords;
    private int numAssays;
    private String reanalysis;
    private final Collection<CvParamSummary> experimentTypes;
    private SubmissionType submissionType;
    private Date submissionDate;
    private Date publicationDate;
    private Date updateDate;
    private final Collection<ReferenceSummary> references;
    private final Collection<ProjectTagSummary> projectTags;
    private final Collection<ContactSummary> labHeads;
    private final Collection<CvParamSummary> ptms;
    private final Collection<CvParamSummary> samples;
    private final Collection<CvParamSummary> instruments;
    private final Collection<CvParamSummary> software;
    private final Collection<CvParamSummary> quantificationMethods;
    private boolean publicProject;
    private Long id;
    private boolean changed;
    private final Collection<ParamSummary> params;
    private final Collection<CvParamSummary> species;
    private final Collection<CvParamSummary> tissues;
    private final Collection<CvParamSummary> cellTypes;
    private final Collection<CvParamSummary> diseases;
    private final Collection<CvParamSummary> goTerms;
    private Boolean highlighted;
    private final Collection<ProjectTagSummary> parentProjectTags;
    private final Collection<ProjectTagSummary> internalTags;


    public ProjectSummary() {
        this.users = new ArrayList<UserSummary>();
        this.experimentTypes = new ArrayList<CvParamSummary>();
        this.references = new ArrayList<ReferenceSummary>();
        this.projectTags = new ArrayList<ProjectTagSummary>();
        this.labHeads = new ArrayList<ContactSummary>();
        this.ptms = new ArrayList<CvParamSummary>();
        this.samples = new ArrayList<CvParamSummary>();
        this.instruments = new ArrayList<CvParamSummary>();
        this.software = new ArrayList<CvParamSummary>();
        this.quantificationMethods = new ArrayList<CvParamSummary>();
        this.publicProject = false;
        this.changed = false;
        this.params = new ArrayList<ParamSummary>();
        this.species = new ArrayList<CvParamSummary>();
        this.tissues = new ArrayList<CvParamSummary>();
        this.cellTypes = new ArrayList<CvParamSummary>();
        this.diseases = new ArrayList<CvParamSummary>();
        this.goTerms = new ArrayList<CvParamSummary>();
        this.internalTags = new ArrayList<ProjectTagSummary>();
        this.parentProjectTags = new ArrayList<ProjectTagSummary>();
        this.highlighted = null;
    }

    public String getAccession() {
        return accession;
    }

    public void setAccession(String accession) {
        this.accession = accession;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }


    public String getSampleProcessingProtocol() {
        return sampleProcessingProtocol;
    }

    public void setSampleProcessingProtocol(String sampleProcessingProtocol) {
        this.sampleProcessingProtocol = sampleProcessingProtocol;
    }

    public String getDataProcessingProtocol() {
        return dataProcessingProtocol;
    }

    public void setDataProcessingProtocol(String dataProcessingProtocol) {
        this.dataProcessingProtocol = dataProcessingProtocol;
    }

    public String getOtherOmicsLink() {
        return otherOmicsLink;
    }

    public void setOtherOmicsLink(String otherOmicsLink) {
        this.otherOmicsLink = otherOmicsLink;
    }

    public UserSummary getSubmitter() {
        return submitter;
    }

    public void setSubmitter(UserSummary sub) {
        this.submitter = sub;
    }

    public Collection<UserSummary> getUsers() {
        return users;
    }

    public void setUsers(Collection<UserSummary> users) {
        CollectionUtils.replaceValuesInCollection(users, this.users);
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public int getNumAssays() {
        return numAssays;
    }

    public void setNumAssays(int numAssays) {
        this.numAssays = numAssays;
    }

    public String getReanalysis() {
        return reanalysis;
    }

    public void setReanalysis(String reanalysis) {
        this.reanalysis = reanalysis;
    }

    public Collection<CvParamSummary> getExperimentTypes() {
        return experimentTypes;
    }

    public void setExperimentTypes(Collection<CvParamSummary> experimentTypes) {
        CollectionUtils.replaceValuesInCollection(experimentTypes, this.experimentTypes);
    }

    public SubmissionType getSubmissionType() {
        return submissionType;
    }

    public void setSubmissionType(SubmissionType submissionType) {
        this.submissionType = submissionType;
    }

    public Date getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Collection<ReferenceSummary> getReferences() {
        return references;
    }

    public void setReferences(Collection<ReferenceSummary> references) {
        CollectionUtils.replaceValuesInCollection(references, this.references);
    }

    public Collection<CvParamSummary> getPtms() {
        return ptms;
    }

    public void setPtms(Collection<CvParamSummary> ptms) {
        CollectionUtils.replaceValuesInCollection(ptms, this.ptms);
    }

    public Collection<CvParamSummary> getSamples() {
        return samples;
    }

    public void setSamples(Collection<CvParamSummary> samples) {
        CollectionUtils.replaceValuesInCollection(samples, this.samples);
        this.species.clear();
        this.tissues.clear();
        this.diseases.clear();
        this.cellTypes.clear();
        this.goTerms.clear();
    }

    public Collection<CvParamSummary> getInstruments() {
        return instruments;
    }

    public void setInstruments(Collection<CvParamSummary> instruments) {
        CollectionUtils.replaceValuesInCollection(instruments, this.instruments);
    }

    public Collection<CvParamSummary> getSoftware() {
        return software;
    }

    public void setSoftware(Collection<CvParamSummary> software) {
        CollectionUtils.replaceValuesInCollection(software, this.software);
    }

    public Collection<CvParamSummary> getQuantificationMethods() {
        return quantificationMethods;
    }

    @Override
    public Map<String, Collection<ProteinIdentificationProvider>> getProteinIdentifications() {
        //TODO May be need to return null?
        return new HashMap<String, Collection<ProteinIdentificationProvider>>();
    }

    @Override
    public Collection<PeptideSequenceProvider> getPeptideSequences() {
        return new LinkedList<PeptideSequenceProvider>();
    }

    public void setQuantificationMethods(Collection<CvParamSummary> quantificationMethods) {
        CollectionUtils.replaceValuesInCollection(quantificationMethods, this.quantificationMethods);
    }

    public boolean isPublicProject() {
        return publicProject;
    }

    public void setPublicProject(boolean publicProject) {
        this.publicProject = publicProject;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public Collection<ParamSummary> getParams() {
        return params;
    }

    public void setParams(Collection<ParamSummary> params) {
        CollectionUtils.replaceValuesInCollection(params, this.params);
    }

    public Collection<CvParamSummary> getSpecies() {
        if (species.isEmpty()) {
            for (CvParamSummary sample : samples) {
                String cvLabel = sample.getCvLabel();
                if (Ontology.NEWT.getName().equalsIgnoreCase(cvLabel)) {
                    this.species.add(sample);
                }
            }
        }

        return species;
    }

    public Collection<CvParamSummary> getTissues() {
        if (tissues.isEmpty()) {
            for (CvParamSummary sample : samples) {
                String cvLabel = sample.getCvLabel();
                if (Ontology.BRENDA.getName().equalsIgnoreCase(cvLabel)) {
                    this.tissues.add(sample);
                }
            }
        }
        return tissues;
    }

    public Collection<CvParamSummary> getCellTypes() {
        if (cellTypes.isEmpty()) {
            for (CvParamSummary sample : samples) {
                String cvLabel = sample.getCvLabel();
                if (Ontology.CL.getName().equalsIgnoreCase(cvLabel)) {
                    this.cellTypes.add(sample);
                }
            }
        }
        return cellTypes;
    }

    public Collection<CvParamSummary> getDiseases() {
        if (diseases.isEmpty()) {
            for (CvParamSummary sample : samples) {
                String cvLabel = sample.getCvLabel();
                if (Ontology.DISEASE.getName().equalsIgnoreCase(cvLabel)) {
                    this.diseases.add(sample);
                }
            }
        }
        return diseases;
    }

    public Collection<CvParamSummary> getGoTerms() {
        if (goTerms.isEmpty()) {
            for (CvParamSummary sample : samples) {
                String cvLabel = sample.getCvLabel();
                if (Ontology.GO.getName().equalsIgnoreCase(cvLabel)) {
                    this.goTerms.add(sample);
                }
            }
        }
        return goTerms;
    }

    public Collection<ProjectTagSummary> getProjectTags() {
        return projectTags;
    }

    public void setProjectTags(Collection<ProjectTagSummary> projectTags) {
        CollectionUtils.replaceValuesInCollection(projectTags, this.projectTags);
        this.highlighted = null;
        this.parentProjectTags.clear();
        this.internalTags.clear();
    }

    public Collection<ProjectTagSummary> getParentProjectTags() {
        if (parentProjectTags.isEmpty()) {
            for (ProjectTagSummary projectTagSummary : projectTags) {
                String tagName = projectTagSummary.getTag();
                ProjectTag projectTag = ProjectTag.getProjectTagByName(tagName);
                if(ProjectTagType.PARENT_PROJECT.equals(projectTag.getType())){
                    parentProjectTags.add(projectTagSummary);
                }
            }
        }
        return parentProjectTags;
    }

    public Collection<ProjectTagSummary> getInternalTags() {
        if (internalTags.isEmpty()) {
            for (ProjectTagSummary projectTagSummary : projectTags) {
                String tagName = projectTagSummary.getTag();
                ProjectTag projectTag = ProjectTag.getProjectTagByName(tagName);
                if(ProjectTagType.PRIDE_INTERNAL.equals(projectTag.getType())){
                    internalTags.add(projectTagSummary);
                }
            }
        }
        return internalTags;
    }

    public Boolean getHighlighted() {
        if (highlighted == null) {
            for (ProjectTagSummary projectTagSummary : projectTags) {
                String tagName = projectTagSummary.getTag();
                ProjectTag projectTag = ProjectTag.getProjectTagByName(tagName);
                if (ProjectTagType.PRIDE_HIGHLIGHTED.equals(projectTag.getType())) {
                    this.highlighted = true;
                }
            }
        }

        return highlighted;
    }

    public Collection<ContactSummary> getLabHeads() {
        return labHeads;
    }

    public void setLabHeads(Collection<ContactSummary> labHeads) {
        CollectionUtils.replaceValuesInCollection(labHeads, this.labHeads);
    }

}
