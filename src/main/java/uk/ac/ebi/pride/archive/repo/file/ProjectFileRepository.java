package uk.ac.ebi.pride.archive.repo.file;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Jose A. Dianes
 * @version $Id$
 */
public interface ProjectFileRepository extends CrudRepository<ProjectFile, Long> {

    List<ProjectFile> findAllByProjectId(Long projectId);

    List<ProjectFile> findAllByAssayId(Long assayId);

}
