package uk.ac.ebi.pride.archive.repo.project;

import uk.ac.ebi.pride.archive.dataprovider.reference.ReferenceProvider;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Jose A. Dianes
 * @version $Id$
 */
@Entity
@Table( name = "reference" )
@SequenceGenerator(name="ReferenceSequence", sequenceName="referenceSequence", allocationSize=100)
public class Reference implements ReferenceProvider {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ReferenceSequence")
    @Column( name = "reference_pk" )
    private Long id;

    @Column( name = "pubmed_id" )
    private int pubmedId;

    @Column( name = "reference_line" )
    private String referenceLine;

    private String doi;

    @NotNull
    @ManyToOne
    @JoinColumn(name="project_fk", nullable = false)
    private Project project;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPubmedId() {
        return pubmedId;
    }

    public void setPubmedId(int pubmedId) {
        this.pubmedId = pubmedId;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getReferenceLine() {
        return referenceLine;
    }

    public void setReferenceLine(String referenceLine) {
        this.referenceLine = referenceLine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reference)) return false;

        Reference reference = (Reference) o;

        if (pubmedId != reference.pubmedId) return false;
        if (doi != null ? !doi.equals(reference.doi) : reference.doi != null) return false;
        if (referenceLine != null ? !referenceLine.equals(reference.referenceLine) : reference.referenceLine != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = pubmedId;
        result = 31 * result + (referenceLine != null ? referenceLine.hashCode() : 0);
        result = 31 * result + (doi != null ? doi.hashCode() : 0);
        return result;
    }
}
