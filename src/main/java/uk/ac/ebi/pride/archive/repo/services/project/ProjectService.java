package uk.ac.ebi.pride.archive.repo.services.project;

import java.util.Collection;

/**
 * @author Rui Wang
 * @version $Id$
 */
public interface ProjectService {

    ProjectSummary findById(Long projectId) throws ProjectAccessException;

    ProjectSummary findByAccession(String accession) throws ProjectAccessException;

    Collection<ProjectSummary> findAllBySubmitterId(Long submitterId) throws ProjectAccessException;
}
