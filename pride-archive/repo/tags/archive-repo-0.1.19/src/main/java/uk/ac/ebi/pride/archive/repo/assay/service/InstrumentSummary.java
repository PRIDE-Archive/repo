package uk.ac.ebi.pride.archive.repo.assay.service;

import uk.ac.ebi.pride.archive.dataprovider.assay.instrument.InstrumentProvider;
import uk.ac.ebi.pride.archive.repo.param.service.CvParamSummary;
import uk.ac.ebi.pride.archive.repo.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Rui Wang
 * @version $Id$
 */
public class InstrumentSummary implements InstrumentProvider {

    private CvParamSummary model;
    private Collection<InstrumentComponentSummary> sources;
    private Collection<InstrumentComponentSummary> analyzers;
    private Collection<InstrumentComponentSummary> detectors;
    private Long id;

    public InstrumentSummary() {
        this.sources = new ArrayList<InstrumentComponentSummary>();
        this.analyzers= new ArrayList<InstrumentComponentSummary>();
        this.detectors = new ArrayList<InstrumentComponentSummary>();
    }

    public CvParamSummary getModel() {
        return model;
    }

    public void setModel(CvParamSummary model) {
        this.model = model;
    }

    public Collection<InstrumentComponentSummary> getSources() {
        return sources;
    }

    public void setSources(Collection<InstrumentComponentSummary> sources) {
        CollectionUtils.replaceValuesInCollection(sources, this.sources);
    }

    public Collection<InstrumentComponentSummary> getAnalyzers() {
        return analyzers;
    }

    public void setAnalyzers(Collection<InstrumentComponentSummary> analyzers) {
        CollectionUtils.replaceValuesInCollection(analyzers, this.analyzers);
    }

    public Collection<InstrumentComponentSummary> getDetectors() {
        return detectors;
    }

    public void setDetectors(Collection<InstrumentComponentSummary> detectors) {
        CollectionUtils.replaceValuesInCollection(detectors, this.detectors);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
