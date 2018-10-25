package uk.ac.ebi.pride.archive.repo.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.pride.archive.repo.project.ProjectRepository;
import uk.ac.ebi.pride.archive.repo.user.UserAAP;
import uk.ac.ebi.pride.archive.repo.user.UserRepository;
import uk.ac.ebi.pride.archive.repo.user.service.url.UserWebServiceUrl;
import uk.ac.ebi.pride.web.util.template.SecureRestTemplateFactory;

/**
 * @author Rui Wang
 * @version $Id$
 */
@Repository
@Transactional(readOnly = true)
public class UserServiceWebServiceImpl extends UserServiceImpl {
  private static final Logger logger = LoggerFactory.getLogger(UserServiceWebServiceImpl.class);

  private RestTemplate restTemplate;
  private UserWebServiceUrl userWebServiceUrl;

  @Autowired
  public UserServiceWebServiceImpl(
      UserRepository userRepository,
      ProjectRepository projectRepository,
      UserWebServiceUrl userWebServiceUrl) {
    super(userRepository, projectRepository);
    this.restTemplate = new RestTemplate();
    this.userWebServiceUrl = userWebServiceUrl;
  }

  @Override
  public UserSummary signUp(UserSummary userSummary) throws UserModificationException {
    try {
      userSummary = registerWithAAP(userSummary);
      return restTemplate.postForObject(
          userWebServiceUrl.getSignUpUrl(), userSummary, UserSummary.class);
    } catch (Exception e) {
      String email = userSummary.getEmail();
      String msg = "Failed to query web service to create a new user: " + email;
      logger.error(msg, e);
      throw new UserModificationException(msg, e, email);
    }
  }

  @Override
  public UserSummary registerWithAAP(UserSummary userSummary) throws UserModificationException {
    try {
      UserAAP userAAP = new UserAAP();
      userAAP.setEmail(userSummary.getEmail());
      userAAP.setUsername(userSummary.getEmail());//email is the username for pride hence using same for AAP
      userAAP.setPassword(userSummary.getPassword());
      userAAP.setName(userSummary.getFirstName()+" "+userSummary.getLastName());
      userAAP.setOrganization(userSummary.getAffiliation().substring(0, userSummary.getAffiliation().length()<=255?userSummary.getAffiliation().length():255));//AAP limits org size to 255bytes

      String userRef = restTemplate.postForObject(
              userWebServiceUrl.getAapRegisterUrl(), userAAP, String.class);

      userSummary.setUserRef(userRef);
      return userSummary;
    } catch (Exception e) {
      String email = userSummary.getEmail();
      String msg = "Failed to query web service to create a new user: " + email;
      logger.error(msg, e);
      throw new UserModificationException(msg, e, email);
    }
  }

  @Override
  public UserSummary resetPassword(String email) throws UserModificationException {
    UserSummary userSummary = new UserSummary();
    userSummary.setEmail(email);

    try {
      return restTemplate.postForObject(
          userWebServiceUrl.getPasswordResetUrl(), userSummary, UserSummary.class);
    } catch (Exception e) {
      String msg =
          "Failed to query web service to reset password for user: " + userSummary.getEmail();
      logger.error(msg, e);
      throw new UserModificationException(msg, e, email);
    }
  }

  /** Please note: original user's password should be plain text instead of hashed version */
  @Override
  public void update(UserSummary originalUserSummary, UserSummary updatedUserSummary)
      throws UserModificationException {
    String updateUserRestfulUrl =
        userWebServiceUrl.getUpdateUrl()
            + (userWebServiceUrl.getUpdateUrl().endsWith("/") ? "" : "/")
            + "{userid}";
    String originalUserEmail = originalUserSummary.getEmail();
    try {
      RestTemplate newRestTemplate =
          SecureRestTemplateFactory.getTemplate(
              originalUserEmail, originalUserSummary.getPassword());
      newRestTemplate.put(
          updateUserRestfulUrl, new UserSummary(updatedUserSummary), originalUserSummary.getId());
    } catch (Exception e) {
      String msg = "Failed to query web service to update details for user: " + originalUserEmail;
      logger.error(msg, e);
      throw new UserModificationException(msg, e, originalUserEmail);
    }
  }
}
