package uk.ac.ebi.pride.archive.repo.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import uk.ac.ebi.pride.archive.dataprovider.person.UserAuthority;
import uk.ac.ebi.pride.archive.repo.project.Project;
import uk.ac.ebi.pride.archive.repo.project.ProjectRepository;
import uk.ac.ebi.pride.archive.repo.project.service.ProjectSummary;
import uk.ac.ebi.pride.archive.repo.user.PasswordUtilities;
import uk.ac.ebi.pride.archive.repo.user.User;
import uk.ac.ebi.pride.archive.repo.user.UserRepository;
import uk.ac.ebi.pride.archive.repo.util.ObjectMapper;

import java.util.*;

/**
 * @author Rui Wang
 * @author Jose A. Dianes
 * @version $Id$
 */
@Repository
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
  private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

  private UserRepository userRepository;
  private ProjectRepository projectRepository;

  @Autowired
  public UserServiceImpl(UserRepository userRepository, ProjectRepository projectRepository) {
    this.userRepository = userRepository;
    this.projectRepository = projectRepository;
  }

  @Override
  @Transactional(readOnly = false)
  public UserSummary signUp(UserSummary userSummary) throws UserModificationException {
    Assert.notNull(userSummary, "New user cannot be empty");
    try {
      User user = mapToPersistableUser(userSummary);
      Date currentDate = Calendar.getInstance().getTime();
      user.setCreateAt(currentDate);
      user.setUpdateAt(currentDate);
      user.setAcceptedTermsOfUseAt(currentDate);
      if (userSummary.getAcceptedTermsOfUse()) {
        user.setAcceptedTermsOfUseAt(currentDate);
      }
      userRepository.save(user);
      return userSummary;
    } catch (Exception ex) {
      String email = userSummary.getEmail();
      String msg = "Failed to create a new user: " + email;
      logger.error(msg, ex);
      throw new UserModificationException(msg, ex, email);
    }
  }

  private User mapToPersistableUser(UserSummary userSummary) {
    User prideUser = new User();
    prideUser.setEmail(userSummary.getEmail());
    prideUser.setPassword(userSummary.getPassword());
    prideUser.setTitle(userSummary.getTitle());
    prideUser.setFirstName(userSummary.getFirstName());
    prideUser.setLastName(userSummary.getLastName());
    prideUser.setAffiliation(userSummary.getAffiliation());
    prideUser.setCountry(userSummary.getCountry());
    prideUser.setOrcid(userSummary.getOrcid());
    prideUser.setAcceptedTermsOfUse(userSummary.getAcceptedTermsOfUse() ? 1 : 0);
    prideUser.setAcceptedTermsOfUseAt(userSummary.getAcceptedTermsOfUseAt());
    Set<UserAuthority> authorities = new HashSet<>();
    authorities.add(UserAuthority.SUBMITTER); // can only create submitter
    prideUser.setUserAuthorities(authorities);
    return prideUser;
  }

  @Override
  public boolean isEmailedInUse(String email) throws UserAccessException {
    Assert.notNull(email, "Email cannot be empty");
    try {
      return userRepository.findByEmail(email) != null;
    } catch (Exception ex) {
      String msg = "Failed to check user existence: " + email;
      logger.error(msg, ex);
      throw new UserAccessException(msg, ex, email);
    }
  }

  @Override
  @Transactional(readOnly = false)
  public UserSummary resetPassword(String email) throws UserModificationException {
    Assert.notNull(email, "Email cannot be empty");
    try {
      User user = userRepository.findByEmail(email);
      String newPassword = PasswordUtilities.generatePassword(); // reset password
      user.setPassword(newPassword);
      userRepository.save(user);
      UserSummary userSummary = ObjectMapper.mapUserToUserSummary(user);
      userSummary.setPassword(newPassword);
      return userSummary;
    } catch (Exception ex) {
      String msg = "Failed to reset password for user: " + email;
      logger.error(msg, ex);
      throw new UserModificationException(msg, ex, email);
    }
  }

  @Override
  public UserSummary login(String email, String passwordPlainText) throws UserAccessException {
    Assert.notNull(email, "Email cannot be empty");
    Assert.notNull(passwordPlainText, "Password cannot be empty");
    try {
      User user = userRepository.findByEmail(email);
      if (PasswordUtilities.matches(passwordPlainText, user.getPassword())) {
        return ObjectMapper.mapUserToUserSummary(user);
      } else {
        String msg = "Failed to login as user: " + email;
        logger.error(msg);
        throw new UserAccessException(msg);
      }
    } catch (Exception ex) {
      String msg = "Failed to login as user: " + email;
      logger.error(msg, ex);
      throw new UserAccessException(msg, ex, email);
    }
  }

  @Override
  public UserSummary findById(Long userId) throws UserAccessException {
    Assert.notNull(userId, "User id cannot be null");
    try {
      User user = userRepository.findOne(userId);
      return ObjectMapper.mapUserToUserSummary(user);
    } catch (Exception ex) {
      String msg = "Failed to find user by user id: " + userId;
      logger.error(msg, ex);
      throw new UserAccessException(msg, ex);
    }
  }

  @Override
  public UserSummary findByEmail(String email) throws UserAccessException {
    Assert.notNull(email, "Email cannot be null");
    try {
      User user = userRepository.findByEmail(email);
      return ObjectMapper.mapUserToUserSummary(user);
    } catch (Exception ex) {
      String msg = "Failed to find user by email: " + email;
      logger.error(msg, ex);
      throw new UserAccessException(msg, ex, email);
    }
  }

  @Override
  @Transactional(readOnly = false)
  public void update(UserSummary originalUserSummary, UserSummary updatedUserSummary)
      throws UserModificationException {
    Assert.notNull(originalUserSummary, "Original user summary cannot be null");
    Assert.notNull(updatedUserSummary, "User summary to update cannot be null");
    try {
      updateOriginalUserSummary(originalUserSummary, updatedUserSummary);
      Date currentDate = Calendar.getInstance().getTime();
      originalUserSummary.setUpdateAt(currentDate);
      if (originalUserSummary.getAcceptedTermsOfUse() != null
          && originalUserSummary.getAcceptedTermsOfUse()) {
        originalUserSummary.setAcceptedTermsOfUseAt(currentDate);
      }
      User user = ObjectMapper.mapUserSummaryToUser(originalUserSummary);
      userRepository.save(user);
    } catch (Exception ex) {
      String msg = "Failed to update user detail, user email: " + originalUserSummary.getEmail();
      logger.error(msg, ex);
      throw new UserAccessException(msg, ex, originalUserSummary.getEmail());
    }
  }

  private void updateOriginalUserSummary(
      UserSummary originalUserSummary, UserSummary updatedUserSummary) {
    if (updatedUserSummary.getEmail() != null) {
      originalUserSummary.setEmail(updatedUserSummary.getEmail());
    }
    if (updatedUserSummary.getPassword() != null) {
      originalUserSummary.setPassword(updatedUserSummary.getPassword());
    }
    if (updatedUserSummary.getTitle() != null) {
      originalUserSummary.setTitle(updatedUserSummary.getTitle());
    }
    if (updatedUserSummary.getFirstName() != null) {
      originalUserSummary.setFirstName(updatedUserSummary.getFirstName());
    }
    if (updatedUserSummary.getLastName() != null) {
      originalUserSummary.setLastName(updatedUserSummary.getLastName());
    }
    if (updatedUserSummary.getAffiliation() != null) {
      originalUserSummary.setAffiliation(updatedUserSummary.getAffiliation());
    }
    if (updatedUserSummary.getCountry() != null) {
      originalUserSummary.setCountry(updatedUserSummary.getCountry());
    }
    if (updatedUserSummary.getOrcid() != null) {
      originalUserSummary.setOrcid(updatedUserSummary.getOrcid());
    }
    if (updatedUserSummary.getAcceptedTermsOfUse() != null) {
      originalUserSummary.setAcceptedTermsOfUse(updatedUserSummary.getAcceptedTermsOfUse());
    }
  }

  private void changeUpdateDate(UserSummary userSummary) {
    Date currentDate = Calendar.getInstance().getTime();
    userSummary.setUpdateAt(currentDate);
    if (userSummary.getAcceptedTermsOfUse() != null && userSummary.getAcceptedTermsOfUse()) {
      userSummary.setAcceptedTermsOfUseAt(currentDate);
    }
  }

  @Override
  public List<ProjectSummary> findAllProjectsById(Long userId) throws UserAccessException {
    List<ProjectSummary> projectSummaries = new ArrayList<>();
    List<Project> ownedProjects =
        projectRepository.findAllBySubmitterId(userId); // find the projects owned by the user
    for (Project ownedProject : ownedProjects) {
      projectSummaries.add(ObjectMapper.mapProjectToProjectSummary(ownedProject));
    }
    List<Project> accessibleProjects = userRepository.findAllProjectsById(userId);
    for (Project accessibleProject : accessibleProjects) {
      projectSummaries.add(ObjectMapper.mapProjectToProjectSummary(accessibleProject));
    }
    return projectSummaries;
  }
}
