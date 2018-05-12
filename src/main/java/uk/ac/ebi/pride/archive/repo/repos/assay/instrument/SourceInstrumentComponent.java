package uk.ac.ebi.pride.archive.repo.repos.assay.instrument;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author Jose A. Dianes
 * @version $Id$
 */
@Entity
@DiscriminatorValue("SOURCE")
public class SourceInstrumentComponent extends InstrumentComponent {

}
