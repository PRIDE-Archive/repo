package uk.ac.ebi.pride.archive.repo.assay.instrument;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Jose A. Dianes
 * @version $Id$
 */
@Repository
public interface InstrumentRepository extends CrudRepository<Instrument, Long> {

    List<Instrument> findAllByAssayId(Long assayId);

}
