package uk.ac.ebi.pride.archive.repo.repos.assay.instrument;

import uk.ac.ebi.pride.archive.dataprovider.param.CvParamProvider;

/**
 * @author Jose A. Dianes
 * @version $Id$
 */
public class InstrumentModel implements CvParamProvider {

  private Long id;

  private String name;

  private String value;

  private String cvLabel;

  private String accession;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getCvLabel() {
    return this.cvLabel;
  }

  public void setCvLabel(String cvLabel) {
    this.cvLabel = cvLabel;
  }

  public String getAccession() {
    return this.accession;
  }

  public void setAccession(String accession) {
    this.accession = accession;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof InstrumentModel)) return false;

    InstrumentModel that = (InstrumentModel) o;

    if (accession != null ? !accession.equals(that.accession) : that.accession != null)
      return false;
    if (cvLabel != null ? !cvLabel.equals(that.cvLabel) : that.cvLabel != null) return false;
    if (name != null ? !name.equals(that.name) : that.name != null) return false;
    return value != null ? value.equals(that.value) : that.value == null;
  }

  @Override
  public int hashCode() {
    int result = name != null ? name.hashCode() : 0;
    result = 31 * result + (value != null ? value.hashCode() : 0);
    result = 31 * result + (cvLabel != null ? cvLabel.hashCode() : 0);
    result = 31 * result + (accession != null ? accession.hashCode() : 0);
    return result;
  }
}
