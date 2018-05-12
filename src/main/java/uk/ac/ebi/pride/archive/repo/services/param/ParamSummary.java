package uk.ac.ebi.pride.archive.repo.services.param;

import uk.ac.ebi.pride.archive.dataprovider.param.ParamProvider;

/**
 * @author Rui Wang
 * @version $Id$
 */
public class ParamSummary implements ParamProvider{

    private Long id;
    private String name;
    private String value;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
