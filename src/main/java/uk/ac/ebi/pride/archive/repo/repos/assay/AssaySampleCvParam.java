package uk.ac.ebi.pride.archive.repo.repos.assay;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author Jose A. Dianes
 * @version $Id$
 */
@Entity
@DiscriminatorValue("SAMPLE")
public class AssaySampleCvParam extends AssayCvParam {

}