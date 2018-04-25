package domain;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;


@Entity
@Access(AccessType.PROPERTY)
public class Volume extends DomainEntity {
    // Constructors -----------------------------------------------------------

    public Volume() {
        super();
    }

    // Attributes -------------------------------------------------------------

    private String title;
    private String description;
    private String anyo;

    @NotBlank
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NotBlank
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotBlank
    public String getAnyo() {
        return anyo;
    }

    public void setAnyo(String anyo) {
        this.anyo = anyo;
    }

    // Relationships -----------------------------------------------------------------------

    private Collection<Customer> customers;
    private Collection<NewsPaper> newsPapers;
    private User user;

    @Valid
    @NotNull
    @ManyToOne(optional = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Valid
    @NotNull
    @ManyToMany
    public Collection<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Collection<Customer> customers) {
        this.customers = customers;
    }

    @Valid
    @NotNull
    @ManyToMany
    public Collection<NewsPaper> getNewsPapers() {
        return newsPapers;
    }

    public void setNewsPapers(Collection<NewsPaper> newsPapers) {
        this.newsPapers = newsPapers;
    }
}
