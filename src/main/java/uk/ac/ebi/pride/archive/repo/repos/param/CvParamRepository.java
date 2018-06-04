package uk.ac.ebi.pride.archive.repo.repos.param;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Jose A. Dianes
 * @version $Id$
 */
@Repository
public interface CvParamRepository extends CrudRepository<CvParam, Long> {

  CvParam findByAccession(String accession);
}
