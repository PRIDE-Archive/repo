package uk.ac.ebi.pride.archive.repo.services.assay;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    Page<AssaySummary> findAllByProjectAccession(String projectAccession, Pageable pageable) throws AssayAccessException;

    Long countByProjectAccession(String projectAccession) throws AssayAccessException;
}
