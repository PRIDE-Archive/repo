package uk.ac.ebi.pride.archive.repo.assay.instrument;

import uk.ac.ebi.pride.archive.dataprovider.param.ParamProvider;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Jose A. Dianes
 * @version $Id$
 */
@Entity
@Table( name="instrument_component_userparam" )
@SequenceGenerator(name="InstrumentComponentParamSequence", sequenceName="instrCompParamSequence", allocationSize=100)
public class InstrumentComponentUserParam implements ParamProvider {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="InstrumentComponentParamSequence")
    @Column( name="param_pk")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name="instrument_component_fk")
    private InstrumentComponent instrumentComponent;

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

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public InstrumentComponent getInstrumentComponent() {
        return instrumentComponent;
    }

    public void setInstrumentComponent(InstrumentComponent instrumentComponent) {
        this.instrumentComponent = instrumentComponent;
    }

    public String getName() {
        return name;
    }

    public void setName(String paramName) {
        this.name = paramName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InstrumentComponentUserParam)) return false;

        InstrumentComponentUserParam that = (InstrumentComponentUserParam) o;

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
