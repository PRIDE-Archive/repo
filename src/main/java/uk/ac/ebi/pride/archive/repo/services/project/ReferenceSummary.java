package uk.ac.ebi.pride.archive.repo.services.project;

import uk.ac.ebi.pride.archive.dataprovider.reference.ReferenceProvider;

/**
 * @author Jose A. Dianes
 * @version $Id$
 */
public class ReferenceSummary implements ReferenceProvider{

    private String referenceLine;
    private int pubmedId;
    private String doi;
    private Long id;

    public String getReferenceLine() {
        return referenceLine;
    }

    public void setReferenceLine(String referenceLine) {
        this.referenceLine = referenceLine;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
