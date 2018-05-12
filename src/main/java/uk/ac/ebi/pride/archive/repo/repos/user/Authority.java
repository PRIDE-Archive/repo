package uk.ac.ebi.pride.archive.repo.repos.user;

import uk.ac.ebi.pride.archive.dataprovider.person.UserAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Jose A. Dianes
 * @version $Id$
 */
@Entity
@Table(name="authorities")
@SequenceGenerator(name="AuthoritySequence", sequenceName="authoritySequence", allocationSize=100)
public class Authority {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="AuthoritySequence")
    @Column(name="authority_pk")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name="user_fk")
    private User user;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserAuthority authority;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserAuthority getAuthority() {
        return authority;
    }

    public void setAuthority(UserAuthority authority) {
        this.authority = authority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Authority)) return false;

        Authority authority1 = (Authority) o;

        if (authority != authority1.authority) return false;
        return user != null ? user.equals(authority1.user) : authority1.user == null;
    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (authority != null ? authority.hashCode() : 0);
        return result;
    }
}
