package uk.ac.ebi.pride.archive.repo.project;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author Rui Wang
 * @version $Id$
 */
@Entity
@DiscriminatorValue("PROJECT")
public class ProjectGroupUserParam extends ProjectUserParam{
}
