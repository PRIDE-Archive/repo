package uk.ac.ebi.pride.archive.repo.repos.assay;

import uk.ac.ebi.pride.archive.dataprovider.param.CvParamProvider;
import uk.ac.ebi.pride.archive.repo.repos.param.CvParam;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Jose A. Dianes
 * @version $Id$
 */
@Entity
@Table(name = "assay_cvparam")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "param_type", discriminatorType = DiscriminatorType.STRING, length = 32)
@org.hibernate.annotations.DiscriminatorOptions(force = true)
@SequenceGenerator(
  name = "AssayParamSequence",
  sequenceName = "assayParamSequence",
  allocationSize = 100
)
public abstract class AssayCvParam implements CvParamProvider {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AssayParamSequence")
  @Column(name = "param_pk")
  private Long id;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "assay_fk")
  private Assay assay;

  @NotNull
  @ManyToOne(cascade = {CascadeType.MERGE})
  @JoinColumn(name = "cv_param_fk")
  private CvParam cvParam;

  @Column(name = "value")
  private String value;

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCvLabel() {
    return cvParam.getCvLabel();
  }

  public String getAccession() {
    return cvParam.getAccession();
  }

  public String getName() {
    return cvParam.getName();
  }

  public String getValue() {
    return this.value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public Assay getAssay() {
    return assay;
  }

  public void setAssay(Assay assay) {
    this.assay = assay;
  }

  public CvParam getCvParam() {
    return cvParam;
  }

  public void setCvParam(CvParam cvParam) {
    this.cvParam = cvParam;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof AssayCvParam)) return false;

    AssayCvParam that = (AssayCvParam) o;

    if (cvParam != null ? !cvParam.equals(that.cvParam) : that.cvParam != null) return false;
    return value != null ? value.equals(that.value) : that.value == null;
  }

  @Override
  public int hashCode() {
    int result = cvParam != null ? cvParam.hashCode() : 0;
    result = 31 * result + (value != null ? value.hashCode() : 0);
    return result;
  }
}
