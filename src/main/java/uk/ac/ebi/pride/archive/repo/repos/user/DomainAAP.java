package uk.ac.ebi.pride.archive.repo.repos.user;

public class DomainAAP {

    private String domainName;
    private String domainDesc;
    private String domainReference;

    public DomainAAP(String domainName, String domainDesc, String domainReference) {
        this.domainName = domainName;
        this.domainDesc = domainDesc;
        this.domainReference = domainReference;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getDomainDesc() {
        return domainDesc;
    }

    public void setDomainDesc(String domainDesc) {
        this.domainDesc = domainDesc;
    }

    public String getDomainReference() {
        return domainReference;
    }

    public void setDomainReference(String domainReference) {
        this.domainReference = domainReference;
    }
}
