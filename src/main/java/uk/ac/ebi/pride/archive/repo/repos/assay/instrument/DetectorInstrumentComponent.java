package uk.ac.ebi.pride.archive.repo.repos.assay.instrument;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author Jose A. Dianes
 * @version $Id$
 */
@Entity
@DiscriminatorValue("DETECTOR")
public class DetectorInstrumentComponent extends InstrumentComponent {

}
