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
 *
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
            setCreationAndUpdateDate(user);

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

        // can only create submitter
        Set<UserAuthority> authorities = new HashSet<UserAuthority>();
        authorities.add(UserAuthority.SUBMITTER);
        prideUser.setUserAuthorities(authorities);

        return prideUser;
    }

    private void setCreationAndUpdateDate(User user) {
        Date currentDate = Calendar.getInstance().getTime();
        user.setCreateAt(currentDate);
        user.setUpdateAt(currentDate);
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

            // reset password
            String newPassword = PasswordUtilities.generatePassword();
            user.setPassword(newPassword);
            userRepository.save(user);

            return hideUserDetailsForPasswordReset(user, newPassword);
        } catch (Exception ex) {
            String msg = "Failed to reset password for user: " + email;
            logger.error(msg, ex);
            throw new UserModificationException(msg, ex, email);
        }
    }

    private UserSummary hideUserDetailsForPasswordReset(User user, String newPassword) {

        UserSummary userSummary = ObjectMapper.mapUserToUserSummary(user);
        userSummary.setPassword(newPassword);

        return userSummary;
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
    public void update(UserSummary originalUser, UserSummary updatedUser) throws UserModificationException {
        Assert.notNull(originalUser, "User to update cannot be null");
        Assert.notNull(updatedUser, "User to update cannot be null");

        try {
            updateUser(originalUser, updatedUser);
            changeUpdateDate(originalUser);

            User user = ObjectMapper.mapUserSummaryToUser(originalUser);
            userRepository.save(user);
        } catch (Exception ex) {
            String msg = "Failed to update user detail, user email: " + originalUser.getEmail();
            logger.error(msg, ex);
            throw new UserAccessException(msg, ex, originalUser.getEmail());
        }
    }

    private void updateUser(UserSummary prideUser, UserSummary user) {
        if (user.getEmail() != null) {
            prideUser.setEmail(user.getEmail());
        }
        if (user.getPassword() != null) {
            prideUser.setPassword(user.getPassword());
        }
        if (user.getTitle() != null) {
            prideUser.setTitle(user.getTitle());
        }
        if (user.getFirstName() != null) {
            prideUser.setFirstName(user.getFirstName());
        }
        if (user.getLastName() != null) {
            prideUser.setLastName(user.getLastName());
        }
        if (user.getAffiliation() != null) {
            prideUser.setAffiliation(user.getAffiliation());
        }
        if (user.getCountry() != null) {
            prideUser.setCountry(user.getCountry());
        }
        if (user.getOrcid() != null) {
            prideUser.setOrcid(user.getOrcid());
        }
    }

    private void changeUpdateDate(UserSummary user) {
        Date currentDate = Calendar.getInstance().getTime();
        user.setUpdateAt(currentDate);
    }

    @Override
    public List<ProjectSummary> findAllProjectsById(Long userId) throws UserAccessException {
        List<ProjectSummary> projectSummaries = new ArrayList<ProjectSummary>();
        List<Project> ownedProjects = projectRepository.findAllBySubmitterId(userId); // find the projects owned by the user
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
