package uk.ac.ebi.pride.archive.repo.user;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import uk.ac.ebi.pride.archive.dataprovider.person.Title;
import uk.ac.ebi.pride.archive.dataprovider.person.UserAuthority;
import uk.ac.ebi.pride.archive.dataprovider.person.UserProvider;
import uk.ac.ebi.pride.archive.repo.project.Project;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Jose A. Dianes
 * @version $Id$
 */
@Entity
@Table(name = "pride_users")
@SequenceGenerator(name="UserSequence", sequenceName="prideUserSequence", allocationSize=100)
public class User implements UserProvider{

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="UserSequence")
    @Column(name = "user_pk")
    private Long id;

    @NotNull
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Title title;

    @NotNull
    @Column( name = "first_name" )
    private String firstName;

    @NotNull
    @Column( name = "last_name" )
    private String lastName;

    @NotNull
    private String affiliation;

    @NotNull
    @Column(unique=true)
    private String email;

    @NotNull
    @Column( name = "creation_date" )
    private Date createAt;

    @NotNull
    @Column( name = "update_date" )
    private Date updateAt;

    @OneToMany( cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "user")
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<Authority> authorities;

    @ManyToMany(
            cascade = CascadeType.MERGE,
            mappedBy = "users"
    )
    private Collection<Project> projects;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = PasswordUtilities.encode(password);
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    @Override
    public Set<UserAuthority> getUserAuthorities() {
        Set<UserAuthority> userAuthorities = new HashSet<UserAuthority>();
        if (authorities != null) {
            for (Authority authority : authorities) {
                userAuthorities.add(authority.getAuthority());
            }
        }
        return userAuthorities;
    }

    public void setUserAuthorities(Set<UserAuthority> userAuthorities) {
        this.authorities = new HashSet<Authority>();
        for (UserAuthority userAuthority : userAuthorities) {
            Authority authority = new Authority();
            authority.setAuthority(userAuthority);
            authority.setUser(this);
            authorities.add(authority);
        }
    }

    private Collection<Authority> getAuthorities() {
        return authorities;
    }

    private void setAuthorities(Collection<Authority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (affiliation != null ? !affiliation.equals(user.affiliation) : user.affiliation != null) return false;
//        if (createAt != null ? !createAt.equals(user.createAt) : user.createAt != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (title != user.title) return false;
//        if (updateAt != null ? !updateAt.equals(user.updateAt) : user.updateAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = password != null ? password.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (affiliation != null ? affiliation.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
//        result = 31 * result + (createAt != null ? createAt.hashCode() : 0);
//        result = 31 * result + (updateAt != null ? updateAt.hashCode() : 0);
        return result;
    }
}
