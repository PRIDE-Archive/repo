package uk.ac.ebi.pride.archive.repo.services.project;

import java.util.Collection;
import java.util.List;

/**
 * @author Rui Wang
 * @version $Id$
 */
public interface ProjectService {

  ProjectSummary findById(Long projectId) throws ProjectAccessException;

  ProjectSummary findByAccession(String accession) throws ProjectAccessException;

  List<ProjectSummary> findFilteredBySubmitterIdAndIsPublic(Long submitterId, Boolean isPublic) throws ProjectAccessException;

  List<ProjectSummary> findFilteredByReviewer(String user_aap_ref) throws ProjectAccessException;

  Collection<ProjectSummary> findAllBySubmitterId(Long submitterId) throws ProjectAccessException;

  List<List<String>> findMonthlySubmissions();

  List<String> findAllAccessionsChanged();

}
