package uk.ac.ebi.pride.archive.repo.project.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import uk.ac.ebi.pride.archive.repo.project.Project;
import uk.ac.ebi.pride.archive.repo.project.ProjectRepository;
import uk.ac.ebi.pride.archive.repo.util.ObjectMapper;

import java.util.Collection;
import java.util.LinkedList;

/**
 * @author Rui Wang
 * @author Jose A. Dianes
 * @version $Id$
 *          <p/>
 */
@Repository
@Transactional(readOnly = true)
public class ProjectServiceImpl implements ProjectService {
    private static final Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

    private ProjectRepository projectRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Collection<ProjectSummary> findAllBySubmitterId(Long submitterId) throws ProjectAccessException {
        Assert.notNull(submitterId, "Submitter id cannot be null");

        try {
            Collection<ProjectSummary> projectSummaries = new LinkedList<ProjectSummary>();

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
            Project project = projectRepository.findOne(projectId);

            return ObjectMapper.mapProjectToProjectSummary(project);
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


}
