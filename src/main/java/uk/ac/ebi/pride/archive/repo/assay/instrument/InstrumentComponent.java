package uk.ac.ebi.pride.archive.repo.assay.instrument;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import uk.ac.ebi.pride.archive.dataprovider.assay.instrument.InstrumentComponentProvider;
import uk.ac.ebi.pride.archive.dataprovider.param.ParamProvider;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.LinkedList;

/**
 * @author Jose A. Dianes
 * @version $Id$
 */
@Entity
@Table( name="instrument_component" )
@Inheritance( strategy=InheritanceType.SINGLE_TABLE )
@DiscriminatorColumn(name="instrument_component_type", discriminatorType=DiscriminatorType.STRING, length=64)
@org.hibernate.annotations.DiscriminatorOptions(force=true)
@SequenceGenerator(name="InstrumentComponentSequence", sequenceName="instrCompSequence", allocationSize=100)
public abstract class InstrumentComponent implements InstrumentComponentProvider {


    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="InstrumentComponentSequence")
    @Column( name="instrument_component_pk" )
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name="instrument_fk", nullable = false)
    private Instrument instrument;

    @NotNull
    @Column( name="order_index" )
    private int order;

    @OneToMany( cascade = CascadeType.ALL, mappedBy = "instrumentComponent")
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<InstrumentComponentUserParam> instrumentComponentUserParams;

    @OneToMany( cascade = CascadeType.ALL, mappedBy = "instrumentComponent")
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<InstrumentComponentCvParam> instrumentComponentCvParams;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Collection<InstrumentComponentUserParam> getInstrumentComponentUserParams() {
        return instrumentComponentUserParams;
    }

    public void setInstrumentComponentUserParams(Collection<InstrumentComponentUserParam> params) {
        this.instrumentComponentUserParams = params;
    }

    public Collection<InstrumentComponentCvParam> getInstrumentComponentCvParams() {
        return instrumentComponentCvParams;
    }

    public void setInstrumentComponentCvParams(Collection<InstrumentComponentCvParam> instrumentComponentCvParams) {
        this.instrumentComponentCvParams = instrumentComponentCvParams;
    }

    public Collection<ParamProvider> getParams() {
        Collection<ParamProvider> params = new LinkedList<>();

        if (this.instrumentComponentCvParams != null) params.addAll(this.instrumentComponentCvParams);
        if (this.instrumentComponentUserParams != null) params.addAll(this.instrumentComponentUserParams);

        return params;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InstrumentComponent)) return false;

        InstrumentComponent that = (InstrumentComponent) o;

        if (order != that.order) return false;
        if (instrumentComponentCvParams != null ? !instrumentComponentCvParams.equals(that.instrumentComponentCvParams) : that.instrumentComponentCvParams != null)
            return false;
        return instrumentComponentUserParams != null ? instrumentComponentUserParams.equals(that.instrumentComponentUserParams) : that.instrumentComponentUserParams == null;
    }

    @Override
    public int hashCode() {
        int result = order;
        result = 31 * result + (instrumentComponentUserParams != null ? instrumentComponentUserParams.hashCode() : 0);
        result = 31 * result + (instrumentComponentCvParams != null ? instrumentComponentCvParams.hashCode() : 0);
        return result;
    }
}
