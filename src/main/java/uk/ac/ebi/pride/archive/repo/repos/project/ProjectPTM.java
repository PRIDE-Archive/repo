package uk.ac.ebi.pride.archive.repo.repos.project;

import com.fasterxml.jackson.annotation.JsonBackReference;
import uk.ac.ebi.pride.archive.dataprovider.param.CvParamProvider;
import uk.ac.ebi.pride.archive.repo.repos.param.CvParam;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Jose A. Dianes
 * @version $Id$
 */
@Entity
@Table(name = "project_ptm")
@SequenceGenerator(
  name = "ProjectPTMSequence",
  sequenceName = "projectPtmSequence",
  allocationSize = 100
)
public class ProjectPTM implements CvParamProvider {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ProjectPTMSequence")
  @Column(name = "project_ptm_pk")
  private Long id;

  @JsonBackReference
  @NotNull
  @ManyToOne
  @JoinColumn(name = "project_fk", nullable = false)
  private Project project;


  @NotNull
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

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
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
    return this.value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ProjectPTM)) return false;

    ProjectPTM that = (ProjectPTM) o;

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
