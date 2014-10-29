package uk.ac.ebi.pride.archive.repo.file.service;

import java.util.Collection;

/**
 * @author Jose A. Dianes
 * @author Rui Wang
 * @version $Id$
 */
public interface FileService {

    FileSummary findById(Long fileId) throws FileAccessException;

    Collection<FileSummary> findAllByProjectAccession(String projectAccession) throws FileAccessException;

    Collection<FileSummary> findAllByAssayAccession(String assayAccession) throws FileAccessException;

    Collection<FileSummary> findAllByProjectId(Long projectId) throws FileAccessException;

    Collection<FileSummary> findAllByAssayId(Long assayId) throws FileAccessException;
}
