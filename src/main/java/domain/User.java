package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
@Access(AccessType.PROPERTY)
public class User extends Actor{

    // Constructors ----------------------------------------------------------------------

    public User() { super();}

    // Attributes ------------------------------------------------------------------------

    private Collection<Chirp> chirps;
    private Collection<NewsPaper> newsPapers;
    private Collection<User> followers;
    private Collection<User> followings;

    // Relationships ----------------------------------------------------------------------


    @Valid
    @NotNull
    @OneToMany(mappedBy = "user")
    public Collection<Chirp> getChirps() {
        return chirps;
    }

    @Valid
    @NotNull
    @OneToMany(mappedBy = "publisher")
    public Collection<NewsPaper> getNewsPapers() {
        return newsPapers;
    }

    @Valid
    @NotNull
    @OneToMany
    public Collection<User> getFollowers() {
        return followers;
    }

    @NotNull
    @Valid
    @OneToMany
    public Collection<User> getFollowings() {
        return followings;
    }

    public void setChirps(Collection<Chirp> chirps) {
        this.chirps = chirps;
    }

    public void setNewsPapers(Collection<NewsPaper> newsPapers) {
        this.newsPapers = newsPapers;
    }

    public void setFollowers(Collection<User> followers) {
        this.followers = followers;
    }

    public void setFollowings(Collection<User> followings) {
        this.followings = followings;
    }
}
