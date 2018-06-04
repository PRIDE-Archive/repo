package uk.ac.ebi.pride.archive.repo.repos.file;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Jose A. Dianes
 * @version $Id$
 */
@Repository
public interface ProjectFileRepository extends CrudRepository<ProjectFile, Long> {

  List<ProjectFile> findAllByProjectId(Long projectId);

  List<ProjectFile> findAllByAssayId(Long assayId);
}
