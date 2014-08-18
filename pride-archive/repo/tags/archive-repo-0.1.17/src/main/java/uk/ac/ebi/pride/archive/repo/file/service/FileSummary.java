package uk.ac.ebi.pride.archive.repo.file.service;

import uk.ac.ebi.pride.archive.dataprovider.file.ProjectFileProvider;
import uk.ac.ebi.pride.archive.dataprovider.file.ProjectFileSource;
import uk.ac.ebi.pride.archive.dataprovider.file.ProjectFileType;

/**
 * @author Jose A. Dianes
 * @version $Id$
 */
public class FileSummary implements ProjectFileProvider {

    private Long projectId;
    private Long assayId;
    private ProjectFileType fileType;
    private long fileSize;
    private ProjectFileSource fileSource;
    @Deprecated
    private String filePath;
    private String fileName;
    private Long id;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getAssayId() {
        return assayId;
    }

    public void setAssayId(Long assayId) {
        this.assayId = assayId;
    }

    public ProjectFileType getFileType() {
        return fileType;
    }

    public void setFileType(ProjectFileType fileType) {
        this.fileType = fileType;
    }

    public ProjectFileSource getFileSource() {
        return fileSource;
    }

    public void setFileSource(ProjectFileSource fileSource) {
        this.fileSource = fileSource;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    @Deprecated
    public String getFilePath() {
        return filePath;
    }

    @Deprecated
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
