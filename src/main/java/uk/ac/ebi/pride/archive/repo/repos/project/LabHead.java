package uk.ac.ebi.pride.archive.repo.repos.project;

import com.fasterxml.jackson.annotation.JsonBackReference;
import uk.ac.ebi.pride.archive.dataprovider.user.ContactProvider;
import uk.ac.ebi.pride.archive.dataprovider.utils.TitleConstants;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Jose A. Dianes
 * @version $Id$
 */
@Entity
@Table(name = "lab_head")
@SequenceGenerator(name = "LabHeadSequence", sequenceName = "labHeadSequence", allocationSize = 100)
public class LabHead implements ContactProvider {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LabHeadSequence")
  @Column(name = "lab_head_pk")
  private Long id;

  @JsonBackReference
  @ManyToOne
  @JoinColumn(name = "project_fk", nullable = false)
  private Project project;

  @NotNull
  @Enumerated(EnumType.STRING)
  private TitleConstants title;

  @NotNull
  @Column(name = "first_name")
  private String firstName;

  @NotNull
  @Column(name = "last_name")
  private String lastName;

  @NotNull private String affiliation;

  @NotNull private String email;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public TitleConstants getTitle() {
    return title;
  }

  @Override
  public String getName() {
    return null;
  }

  public void setTitle(TitleConstants title) {
    this.title = title;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getAffiliation() {
    return affiliation;
  }

  public void setAffiliation(String affiliation) {
    this.affiliation = affiliation;
  }

  public String getEmail() {
    return email;
  }

  @Override
  public String getCountry() {
    return null;
  }

  @Override
  public String getOrcid() {
    return null;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
