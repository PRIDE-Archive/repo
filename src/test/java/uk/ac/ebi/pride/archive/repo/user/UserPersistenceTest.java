package uk.ac.ebi.pride.archive.repo.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.ebi.pride.archive.dataprovider.utils.TitleConstants;
import uk.ac.ebi.pride.archive.dataprovider.utils.RoleConstants;
import uk.ac.ebi.pride.archive.repo.config.ArchiveOracleConfig;
import uk.ac.ebi.pride.archive.repo.repos.project.Project;
import uk.ac.ebi.pride.archive.repo.repos.user.User;
import uk.ac.ebi.pride.archive.repo.repos.user.UserRepository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author Jose A. Dianes
 * @version $Id$
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ArchiveOracleConfig.class})
public class UserPersistenceTest {

  private static final long USER_1_ID = 11111;
  private static final String ANOTHER_USER_PASSWORD = "password";
  private static final String ANOTHER_USER_EMAIL = "another.user@test.ebi.ac.uk";
  private static final TitleConstants ANOTHER_USER_TITLE = TitleConstants.Dr;
  private static final String ANOTHER_USER_FIRST_NAME = "Lucas";
  private static final String ANOTHER_USER_LAST_NAME = "Grijander";
  private static final String ANOTHER_USER_AFFILIATION = "EMBL-EBI";
  private static final String USER_1_PASSWORD =
      "$2a$10$Zf1AEQW1Blw7e4Dt.y3Bne5flAXs.R69AbBdCqcv0h8Cv7Y6Ycatq";
  private static final String USER_1_EMAIL = "john.smith@dummy.ebi.com";
  private static final TitleConstants USER_1_TITLE = TitleConstants.Mr;
  private static final String USER_1_FIRST_NAME = "john";
  private static final String USER_1_LAST_NAME = "smith";
  private static final String USER_1_AFFILIATION = "EBI";
  private static final String USER_1_CREATE_AT = "2010-04-29";
  private static final String USER_1_UPDATE_AT = "2010-04-30";
  private static final String USER_1_COUNTRY = "UK";
  private static final String USER_1_ORCID = "0000-1111-2222-3333";
  private static final RoleConstants AUTH_1_AUTHORITY = RoleConstants.SUBMITTER;
  private static final String PROJECT_1_ACCESSION = "PXD00001";

  @Autowired private UserRepository userRepository;

  @Test
  public void testSaveAndGet() throws Exception {
    User user = new User();
    user.setPassword(ANOTHER_USER_PASSWORD);
    user.setEmail(ANOTHER_USER_EMAIL);
    user.setTitle(ANOTHER_USER_TITLE);
    user.setFirstName(ANOTHER_USER_FIRST_NAME);
    user.setLastName(ANOTHER_USER_LAST_NAME);
    user.setAffiliation(ANOTHER_USER_AFFILIATION);
    Date creationDate = Calendar.getInstance().getTime();
    user.setCreateAt(creationDate);
    Date updateDate = Calendar.getInstance().getTime();
    user.setUpdateAt(updateDate);
    user.setCountry(USER_1_COUNTRY);
    user.setOrcid(USER_1_ORCID);

    user.setUserRef(null);
    user.setAcceptedTermsOfUse(1);
    user.setAcceptedTermsOfUseAt(updateDate);

    Set<RoleConstants> auths = new HashSet<>();
    auths.add(AUTH_1_AUTHORITY);
    user.setUserAuthorities(auths);
    userRepository.save(user); // id will be autogenerated on save
    User other = userRepository.findById(user.getId()).get();
    assertEquals(other.getId(), user.getId());
    assertEquals(other.getPassword(), user.getPassword());
    assertEquals(other.getEmail(), user.getEmail());
    assertEquals(other.getTitle(), user.getTitle());
    assertEquals(other.getFirstName(), user.getFirstName());
    assertEquals(other.getLastName(), user.getLastName());
    assertEquals(other.getAffiliation(), user.getAffiliation());
    assertEquals(other.getCountry(), user.getCountry());
    assertEquals(other.getOrcid(), user.getOrcid());
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    assertEquals(df.format(other.getCreateAt()), df.format(user.getCreateAt()));
    assertEquals(df.format(other.getUpdateAt()), df.format(user.getUpdateAt()));
    RoleConstants otherAuth = other.getUserAuthorities().iterator().next(); // check authorities
    assertNotNull(otherAuth);
    assertThat(otherAuth, is(AUTH_1_AUTHORITY));
    userRepository.delete(other); // delete user
  }

  @Test
  public void testGetById() throws Exception {
    User user = userRepository.findById(USER_1_ID).get();
    assertNotNull(user);
    checkUser1InDb(user);
  }

  private void checkUser1InDb(User user) {
    assertThat(user.getId(), is(USER_1_ID));
    assertThat(user.getPassword(), is(USER_1_PASSWORD));
    assertThat(user.getEmail(), is(USER_1_EMAIL));
    assertThat(user.getTitle(), is(USER_1_TITLE));
    assertThat(user.getFirstName(), is(USER_1_FIRST_NAME));
    assertThat(user.getLastName(), is(USER_1_LAST_NAME));
    assertThat(user.getAffiliation(), is(USER_1_AFFILIATION));
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    assertThat(df.format(user.getCreateAt()), is(USER_1_CREATE_AT));
    assertThat(df.format(user.getUpdateAt()), is(USER_1_UPDATE_AT));
    assertThat(user.getCountry(), is(USER_1_COUNTRY));
    assertThat(user.getOrcid(), is(USER_1_ORCID));
    RoleConstants auth = user.getUserAuthorities().iterator().next();
    assertNotNull(auth);
    assertThat(auth, is(AUTH_1_AUTHORITY));
  }

  @Test
  public void testGetAllProjectsById() throws Exception {
    List<Project> projects = userRepository.findAllProjectsById(USER_1_ID);
    assertNotNull(projects);
    Project project = projects.get(0);
    assertThat(project.getAccession(), is(PROJECT_1_ACCESSION));
  }
}
