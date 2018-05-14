package uk.ac.ebi.pride.archive.repo.services.assay;

import org.springframework.core.NestedRuntimeException;

/**
 * {@code AssayAccessException} is thrown when there is an error during accessing assay related
 * details.
 *
 * <p>As this class is a runtime exception, there is no need for user to catch it or subclasses it
 * if the error is considered to be fatal.
 *
 * <p>For more reference, please refer to Chapter 9 of <a
 * href="http://www.amazon.com/exec/obidos/tg/detail/-/0764543857/">Expert One-On-One J2EE Design
 * and Development</a>.
 *
 * @author Rui Wang
 * @version $Id$
 */
public class AssayAccessException extends NestedRuntimeException {

  private String projectAccession = null;
  private String assayAccession = null;

  public AssayAccessException(String msg) {
    super(msg);
  }

  public AssayAccessException(String msg, Throwable cause) {
    super(msg, cause);
  }

  public AssayAccessException(String msg, String projectAccession, String assayAccession) {
    super(msg);
    this.projectAccession = projectAccession;
    this.assayAccession = assayAccession;
  }

  public AssayAccessException(
      String msg, Throwable cause, String projectAccession, String assayAccession) {
    super(msg, cause);
    this.projectAccession = projectAccession;
    this.assayAccession = assayAccession;
  }

  public String getProjectAccession() {
    return projectAccession;
  }

  public String getAssayAccession() {
    return assayAccession;
  }
}
