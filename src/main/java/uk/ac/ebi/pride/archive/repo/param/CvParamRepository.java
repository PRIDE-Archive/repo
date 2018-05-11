package uk.ac.ebi.pride.archive.repo.param;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Jose A. Dianes
 * @version $Id$
 */
@Repository
public interface CvParamRepository extends CrudRepository<CvParam, Long> {

    public CvParam findByAccession(String accession);
}
