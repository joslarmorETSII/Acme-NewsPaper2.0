package domain;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
@Access(AccessType.PROPERTY)
public class Customer extends Actor {

    // Constructors ----------------------------------------------------------

    public Customer() {
        super();
    }

    // Relationships ----------------------------------------------------------

    Collection<NewsPaper> newsPapers;

    @Valid
    @NotNull
    @ManyToMany(mappedBy = "newsPapers")
    public Collection<NewsPaper> getNewsPapers() {
        return newsPapers;
    }

    public void setNewsPapers(Collection<NewsPaper> newsPapers) {
        this.newsPapers = newsPapers;
    }

}
