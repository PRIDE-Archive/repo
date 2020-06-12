package uk.ac.ebi.pride.archive.repo.services.project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import uk.ac.ebi.pride.archive.repo.repos.project.Project;
import uk.ac.ebi.pride.archive.repo.repos.project.ProjectRepository;
import uk.ac.ebi.pride.archive.repo.util.ObjectMapper;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Rui Wang
 * @author Jose A. Dianes
 * @version $Id$
 * <p>
 */
@Service
@Transactional(readOnly = true)
public class ProjectServiceImpl implements ProjectService {
    private static final Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

    private ProjectRepository projectRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Collection<ProjectSummary> findAllBySubmitterId(Long submitterId)
            throws ProjectAccessException {
        Assert.notNull(submitterId, "Submitter id cannot be null");

        try {
            Collection<ProjectSummary> projectSummaries = new LinkedList<>();

            for (Project project : projectRepository.findAllBySubmitterId(submitterId)) {
                ProjectSummary projectSummary = ObjectMapper.mapProjectToProjectSummary(project);
                projectSummaries.add(projectSummary);
            }

            return projectSummaries;
        } catch (Exception ex) {
            String msg = "Failed to find projects by submitter id: " + submitterId;
            logger.error(msg, ex);
            throw new ProjectAccessException(msg, ex);
        }
    }

    @Override
    public ProjectSummary findById(Long projectId) throws ProjectAccessException {
        Assert.notNull(projectId, "Project id cannot be null");

        try {
            Optional<Project> project = projectRepository.findById(projectId);

            return ObjectMapper.mapProjectToProjectSummary(project.get());
        } catch (Exception ex) {
            String msg = "Failed to find project using project id: " + projectId;
            logger.error(msg, ex);
            throw new ProjectAccessException(msg, ex);
        }
    }

    @Override
    public ProjectSummary findByAccession(String accession) throws ProjectAccessException {
        Assert.notNull(accession, "Project accession cannot be null");

        try {
            Project project = projectRepository.findByAccession(accession);

            return ObjectMapper.mapProjectToProjectSummary(project);
        } catch (Exception ex) {
            String msg = "Failed to find project using project accession: " + accession;
            logger.error(msg, ex);
            throw new ProjectAccessException(msg, ex, accession);
        }
    }

    @Override
    public List<ProjectSummary> findFilteredBySubmitterIdAndIsPublic(Long submitterId, Boolean isPublic) {
        Assert.notNull(submitterId, "submitterId cannot be null");
        Assert.notNull(isPublic, "isPublic cannot be null");

        try {
            List<Project> projects = projectRepository.findFilteredBySubmitterIdAndIsPublic(submitterId, isPublic);
            return projects.stream().map(ObjectMapper::mapProjectToProjectSummary).collect(Collectors.toList());
        } catch (Exception ex) {
            String msg = "Failed to find project using submitterId : " + submitterId + "& isPublic: " + isPublic;
            logger.error(msg, ex);
            throw new ProjectAccessException(msg, ex);
        }
    }

    @Override
    public List<ProjectSummary> findFilteredByReviewer(String user_aap_ref) {
        Assert.notNull(user_aap_ref, "user_aap_ref cannot be null");
        try {
            List<Project> projects = projectRepository.findFilteredByReviewer(user_aap_ref);
            return projects.stream().map(ObjectMapper::mapProjectToProjectSummary).collect(Collectors.toList());
        } catch (Exception ex) {
            String msg = "Failed to find project using Reviewer user_aap_ref : " + user_aap_ref;
            logger.error(msg, ex);
            throw new ProjectAccessException(msg, ex);
        }
    }

    @Override
    public List<List<String>> findMonthlySubmissions() {
        return projectRepository.findMonthlySubmissions();
    }

    @Override
    public List<String> findAllAccessionsChanged() {
        return projectRepository.findAllAccessionsChanged();
    }
}
