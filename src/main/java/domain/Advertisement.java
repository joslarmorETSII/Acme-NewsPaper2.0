package domain;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
@Access(AccessType.PROPERTY)
public class Advertisement extends DomainEntity{

    // Constructors ----------------------------------------------------------------------

    public Advertisement() { super();}

    // Attributes ------------------------------------------------------------------------

    private String title;
    private String banner;
    private String targetPage;
    private Boolean taboo;

    @NotBlank
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @URL
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    @URL
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getTargetPage() {
        return targetPage;
    }

    public void setTargetPage(String targetPage) {
        this.targetPage = targetPage;
    }

    public Boolean getTaboo() {
        return taboo;
    }

    public void setTaboo(Boolean taboo) {
        this.taboo = taboo;
    }

    // Relationships ----------------------------------------------------------------------

    private Collection<NewsPaper> newsPapers;
    private Agent agent;

    @Valid
    @NotNull
    @ManyToOne(optional = false)
    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    @Valid
    @NotNull
    @ManyToMany(mappedBy = "advertisements")
    public Collection<NewsPaper> getNewsPapers() {
        return newsPapers;
    }

    public void setNewsPapers(Collection<NewsPaper> newsPapers) {
        this.newsPapers = newsPapers;
    }
}
