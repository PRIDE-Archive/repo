package uk.ac.ebi.pride.archive.repo.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import uk.ac.ebi.pride.archive.repo.project.Project;

import java.util.List;

/**
 * @author Jose A. Dianes
 * @version $Id$
 */
public interface UserRepository extends CrudRepository<User, Long> {

    User findByEmail(String email);

    @Query("select u from User u, IN(u.projects) p where p.id = ?1")
    List<User> findAllByProjectId(Long projectId);

    @Query("select u.projects from User u where u.id = ?1")
    List<Project> findAllProjectsById(Long userId);

}
