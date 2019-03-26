package uk.ac.ebi.pride.archive.repo.repos.project;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author Jose A. Dianes
 * @version $Id$
 */
@Transactional
@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {

  Project findByAccession(String projectAccession);

  @Query("select p from Project p where p.submitter.id = ?1")
  List<Project> findAllBySubmitterId(Long submitterId);

  @Query("select p.accession from Project p order by p.submissionDate")
  List<String> findAllAccessions();

  @Query("select p.accession from Project p where p.changed = 1")
  List<String> findAllAccessionsChanged();

  @Query(value="select to_char(trunc(submission_date,'MM'),'MON-YY') MONTH ,count(*) from Project group by trunc(submission_date,'MM') order by trunc(submission_date,'MM') DESC",nativeQuery = true)
  List<List<String>> findMonthlySubmissions();
}
