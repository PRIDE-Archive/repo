package uk.ac.ebi.pride.archive.repo.repos.assay;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import uk.ac.ebi.pride.archive.dataprovider.assay.AssayProvider;
import uk.ac.ebi.pride.archive.dataprovider.assay.AssayType;
import uk.ac.ebi.pride.archive.dataprovider.common.ITuple;
import uk.ac.ebi.pride.archive.dataprovider.param.CvParamProvider;
import uk.ac.ebi.pride.archive.dataprovider.param.ParamProvider;
import uk.ac.ebi.pride.archive.repo.repos.assay.instrument.Instrument;
import uk.ac.ebi.pride.archive.repo.repos.assay.software.Software;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.LinkedList;

/**
 * @author Jose A. Dianes
 * @version $Id$
 */
@Entity
@Table(name = "assay")
@SequenceGenerator(name = "AssaySequence", sequenceName = "assaySequence", allocationSize = 100)
public class Assay implements AssayProvider {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AssaySequence")
  @Column(name = "assay_pk")
  private Long id;

  @Column(name = "project_fk")
  private Long projectId;

  @NotNull private String accession;

  @NotNull private String title;

  @Column(name = "short_label")
  private String shortLabel;

  @NotNull
  @Column(name = "protein_count")
  private int proteinCount;

  @NotNull
  @Column(name = "unique_peptide_count")
  private int uniquePeptideCount;

  @Column(name = "identified_spectrum_count")
  private int identifiedSpectrumCount;

  @NotNull
  @Column(name = "total_spectrum_count")
  private int totalSpectrumCount;

  @NotNull
  @Column(name = "peptide_count")
  private int peptideCount;

  @NotNull
  @Column(name = "ms2_annotation")
  private boolean ms2Annotation;

  @NotNull private boolean chromatogram;

  @Column(name = "experimental_factor")
  private String experimentalFactor;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "assay_fk", insertable = false, updatable = false)
  @LazyCollection(LazyCollectionOption.FALSE)
  private Collection<AssayGroupUserParam> assayGroupUserParams;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "assay_fk", insertable = false, updatable = false)
  @LazyCollection(LazyCollectionOption.FALSE)
  private Collection<AssayGroupCvParam> assayGroupCvParams;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "assay_fk", insertable = false, updatable = false)
  @LazyCollection(LazyCollectionOption.FALSE)
  private Collection<AssaySampleCvParam> samples;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "assay")
  @LazyCollection(LazyCollectionOption.FALSE)
  private Collection<Contact> contacts;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "assay_fk", insertable = false, updatable = false)
  @LazyCollection(LazyCollectionOption.FALSE)
  private Collection<AssayQuantificationMethodCvParam> quantificationMethods;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "assay")
  @LazyCollection(LazyCollectionOption.FALSE)
  private Collection<AssayPTM> ptms;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "assay")
  @LazyCollection(LazyCollectionOption.FALSE)
  private Collection<Software> softwares;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "assay")
  @LazyCollection(LazyCollectionOption.FALSE)
  private Collection<Instrument> instruments;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getProjectId() {
    return projectId;
  }

  public void setProjectId(Long projectId) {
    this.projectId = projectId;
  }

  public String getAccession() {
    return this.accession;
  }

  public void setAccession(String accession) {
    this.accession = accession;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getShortLabel() {
    return this.shortLabel;
  }

  public void setShortLabel(String shortLabel) {
    this.shortLabel = shortLabel;
  }

  public String getExperimentalFactor() {
    return this.experimentalFactor;
  }

  public void setExperimentalFactor(String experimentalFactor) {
    this.experimentalFactor = experimentalFactor;
  }

  public int getProteinCount() {
    return this.proteinCount;
  }

  public void setProteinCount(int proteinCount) {
    this.proteinCount = proteinCount;
  }

  public int getPeptideCount() {
    return this.peptideCount;
  }

  public void setPeptideCount(int peptideCount) {
    this.peptideCount = peptideCount;
  }

  public int getUniquePeptideCount() {
    return this.uniquePeptideCount;
  }

  public void setUniquePeptideCount(int uniquePeptideCount) {
    this.uniquePeptideCount = uniquePeptideCount;
  }

  public int getIdentifiedSpectrumCount() {
    return this.identifiedSpectrumCount;
  }

  public void setIdentifiedSpectrumCount(int identifiedSpectrumCount) {
    this.identifiedSpectrumCount = identifiedSpectrumCount;
  }

  public int getTotalSpectrumCount() {
    return this.totalSpectrumCount;
  }

  public void setTotalSpectrumCount(int totalSpectrumCount) {
    this.totalSpectrumCount = totalSpectrumCount;
  }

  public boolean hasMs2Annotation() {
    return this.ms2Annotation;
  }

  public void setMs2Annotation(boolean ms2Annotation) {
    this.ms2Annotation = ms2Annotation;
  }

  public boolean hasChromatogram() {
    return this.chromatogram;
  }

  public void setChromatogram(boolean chromatogram) {
    this.chromatogram = chromatogram;
  }

  public Collection<AssaySampleCvParam> getSamples() {
    return this.samples;
  }

  public void setSamples(Collection<AssaySampleCvParam> samples) {
    this.samples = samples;
  }

  public Collection<Instrument> getInstruments() {
    return this.instruments;
  }

  public void setInstruments(Collection<Instrument> instruments) {
    this.instruments = instruments;
  }

  public Collection<Software> getSoftwares() {
    return this.softwares;
  }

  public void setSoftwares(Collection<Software> softwares) {
    this.softwares = softwares;
  }

  public Collection<AssayPTM> getPtms() {
    return this.ptms;
  }

  public void setPtms(Collection<AssayPTM> ptms) {
    this.ptms = ptms;
  }

  public Collection<AssayQuantificationMethodCvParam> getQuantificationMethods() {
    return this.quantificationMethods;
  }

  public void setQuantificationMethods(
      Collection<AssayQuantificationMethodCvParam> quantificationMethods) {
    this.quantificationMethods = quantificationMethods;
  }

  public Collection<Contact> getContacts() {
    return this.contacts;
  }

  public void setContacts(Collection<Contact> contacts) {
    this.contacts = contacts;
  }

  public Collection<AssayGroupUserParam> getAssayGroupUserParams() {
    return this.assayGroupUserParams;
  }

  public void setAssayGroupUserParams(Collection<AssayGroupUserParam> params) {
    this.assayGroupUserParams = params;
  }

  public Collection<AssayGroupCvParam> getAssayGroupCvParams() {
    return assayGroupCvParams;
  }

  public void setAssayGroupCvParams(Collection<AssayGroupCvParam> assayGroupCvParams) {
    this.assayGroupCvParams = assayGroupCvParams;
  }

  public Collection<ParamProvider> getParams() {
    Collection<ParamProvider> params = new LinkedList<>();

    if (this.assayGroupCvParams != null) params.addAll(this.assayGroupCvParams);
    if (this.assayGroupUserParams != null) params.addAll(this.assayGroupUserParams);

    return params;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Assay)) return false;

    Assay assay = (Assay) o;

    if (chromatogram != assay.chromatogram) return false;
    if (identifiedSpectrumCount != assay.identifiedSpectrumCount) return false;
    if (ms2Annotation != assay.ms2Annotation) return false;
    if (peptideCount != assay.peptideCount) return false;
    if (proteinCount != assay.proteinCount) return false;
    if (totalSpectrumCount != assay.totalSpectrumCount) return false;
    if (uniquePeptideCount != assay.uniquePeptideCount) return false;
    if (accession != null ? !accession.equals(assay.accession) : assay.accession != null)
      return false;
    if (assayGroupCvParams != null
        ? !assayGroupCvParams.equals(assay.assayGroupCvParams)
        : assay.assayGroupCvParams != null) return false;
    if (assayGroupUserParams != null
        ? !assayGroupUserParams.equals(assay.assayGroupUserParams)
        : assay.assayGroupUserParams != null) return false;
    if (contacts != null ? !contacts.equals(assay.contacts) : assay.contacts != null) return false;
    if (experimentalFactor != null
        ? !experimentalFactor.equals(assay.experimentalFactor)
        : assay.experimentalFactor != null) return false;
    if (instruments != null ? !instruments.equals(assay.instruments) : assay.instruments != null)
      return false;
    if (ptms != null ? !ptms.equals(assay.ptms) : assay.ptms != null) return false;
    if (quantificationMethods != null
        ? !quantificationMethods.equals(assay.quantificationMethods)
        : assay.quantificationMethods != null) return false;
    if (samples != null ? !samples.equals(assay.samples) : assay.samples != null) return false;
    if (shortLabel != null ? !shortLabel.equals(assay.shortLabel) : assay.shortLabel != null)
      return false;
    if (softwares != null ? !softwares.equals(assay.softwares) : assay.softwares != null)
      return false;
    return title != null ? title.equals(assay.title) : assay.title == null;
  }

  @Override
  public int hashCode() {
    int result = accession != null ? accession.hashCode() : 0;
    result = 31 * result + (title != null ? title.hashCode() : 0);
    result = 31 * result + (shortLabel != null ? shortLabel.hashCode() : 0);
    result = 31 * result + proteinCount;
    result = 31 * result + uniquePeptideCount;
    result = 31 * result + identifiedSpectrumCount;
    result = 31 * result + totalSpectrumCount;
    result = 31 * result + peptideCount;
    result = 31 * result + (ms2Annotation ? 1 : 0);
    result = 31 * result + (chromatogram ? 1 : 0);
    result = 31 * result + (experimentalFactor != null ? experimentalFactor.hashCode() : 0);
    result = 31 * result + (assayGroupUserParams != null ? assayGroupUserParams.hashCode() : 0);
    result = 31 * result + (assayGroupCvParams != null ? assayGroupCvParams.hashCode() : 0);
    result = 31 * result + (samples != null ? samples.hashCode() : 0);
    result = 31 * result + (contacts != null ? contacts.hashCode() : 0);
    result = 31 * result + (quantificationMethods != null ? quantificationMethods.hashCode() : 0);
    result = 31 * result + (ptms != null ? ptms.hashCode() : 0);
    result = 31 * result + (softwares != null ? softwares.hashCode() : 0);
    result = 31 * result + (instruments != null ? instruments.hashCode() : 0);
    return result;
  }

  @Override
  public Collection<? extends ITuple<? extends CvParamProvider, ? extends CvParamProvider>> getSampleProperties() {
    return null;
  }

  @Override
  public AssayType getAssayType() {
    return null;
  }

  @Override
  public Collection<? extends CvParamProvider> getAdditionalProperties() {
    return null;
  }
}
