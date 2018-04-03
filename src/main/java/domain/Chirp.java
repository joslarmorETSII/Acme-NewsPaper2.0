package domain;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Chirp extends DomainEntity{

    // Constructors ----------------------------------------------------------------------

    public Chirp() { super();}



    // Attributes ------------------------------------------------------------------------





    // Relationships ---------------------------------------------------------------------

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
}
