package uk.ac.ebi.pride.archive.repo.assay.instrument;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author Jose A. Dianes
 * @version $Id$
 */
@Entity
@DiscriminatorValue("ANALYZER")
public class AnalyzerInstrumentComponent extends InstrumentComponent {

}
