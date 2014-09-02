package uk.ac.ebi.pride.archive.repo.assay;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Jose A. Dianes
 * @version $Id$
 */
public interface AssayRepository extends CrudRepository<Assay, Long> {

    List<Assay> findAllByProjectId(Long projectId);

    Long countByProjectId(Long projectId);

    Assay findByAccession(String accession);


}
