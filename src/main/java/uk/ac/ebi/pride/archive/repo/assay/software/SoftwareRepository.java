package uk.ac.ebi.pride.archive.repo.assay.software;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Jose A. Dianes
 * @version $Id$
 */
@Repository
public interface SoftwareRepository extends CrudRepository<Software, Long> {

    List<Software> findAllByAssayId(Long assayId);

}
