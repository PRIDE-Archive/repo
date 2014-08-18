package uk.ac.ebi.pride.archive.repo.assay.software;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import uk.ac.ebi.pride.archive.dataprovider.assay.software.SoftwareProvider;
import uk.ac.ebi.pride.archive.dataprovider.param.ParamProvider;
import uk.ac.ebi.pride.archive.repo.assay.Assay;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.LinkedList;

/**
 * @author Jose A. Dianes
 * @version $Id$
 */
@Entity
@Table( name="software" )
@SequenceGenerator(name="SoftwareSequence", sequenceName="softwareParamSequence", allocationSize=100)
public class Software implements SoftwareProvider {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SoftwareSequence")
    @Column( name="software_pk" )
    private Long id;

    @NotNull
    private String name;

    @Column(name="order_index")
    private int order;

    private String version;

    private String customization;

    @ManyToOne
    @JoinColumn( name="assay_fk", nullable = false )
    private Assay assay;

    @OneToMany( cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "software" )
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<SoftwareCvParam> softwareCvParams;

    @OneToMany( cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "software" )
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<SoftwareUserParam> softwareUserParams;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCustomization() {
        return this.customization;
    }

    public void setCustomization(String customization) {
        this.customization = customization;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Assay getAssay() {
        return assay;
    }

    public void setAssay(Assay assay) {
        this.assay = assay;
    }

    public Collection<ParamProvider> getParams() {
        Collection<ParamProvider> params = new LinkedList<ParamProvider>();

        if (this.softwareCvParams != null) params.addAll(this.softwareCvParams);
        if (this.softwareUserParams != null) params.addAll(this.softwareUserParams);

        return params;
    }

    public Collection<SoftwareCvParam> getSoftwareCvParams() {
        return softwareCvParams;
    }

    public void setSoftwareCvParams(Collection<SoftwareCvParam> cvParams) {
        this.softwareCvParams = cvParams;
    }

    public Collection<SoftwareUserParam> getSoftwareUserParams() {
        return softwareUserParams;
    }

    public void setSoftwareUserParams(Collection<SoftwareUserParam> userParams) {
        this.softwareUserParams = userParams;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Software)) return false;

        Software software = (Software) o;

        if (order != software.order) return false;
        if (customization != null ? !customization.equals(software.customization) : software.customization != null)
            return false;
        if (name != null ? !name.equals(software.name) : software.name != null) return false;
        if (softwareCvParams != null ? !softwareCvParams.equals(software.softwareCvParams) : software.softwareCvParams != null)
            return false;
        if (softwareUserParams != null ? !softwareUserParams.equals(software.softwareUserParams) : software.softwareUserParams != null)
            return false;
        if (version != null ? !version.equals(software.version) : software.version != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + order;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (customization != null ? customization.hashCode() : 0);
        result = 31 * result + (softwareCvParams != null ? softwareCvParams.hashCode() : 0);
        result = 31 * result + (softwareUserParams != null ? softwareUserParams.hashCode() : 0);
        return result;
    }
}
