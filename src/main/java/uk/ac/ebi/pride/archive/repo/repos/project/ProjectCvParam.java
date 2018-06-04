package uk.ac.ebi.pride.archive.repo.repos.project;

import uk.ac.ebi.pride.archive.dataprovider.param.CvParamProvider;
import uk.ac.ebi.pride.archive.repo.repos.param.CvParam;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Jose A. Dianes
 * @version $Id$
 *     <p>todo: should we expose cvparam
 */
@Entity
@Table(name = "project_cvparam")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "param_type", discriminatorType = DiscriminatorType.STRING, length = 32)
@org.hibernate.annotations.DiscriminatorOptions(force = true)
@SequenceGenerator(
  name = "ProjectParamSequence",
  sequenceName = "projectParamSequence",
  allocationSize = 100
)
public abstract class ProjectCvParam implements CvParamProvider {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ProjectParamSequence")
  @Column(name = "param_pk")
  private Long id;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "project_fk", nullable = false)
  private Project project;

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

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
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
    if (!(o instanceof ProjectCvParam)) return false;

    ProjectCvParam that = (ProjectCvParam) o;

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
