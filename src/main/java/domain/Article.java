package domain;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.Collection;
import java.util.Date;


@Entity
@Access(AccessType.PROPERTY)
public class Article extends DomainEntity {
    // Constructors -----------------------------------------------------------

    public Article() {
        super();
    }

    // Attributes -------------------------------------------------------------

    private String title;
    private Date moment;
    private String summary;
    private String body;
    private String picture;
    private Boolean finalMode;
    private Boolean taboo;

    @NotBlank
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    public Date getMoment() {
        return moment;
    }

    public void setMoment(Date moment) {
        this.moment = moment;
    }

    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @URL
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Boolean getFinalMode() {
        return finalMode;
    }

    public void setFinalMode(Boolean finalMode) {
        this.finalMode = finalMode;
    }

    public Boolean getTaboo() {
        return taboo;
    }

    public void setTaboo(Boolean taboo) {
        this.taboo = taboo;
    }

    // Relationships -----------------------------------------------------------------------

    private NewsPaper newsPaper;
    private Collection<FollowUp> followUps;

    @Valid
    @ManyToMany(mappedBy = "articles")
    public Collection<FollowUp> getFollowUps() {
        return followUps;
    }

    public void setFollowUps(Collection<FollowUp> followUps) {
        this.followUps = followUps;
    }

    @Valid
    @ManyToOne(optional = false)
    public NewsPaper getNewsPaper() {
        return newsPaper;
    }

    public void setNewsPaper(NewsPaper newsPaper) {
        this.newsPaper = newsPaper;
    }
}
