package uk.ac.ebi.pride.archive.repo.file;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.pride.archive.dataprovider.file.ProjectFileSource;
import uk.ac.ebi.pride.archive.dataprovider.file.ProjectFileType;
import uk.ac.ebi.pride.archive.repo.config.ArchiveOracleConfig;

import java.util.Collection;

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
public class FilePersistenceTest {

    @Autowired
    private ProjectFileRepository projectFileRepository;

    private long PROJECT_FILE_1_ID = 99999;
    public static final long PROJECT_ID = 11111;
    public static final long ASSAY_ID = 44444;
    public static final long FILE_SIZE = 1024;
    public static final String FILE_NAME = "file.name";
    public static final String FILE_PATH = "/path/to/file/file.name";

    @Test
    public void testSaveAndGet() throws Exception {

        ProjectFile projectFile = new ProjectFile();
        projectFile.setProjectId(PROJECT_ID);
        projectFile.setAssayId(ASSAY_ID);
        projectFile.setFileType(ProjectFileType.RESULT);
        projectFile.setFileSize(FILE_SIZE);
        projectFile.setFileName(FILE_NAME);
        projectFile.setFilePath(FILE_PATH);
        projectFile.setFileSource(ProjectFileSource.SUBMITTED);

        projectFileRepository.save(projectFile);
        //id will be set on file save
        long newId = projectFile.getId();

        ProjectFile other = projectFileRepository.findById(newId).get();
        assertEquals(other.getId(), projectFile.getId());
        assertEquals(other.getProjectId(), projectFile.getProjectId());
        assertEquals(other.getAssayId(), projectFile.getAssayId());
        assertEquals(other.getFileType(), projectFile.getFileType());
        assertEquals(other.getFileSize(), projectFile.getFileSize());
        assertEquals(other.getFileName(), projectFile.getFileName());
        assertEquals(other.getFilePath(), projectFile.getFilePath());

        // delete file
        projectFileRepository.delete(other);

    }

    @Test
    public void testFindAllByProjectId() throws Exception {
        Collection<ProjectFile> projectFiles = projectFileRepository.findAllByProjectId(PROJECT_ID);

        assertNotNull(projectFiles);
        assertEquals(projectFiles.size(), 1);

        ProjectFile projectFile = projectFiles.iterator().next();

        assertThat(projectFile.getId(), is(PROJECT_FILE_1_ID));
        assertThat(projectFile.getProjectId(), is(PROJECT_ID));
        assertThat(projectFile.getAssayId(), is(ASSAY_ID));
        assertEquals(projectFile.getFileType(), ProjectFileType.PEAK);
        assertEquals(projectFile.getFileSource(), ProjectFileSource.SUBMITTED);
        assertEquals(projectFile.getFileSize(), FILE_SIZE);
        assertEquals(projectFile.getFileName(), FILE_NAME);
        assertEquals(projectFile.getFilePath(), FILE_PATH);

    }

    @Test
    @Transactional
    public void testFindAllByAssayId() throws Exception {
        Collection<ProjectFile> projectFiles = projectFileRepository.findAllByAssayId(ASSAY_ID);

        assertNotNull(projectFiles);
        assertEquals(projectFiles.size(), 1);

        ProjectFile projectFile = projectFiles.iterator().next();

        assertThat(projectFile.getId(), is(PROJECT_FILE_1_ID));
        assertThat(projectFile.getProjectId(), is(PROJECT_ID));
        assertThat(projectFile.getAssayId(), is(ASSAY_ID));
        assertEquals(projectFile.getFileType(), ProjectFileType.PEAK);
        assertEquals(projectFile.getFileSize(), FILE_SIZE);
        assertEquals(projectFile.getFileName(), FILE_NAME);
        assertEquals(projectFile.getFilePath(), FILE_PATH);

    }

}
