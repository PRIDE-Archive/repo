package uk.ac.ebi.pride.archive.repo.assay;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Jose A. Dianes
 * @version $Id$
 */
@Repository
public interface AssayRepository extends JpaRepository<Assay, Long> {

    List<Assay> findAllByProjectId(Long projectId);

    Page<Assay> findAllByProjectId(Long projectId, Pageable pageable);

    Long countByProjectId(Long projectId);

    Assay findByAccession(String accession);


}
