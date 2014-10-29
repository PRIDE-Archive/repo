package uk.ac.ebi.pride.archive.repo.param;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Jose A. Dianes
 * @version $Id$
 */
public interface CvParamRepository extends CrudRepository<CvParam, Long> {

    public CvParam findByAccession(String accession);
}
