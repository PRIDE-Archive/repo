package uk.ac.ebi.pride.archive.repo.services.user;

import uk.ac.ebi.pride.archive.repo.services.project.ProjectSummary;

import java.util.List;

/**
 * @author Rui Wang
 * @version $Id$
 * @see UserSummary
 */
public interface UserService {

  /**
   * Sign up a new user
   *
   * @param user user details, this should contain all the required details
   * @return the return object is a {@code UserSummary} containing only user id, and createAt date
   */
  UserSummary signUp(UserSummary user) throws UserModificationException;

  /**
   * Sign up a new user
   *
   * @param user user details, this should contain all the required details
   * @return the return object is a {@code UserSummary} containing user reference from AAP if
   * creation of user was successfull
   */
  UserSummary registerWithAAP(UserSummary user) throws UserModificationException;

  /**
   * Reset the password of a user by using the email address
   *
   * @param email user's email
   * @return  the return object is a {@code UserSummary} containing only user id,
   * the new password in plain text, and updateAt date
   */
  UserSummary resetPassword(String email) throws UserModificationException;

  /**
   * Update the details of a user
   *
   * @param originalUser original user details
   * @param updatedUser updated user details
   */
  void update(UserSummary originalUser, UserSummary updatedUser) throws UserModificationException;

  /**
   * Check whether an email has already been used by another user
   *
   * @param email email to check
   * @return true indicates emails already in use, false means email can be used to create a new
   *     user
   */
  boolean isEmailedInUse(String email) throws UserAccessException;

  /**
   * Attempt to login as an already registered user The password should be passed in as plain text
   */
  UserSummary login(String email, String passwordPlainText) throws UserAccessException;

  /** Get user details of a given user id */
  UserSummary findById(Long userId) throws UserAccessException;

  /** Get user details of a given user email without the password */
  UserSummary findByEmail(String email) throws UserAccessException;

  /** Get all projects that a given user has access permission */
  List<ProjectSummary> findAllProjectsById(Long userId) throws UserAccessException;
}
