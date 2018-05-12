package uk.ac.ebi.pride.archive.repo.assay.service;

import uk.ac.ebi.pride.archive.dataprovider.assay.software.SoftwareProvider;
import uk.ac.ebi.pride.archive.repo.param.service.ParamSummary;
import uk.ac.ebi.pride.archive.repo.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Rui Wang
 * @version $Id$
 */
public class SoftwareSummary implements SoftwareProvider {

    private int order;
    private String name;
    private String customization;
    private String version;
    private Long id;
    private Collection<ParamSummary> params;

    public SoftwareSummary() {
        this.params = new ArrayList<>();
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCustomization() {
        return customization;
    }

    public void setCustomization(String customization) {
        this.customization = customization;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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
