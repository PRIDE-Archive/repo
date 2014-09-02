package uk.ac.ebi.pride.archive.repo.assay.service;

import java.util.Collection;

/**
 * @author Jose A. Dianes
 * @author Rui Wang
 * @version $Id$
 */
public interface AssayService {

    AssaySummary findById(Long assayId) throws AssayAccessException;

    AssaySummary findByAccession(String assayAccession) throws AssayAccessException;

    Collection<AssaySummary> findAllByProjectAccession(String projectAccession) throws AssayAccessException;

    Long countByProjectAccession(String projectAccession);
}
