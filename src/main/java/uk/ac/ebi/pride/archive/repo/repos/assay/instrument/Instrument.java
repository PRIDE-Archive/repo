package uk.ac.ebi.pride.archive.repo.repos.assay.instrument;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import uk.ac.ebi.pride.archive.dataprovider.assay.instrument.InstrumentProvider;
import uk.ac.ebi.pride.archive.repo.repos.assay.Assay;
import uk.ac.ebi.pride.archive.repo.repos.param.CvParam;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * @author Jose A. Dianes
 * @version $Id$
 */
@Entity
@Table( name="instrument" )
@SequenceGenerator(name="InstrumentSequence", sequenceName="instrumentSequence", allocationSize=100)
public class Instrument implements InstrumentProvider {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="InstrumentSequence")
    @Column(name = "instrument_pk")
    private Long id;

    private String value;

    @NotNull
    @ManyToOne( cascade = {CascadeType.MERGE} )
    @JoinColumn( name = "cv_param_fk")
    private CvParam cvParam;

    @ManyToOne
    @JoinColumn( name="assay_fk", nullable = false )
    private Assay assay;

    @OneToMany( cascade = CascadeType.ALL)
    @JoinColumn( name = "instrument_fk", insertable = false, updatable = false )
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<SourceInstrumentComponent> sources;

    @OneToMany( cascade = CascadeType.ALL)
    @JoinColumn( name = "instrument_fk", insertable = false, updatable = false )
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<AnalyzerInstrumentComponent> analyzers;

    @OneToMany( cascade = CascadeType.ALL)//, mappedBy = "instrument")
    @JoinColumn( name = "instrument_fk", insertable = false, updatable = false )
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<DetectorInstrumentComponent> detectors;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InstrumentModel getModel() {
        InstrumentModel model = new InstrumentModel();
        model.setId(this.cvParam.getId()); // TODO: should we use here this id?? instrument ID? neither? it is not important?
        model.setName(this.cvParam.getName());
        model.setValue(this.value);
        model.setAccession(this.cvParam.getAccession());
        model.setCvLabel(this.cvParam.getCvLabel());
        return model;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public CvParam getCvParam() {
        return cvParam;
    }

    public void setCvParam(CvParam cvParam) {
        this.cvParam = cvParam;
    }

    public Collection<SourceInstrumentComponent> getSources() {
        return sources;
    }

    public void setSources(Collection<SourceInstrumentComponent> sources) {
        this.sources = sources;
    }

    public Collection<AnalyzerInstrumentComponent> getAnalyzers() {
        return analyzers;
    }

    public void setAnalyzers(Collection<AnalyzerInstrumentComponent> analyzer) {
        this.analyzers = analyzer;
    }

    public Collection<DetectorInstrumentComponent> getDetectors() {
        return detectors;
    }

    public void setDetectors(Collection<DetectorInstrumentComponent> detector) {
        this.detectors = detector;
    }

    public void setAssay(Assay assay) {
        this.assay = assay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Instrument)) return false;

        Instrument that = (Instrument) o;

        if (analyzers != null ? !analyzers.equals(that.analyzers) : that.analyzers != null) return false;
        if (cvParam != null ? !cvParam.equals(that.cvParam) : that.cvParam != null) return false;
        if (detectors != null ? !detectors.equals(that.detectors) : that.detectors != null) return false;
        if (sources != null ? !sources.equals(that.sources) : that.sources != null) return false;
        return value != null ? value.equals(that.value) : that.value == null;
    }

    @Override
    public int hashCode() {
        int result = value != null ? value.hashCode() : 0;
        result = 31 * result + (cvParam != null ? cvParam.hashCode() : 0);
        result = 31 * result + (sources != null ? sources.hashCode() : 0);
        result = 31 * result + (analyzers != null ? analyzers.hashCode() : 0);
        result = 31 * result + (detectors != null ? detectors.hashCode() : 0);
        return result;
    }
}
