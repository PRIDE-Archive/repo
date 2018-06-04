package uk.ac.ebi.pride.archive.repo.repos.assay;

import uk.ac.ebi.pride.archive.dataprovider.param.CvParamProvider;
import uk.ac.ebi.pride.archive.repo.repos.param.CvParam;

import javax.persistence.*;

/**
 * @author Jose A. Dianes
 * @version $Id$
 */
@Entity
@Table(name = "assay_ptm")
@SequenceGenerator(
  name = "AssayPTMSequence",
  sequenceName = "assayPtmSequence",
  allocationSize = 100
)
public class AssayPTM implements CvParamProvider {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AssayPTMSequence")
  @Column(name = "assay_ptm_pk")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "assay_fk")
  private Assay assay;

  @ManyToOne(cascade = {CascadeType.MERGE})
  @JoinColumn(name = "cv_param_fk")
  private CvParam cvParam;

  private String value;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public CvParam getCvParam() {
    return cvParam;
  }

  public void setCvParam(CvParam cvParam) {
    this.cvParam = cvParam;
  }

  public Assay getAssay() {
    return assay;
  }

  public void setAssay(Assay assay) {
    this.assay = assay;
  }

  public String getCvLabel() {
    return this.cvParam.getCvLabel();
  }

  public String getAccession() {
    return this.cvParam.getAccession();
  }

  public String getName() {
    return this.cvParam.getName();
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof AssayPTM)) return false;

    AssayPTM assayPTM = (AssayPTM) o;

    if (cvParam != null ? !cvParam.equals(assayPTM.cvParam) : assayPTM.cvParam != null)
      return false;
    return value != null ? value.equals(assayPTM.value) : assayPTM.value == null;
  }

  @Override
  public int hashCode() {
    int result = cvParam != null ? cvParam.hashCode() : 0;
    result = 31 * result + (value != null ? value.hashCode() : 0);
    return result;
  }
}
