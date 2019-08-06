package uk.ac.ebi.pride.archive.repo.services.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.ac.ebi.pride.archive.dataprovider.utils.TitleConstants;
import uk.ac.ebi.pride.archive.dataprovider.utils.RoleConstants;
import uk.ac.ebi.pride.archive.dataprovider.user.UserProvider;
import uk.ac.ebi.pride.archive.repo.util.CollectionUtils;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Rui Wang
 * @version $Id$
 */
public class UserSummary implements UserProvider {

  private final Set<RoleConstants> userAuthorities = new HashSet<>();
  private Long id;
  private String email;

  @JsonIgnore
  private String password;

  private TitleConstants title;
  private String firstName;
  private String lastName;
  private String affiliation;
  private Date createAt;
  private Date updateAt;
  private String country;
  private String orcid;
  private Boolean acceptedTermsOfUse;
  private Date acceptedTermsOfUseAt;
  private String userRef;

  public UserSummary() {}

  public UserSummary(UserSummary user) {
    this.setId(user.getId());
    this.setEmail(user.getEmail());
    this.setPassword(user.getPassword());
    this.setTitle(user.getTitle());
    this.setFirstName(user.getFirstName());
    this.setLastName(user.getLastName());
    this.setAffiliation(user.getAffiliation());
    this.setCreateAt(user.getCreateAt());
    this.setUpdateAt(user.getUpdateAt());
    this.setUserAuthorities(new HashSet<>(user.getUserAuthorities()));
    this.setCountry(user.getCountry());
    this.setOrcid(user.getOrcid());
    this.setAcceptedTermsOfUse(user.getAcceptedTermsOfUse());
    this.setAcceptedTermsOfUseAt(user.getAcceptedTermsOfUseAt());
    this.setUserRef(user.getUserRef());
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
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

  public Set<RoleConstants> getUserAuthorities() {
    return userAuthorities;
  }

  public void setUserAuthorities(Set<RoleConstants> userAuthorities) {
    CollectionUtils.replaceValuesInCollection(userAuthorities, this.userAuthorities);
  }

  public Date getCreateAt() {
    return createAt;
  }

  public void setCreateAt(Date createAt) {
    this.createAt = createAt;
  }

  public Date getUpdateAt() {
    return updateAt;
  }

  public void setUpdateAt(Date updateAt) {
    this.updateAt = updateAt;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getOrcid() {
    return orcid;
  }

  public void setOrcid(String orcid) {
    this.orcid = orcid;
  }

  public String getUserRef() {
    return userRef;
  }

  public void setUserRef(String userRef) {
    this.userRef = userRef;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof UserSummary)) return false;
    UserSummary that = (UserSummary) o;
    if (affiliation != null ? !affiliation.equals(that.affiliation) : that.affiliation != null)
      return false;
    if (createAt != null ? !createAt.equals(that.createAt) : that.createAt != null) return false;
    if (email != null ? !email.equals(that.email) : that.email != null) return false;
    if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null)
      return false;
    if (id != null ? !id.equals(that.id) : that.id != null) return false;
    if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
    if (password != null ? !password.equals(that.password) : that.password != null) return false;
    if (title != that.title) return false;
    if (updateAt != null ? !updateAt.equals(that.updateAt) : that.updateAt != null) return false;
    if (country != null ? !country.equals(that.country) : that.country != null) return false;
    if (orcid != null ? !orcid.equals(that.orcid) : that.orcid != null) return false;
    if (userRef != null ? !userRef.equals(that.userRef) : that.userRef != null) return false;
    if (acceptedTermsOfUseAt != null
            ? !acceptedTermsOfUseAt.equals(that.acceptedTermsOfUseAt)
            : that.acceptedTermsOfUseAt != null) return false;
    return acceptedTermsOfUse != null
            ? acceptedTermsOfUse.equals(that.acceptedTermsOfUse)
            : that.acceptedTermsOfUse == null;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (email != null ? email.hashCode() : 0);
    result = 31 * result + (password != null ? password.hashCode() : 0);
    result = 31 * result + (title != null ? title.hashCode() : 0);
    result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
    result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
    result = 31 * result + (affiliation != null ? affiliation.hashCode() : 0);
    result = 31 * result + (createAt != null ? createAt.hashCode() : 0);
    result = 31 * result + (updateAt != null ? updateAt.hashCode() : 0);
    result = 31 * result + (country != null ? country.hashCode() : 0);
    result = 31 * result + (orcid != null ? orcid.hashCode() : 0);
    result = 31 * result + (userRef != null ? userRef.hashCode() : 0);
    result = 31 * result + (acceptedTermsOfUse != null ? acceptedTermsOfUse.hashCode() : 0);
    result = 31 * result + (acceptedTermsOfUseAt != null ? acceptedTermsOfUseAt.hashCode() : 0);
    return result;
  }

  /**
   * Sets new acceptedTermsOfUseAt.
   *
   * @param acceptedTermsOfUseAt New value of acceptedTermsOfUseAt.
   */
  public void setAcceptedTermsOfUseAt(Date acceptedTermsOfUseAt) {
    this.acceptedTermsOfUseAt = acceptedTermsOfUseAt;
  }

  /**
   * Gets acceptedTermsOfUseAt.
   *
   * @return Value of acceptedTermsOfUseAt.
   */
  public Date getAcceptedTermsOfUseAt() {
    return acceptedTermsOfUseAt;
  }

  /**
   * Gets acceptedTermsOfUse.
   *
   * @return Value of acceptedTermsOfUse.
   */
  public Boolean getAcceptedTermsOfUse() {
    return acceptedTermsOfUse;
  }

  /**
   * Sets new acceptedTermsOfUse.
   *
   * @param acceptedTermsOfUse New value of acceptedTermsOfUse.
   */
  public void setAcceptedTermsOfUse(Boolean acceptedTermsOfUse) {
    this.acceptedTermsOfUse = acceptedTermsOfUse;
  }
}
