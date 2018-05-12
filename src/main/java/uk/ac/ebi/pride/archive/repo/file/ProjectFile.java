package uk.ac.ebi.pride.archive.repo.file;

import uk.ac.ebi.pride.archive.dataprovider.file.ProjectFileProvider;
import uk.ac.ebi.pride.archive.dataprovider.file.ProjectFileSource;
import uk.ac.ebi.pride.archive.dataprovider.file.ProjectFileType;

import javax.persistence.*;

/**
 *
 * {@code ProjectFile} represents a data file from a project
 *
 * NOTE: there can be different file types, such as: RESULT_FILE_TYPE, SEARCH_FILE_TYPE and etc
 *
 * @author Jose A. Dianes
 * @version $Id$
 */
@Entity
@Table( name = "project_files" )
@SequenceGenerator(name="ProjectFileSequence", sequenceName="projectFileSequence", allocationSize=100)
public class ProjectFile implements ProjectFileProvider {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ProjectFileSequence")
    @Column(name = "file_pk")
    private Long id;

    @Column( name = "project_fk" )
    private Long projectId;

    @Column( name = "assay_fk")
    private Long assayId;

    @Column( name = "file_type")
    @Enumerated( EnumType.STRING )
    private ProjectFileType fileType;

    @Column( name = "file_size")
    private long fileSize;

    @Column( name = "file_name")
    private String fileName;

    @Column( name = "file_path")
    private String filePath;

    @Column( name = "file_source")
    @Enumerated( EnumType.STRING )
    private ProjectFileSource fileSource;



    /** Different project file types */

    /** Result file type, such as: PRIDE XML, mzIdentML */
    public static final int RESULT_FILE_TYPE = 1;

    /** Peak list file type, such as: mgf, pkl */
    public static final int PEAK_FILE_TYPE = 2;

    /** Search engine file type, such as: Mascot DAT */
    public static final int SEARCH_FILE_TYPE = 3;

    /** Raw MS instrument binary output file type */
    public static final int RAW_FILE_TYPE = 4;

    /** Quantification file type */
    public static final int QUANTIFICATION_FILE_TYPE = 5;

    /** Gel image file type, such as: TIFF files */
    public static final int GEL_FILE_TYPE = 6;

    /** Other submission file type*/
    public static final int OTHER_FILE_TYPE = 7;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Deprecated
    public String getFilePath() {
        return filePath;
    }

    @Deprecated
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProjectFile)) return false;

        ProjectFile that = (ProjectFile) o;

        if (fileSize != that.fileSize) return false;
        if (assayId != null ? !assayId.equals(that.assayId) : that.assayId != null) return false;
        if (!fileName.equals(that.fileName)) return false;
        if (filePath != null ? !filePath.equals(that.filePath) : that.filePath != null) return false;
        if (fileSource != that.fileSource) return false;
        if (fileType != that.fileType) return false;
        return projectId != null ? projectId.equals(that.projectId) : that.projectId == null;
    }

    @Override
    public int hashCode() {
        int result = projectId != null ? projectId.hashCode() : 0;
        result = 31 * result + (assayId != null ? assayId.hashCode() : 0);
        result = 31 * result + fileType.hashCode();
        result = 31 * result + (int) (fileSize ^ (fileSize >>> 32));
        result = 31 * result + fileName.hashCode();
        result = 31 * result + (filePath != null ? filePath.hashCode() : 0);
        result = 31 * result + fileSource.hashCode();
        return result;
    }
}
