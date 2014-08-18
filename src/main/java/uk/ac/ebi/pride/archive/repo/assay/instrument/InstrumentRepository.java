package uk.ac.ebi.pride.archive.repo.assay.instrument;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Jose A. Dianes
 * @version $Id$
 */
public interface InstrumentRepository extends CrudRepository<Instrument, Long> {

    List<Instrument> findAllByAssayId(Long assayId);

}
