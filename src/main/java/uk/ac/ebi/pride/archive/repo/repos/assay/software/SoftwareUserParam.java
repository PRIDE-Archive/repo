package uk.ac.ebi.pride.archive.repo.repos.assay.software;

import uk.ac.ebi.pride.archive.dataprovider.param.ParamProvider;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Jose A. Dianes
 * @version $Id$
 */
@Entity
@Table(name = "software_userparam")
@SequenceGenerator(
  name = "SoftwareParamSequence",
  sequenceName = "softwareParamSequence",
  allocationSize = 100
)
public class SoftwareUserParam implements ParamProvider {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SoftwareParamSequence")
  @Column(name = "param_pk")
  private Long id;

  @Column(name = "param_name")
  private String name;

  private String value;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "software_fk")
  private Software software;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getValue() {
    return this.value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public Software getSoftware() {
    return software;
  }

  public void setSoftware(Software software) {
    this.software = software;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof SoftwareUserParam)) return false;

    SoftwareUserParam that = (SoftwareUserParam) o;

    if (name != null ? !name.equals(that.name) : that.name != null) return false;
    return value != null ? value.equals(that.value) : that.value == null;
  }

  @Override
  public int hashCode() {
    int result = name != null ? name.hashCode() : 0;
    result = 31 * result + (value != null ? value.hashCode() : 0);
    return result;
  }
}
