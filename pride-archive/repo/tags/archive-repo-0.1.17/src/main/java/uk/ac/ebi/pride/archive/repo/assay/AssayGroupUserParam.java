package uk.ac.ebi.pride.archive.repo.assay;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author Rui Wang
 * @version $Id$
 */
@Entity
@DiscriminatorValue("ASSAY")
public class AssayGroupUserParam extends AssayUserParam{
}
