package uk.ac.ebi.pride.archive.repo.project;

import uk.ac.ebi.pride.archive.dataprovider.project.ProjectTagProvider;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Jose A. Dianes
 * @version $Id$
 */
@Entity
@Table( name = "project_tag" )
@SequenceGenerator(name="ProjectTagSequence", sequenceName="projectTagSequence", allocationSize=100)
public class ProjectTag implements ProjectTagProvider {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ProjectTagSequence")
    @Column( name = "project_tag_pk" )
    private Long id;

    @NotNull
    private String tag;

    @NotNull
    @ManyToOne
    @JoinColumn(name="project_fk", nullable = false)
    private Project project;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProjectTag)) return false;

        ProjectTag that = (ProjectTag) o;

        if (project != null ? !project.equals(that.project) : that.project != null) return false;
        if (!tag.equals(that.tag)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tag.hashCode();
        result = 31 * result + (project != null ? project.hashCode() : 0);
        return result;
    }
}
