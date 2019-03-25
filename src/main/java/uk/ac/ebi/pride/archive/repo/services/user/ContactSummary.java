package uk.ac.ebi.pride.archive.repo.services.user;

import uk.ac.ebi.pride.archive.dataprovider.user.ContactProvider;
import uk.ac.ebi.pride.archive.dataprovider.utils.TitleConstants;

/**
 * @author Rui Wang
 * @version $Id$
 */
public class ContactSummary implements ContactProvider {

  private TitleConstants title;
  private String firstName;
  private String lastName;
  private String affiliation;
  private String email;
  private Long id;

  public ContactSummary() {
    this.title = TitleConstants.UNKNOWN;
  }

  public TitleConstants getTitle() {
    return title;
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

  public void setEmail(String email) {
    this.email = email;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Override
  public String getName() {
    return firstName+" "+lastName;
  }

  @Override
  public String getCountry() {
    return null;
  }

  @Override
  public String getOrcid() {
    return null;
  }

}
