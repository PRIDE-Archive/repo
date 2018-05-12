package uk.ac.ebi.pride.archive.repo.services.project;

import org.springframework.core.NestedRuntimeException;

/**
 * {@code ProjectAccessException} is thrown when there is an error during accessing project details.
 *
 * As this class is a runtime exception, there is no need for user to catch it or
 * subclasses it if the error is considered to be fatal.
 *
 * For more reference, please refer to Chapter 9 of
 * <a href="http://www.amazon.com/exec/obidos/tg/detail/-/0764543857/">Expert One-On-One J2EE Design and Development</a>.
 *
 * @author Rui Wang
 * @version $Id$
 */
public class ProjectAccessException extends NestedRuntimeException {

    private String projectAccession = null;

    public ProjectAccessException(String msg) {
        super(msg);
    }

    public ProjectAccessException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ProjectAccessException(String msg, String projectAccession) {
        super(msg);
        this.projectAccession = projectAccession;
    }

    public ProjectAccessException(String msg, Throwable cause, String projectAccession) {
        super(msg, cause);
        this.projectAccession = projectAccession;
    }

    public String getProjectAccession() {
        return projectAccession;
    }
}
