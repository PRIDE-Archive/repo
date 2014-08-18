package uk.ac.ebi.pride.archive.repo.assay.software;

import uk.ac.ebi.pride.archive.repo.param.CvParam;
import uk.ac.ebi.pride.archive.dataprovider.param.CvParamProvider;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Jose A. Dianes
 * @version $Id$
 */
@Entity
@Table( name = "software_cvparam" )
@SequenceGenerator(name="SoftwareParamSequence", sequenceName="softwareParamSequence", allocationSize=100)
public class SoftwareCvParam implements CvParamProvider {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SoftwareParamSequence")
    @Column(name="param_pk")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name="software_fk")
    private Software software;

    @NotNull
    @ManyToOne( cascade = {CascadeType.MERGE} )
    @JoinColumn( name = "cv_param_fk")
    private CvParam cvParam;

    @Column( name="value" )
    private String value;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Software getSoftware() {
        return software;
    }

    public void setSoftware(Software software) {
        this.software = software;
    }

    public CvParam getCvParam() {
        return cvParam;
    }

    public void setCvParam(CvParam cvParam) {
        this.cvParam = cvParam;
    }

    public String getName() {
        return this.cvParam.getName();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCvLabel() {
        return this.cvParam.getCvLabel();
    }

    public String getAccession() {
        return this.cvParam.getAccession();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SoftwareCvParam)) return false;

        SoftwareCvParam that = (SoftwareCvParam) o;

        if (cvParam != null ? !cvParam.equals(that.cvParam) : that.cvParam != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = cvParam != null ? cvParam.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
