package uk.ac.ebi.pride.archive.repo.file.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import uk.ac.ebi.pride.archive.repo.assay.Assay;
import uk.ac.ebi.pride.archive.repo.assay.AssayRepository;
import uk.ac.ebi.pride.archive.repo.file.ProjectFile;
import uk.ac.ebi.pride.archive.repo.file.ProjectFileRepository;
import uk.ac.ebi.pride.archive.repo.project.Project;
import uk.ac.ebi.pride.archive.repo.project.ProjectRepository;
import uk.ac.ebi.pride.archive.repo.util.ObjectMapper;

import java.util.Collection;
import java.util.List;

/**
 * @author Rui Wang
 * @author Jose A. Dianes
 * @version $Id$
 *          <p/>
 */
@Repository
@Transactional(readOnly = true)
public class FileServiceImpl implements FileService {
    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    private ProjectFileRepository projectFileRepository;

    private ProjectRepository projectRepository;

    private AssayRepository assayRepository;

    @Autowired
    public FileServiceImpl(ProjectFileRepository projectFileRepository,
                           ProjectRepository projectRepository,
                           AssayRepository assayRepository) {
        this.projectFileRepository = projectFileRepository;
        this.projectRepository = projectRepository;
        this.assayRepository = assayRepository;
    }

    @Override
    public FileSummary findById(Long fileId) throws FileAccessException {
        Assert.notNull(fileId, "File id cannot be empty");

        try {
            ProjectFile projectFile = projectFileRepository.findOne(fileId);

            return ObjectMapper.mapProjectFileToFileSummary(projectFile);
        } catch (Exception ex) {
            String msg = "Failed to find file by id: " + fileId;
            logger.error(msg, ex);
            throw new FileAccessException(msg, ex);
        }
    }

    @Override
    public Collection<FileSummary> findAllByProjectAccession(String projectAccession) throws FileAccessException{
        Assert.notNull(projectAccession, "Project accession cannot be null");

        try {
            Project project = projectRepository.findByAccession(projectAccession);
            List<ProjectFile> projectFiles = projectFileRepository.findAllByProjectId(project.getId());

            return ObjectMapper.mapProjectFileToFileSummaries(projectFiles);
        } catch (Exception ex) {
            String msg = "Failed to find files by project accession: " + projectAccession;
            logger.error(msg, ex);
            throw new FileAccessException(msg, ex, projectAccession, null);
        }
    }

    @Override
    public Collection<FileSummary> findAllByAssayAccession(String assayAccession) throws FileAccessException{
        Assert.notNull(assayAccession, "Assay accession cannot be null");

        try {
            Assay assay = assayRepository.findByAccession(assayAccession);
            List<ProjectFile> projectFiles = projectFileRepository.findAllByAssayId(assay.getId());

            return ObjectMapper.mapProjectFileToFileSummaries(projectFiles);
        } catch (Exception ex) {
            String msg = "Failed to find files by assay accession: " + assayAccession;
            logger.error(msg, ex);
            throw new FileAccessException(msg, ex, null, assayAccession);
        }
    }

    @Override
    public Collection<FileSummary> findAllByProjectId(Long projectId) throws FileAccessException{
        Assert.notNull(projectId, "Project id cannot be empty");

        try {
            List<ProjectFile> projectFiles = projectFileRepository.findAllByProjectId(projectId);

            return ObjectMapper.mapProjectFileToFileSummaries(projectFiles);
        } catch (Exception ex) {
            String msg = "Failed to find files by project id: " + projectId;
            logger.error(msg, ex);
            throw new FileAccessException(msg, ex);
        }
    }

    @Override
    public Collection<FileSummary> findAllByAssayId(Long assayId) throws FileAccessException{
        Assert.notNull(assayId, "Assay id cannot be empty");

        try {
            List<ProjectFile> projectFiles = projectFileRepository.findAllByAssayId(assayId);

            return ObjectMapper.mapProjectFileToFileSummaries(projectFiles);
        } catch (Exception ex) {
            String msg = "Failed to find files by assay id: " + assayId;
            logger.error(msg, ex);
            throw new FileAccessException(msg, ex);
        }
    }
}