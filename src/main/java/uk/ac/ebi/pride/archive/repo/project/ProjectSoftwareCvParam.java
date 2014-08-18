package uk.ac.ebi.pride.archive.repo.project;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author Jose A. Dianes
 * @version $Id$
 */
@Entity
@DiscriminatorValue("SOFTWARE")
public class ProjectSoftwareCvParam extends ProjectCvParam {
}
