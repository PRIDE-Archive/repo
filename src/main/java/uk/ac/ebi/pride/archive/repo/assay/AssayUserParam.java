package uk.ac.ebi.pride.archive.repo.assay;

import uk.ac.ebi.pride.archive.dataprovider.param.ParamProvider;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Jose A. Dianes
 * @version $Id$
 */
@Entity
@Table( name = "assay_userparam" )
@Inheritance( strategy= InheritanceType.SINGLE_TABLE )
@DiscriminatorColumn(name="param_type", discriminatorType=DiscriminatorType.STRING, length=32)
@org.hibernate.annotations.DiscriminatorOptions(force=true)
@SequenceGenerator(name="AssayParamSequence", sequenceName="assayParamSequence", allocationSize=100)
public abstract class AssayUserParam implements ParamProvider {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="AssayParamSequence")
    @Column(name="param_pk")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name="assay_fk", nullable = false )
    private Assay assay;

    @Column( name = "param_name" )
    private String name;

    @Column( name="value" )
    private String value;


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Assay getAssay() {
        return assay;
    }

    public void setAssay(Assay assay) {
        this.assay = assay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AssayUserParam)) return false;

        AssayUserParam that = (AssayUserParam) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return value != null ? value.equals(that.value) : that.value == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
