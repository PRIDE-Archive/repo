package uk.ac.ebi.pride.archive.repo.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.pride.archive.repo.project.ProjectRepository;
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
    public UserServiceWebServiceImpl(UserRepository userRepository,
                                     ProjectRepository projectRepository,
                                     UserWebServiceUrl userWebServiceUrl) {
        super(userRepository, projectRepository);
        this.restTemplate = new RestTemplate();
        this.userWebServiceUrl= userWebServiceUrl;
    }

    @Override
    public UserSummary signUp(UserSummary userSummary) throws UserModificationException{
        try {
            return restTemplate.postForObject(userWebServiceUrl.getSignUpUrl(), userSummary, UserSummary.class);
        } catch (Exception ex) {
            String email = userSummary.getEmail();
            String msg = "Failed to query web service to create a new user: " + email;
            logger.error(msg, ex);
            throw new UserModificationException(msg, ex, email);
        }
    }

    @Override
    public UserSummary resetPassword(String email) throws UserModificationException {
        UserSummary userSummary = new UserSummary();
        userSummary.setEmail(email);

        try {
            return restTemplate.postForObject(userWebServiceUrl.getPasswordResetUrl(), userSummary, UserSummary.class);
        } catch (Exception ex) {
            String msg = "Failed to query web service to reset password for user: " + userSummary.getEmail();
            logger.error(msg, ex);
            throw new UserModificationException(msg, ex, email);
        }
    }

    /**
     * Please note: original user's password should be plain text instead of hashed version
     */
    @Override
    public void update(UserSummary originalUser, UserSummary updatedUser) throws UserModificationException{
        String updateUserRestfulUrl = userWebServiceUrl.getUpdateUrl() + (userWebServiceUrl.getUpdateUrl().endsWith("/") ? "" : "/") + "{userid}";
        String originalUserEmail = originalUser.getEmail();
        try {
            RestTemplate newRestTemplate = SecureRestTemplateFactory.getTemplate(originalUserEmail, originalUser.getPassword());
            newRestTemplate.put(updateUserRestfulUrl, new UserSummary(updatedUser), originalUser.getId());
        } catch (Exception ex) {
            String msg = "Failed to query web service to update details for user: " + originalUserEmail;
            logger.error(msg, ex);
            throw new UserModificationException(msg, ex, originalUserEmail);
        }
    }

}