package uk.ac.ebi.pride.archive.repo.assay.service;

import uk.ac.ebi.pride.archive.dataprovider.assay.instrument.InstrumentComponentProvider;
import uk.ac.ebi.pride.archive.repo.param.service.ParamSummary;
import uk.ac.ebi.pride.archive.repo.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Rui Wang
 * @version $Id$
 */
public class InstrumentComponentSummary implements InstrumentComponentProvider {

    private int order;
    private Long id;
    private Collection<ParamSummary> params;

    public InstrumentComponentSummary() {
        this.params = new ArrayList<ParamSummary>();
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Collection<ParamSummary> getParams() {
        return params;
    }

    public void setParams(Collection<ParamSummary> params) {
        CollectionUtils.replaceValuesInCollection(params, this.params);
    }
}
