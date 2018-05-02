package domain;

import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Access(AccessType.PROPERTY)
public class Search extends DomainEntity{


    public Search() {
        super();
    }

    private String keyword;

    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    // Relationships ------------------------------------------------

    private Collection<NewsPaper> newsPapers;
    private Collection<Article>   articles;

    @ManyToMany
    public Collection<NewsPaper> getNewsPapers() {
        return newsPapers;
    }

    public void setNewsPapers(Collection<NewsPaper> newsPapers) {
        this.newsPapers = newsPapers;
    }

    @ManyToMany
    public Collection<Article> getArticles() {
        return articles;
    }

    public void setArticles(Collection<Article> articles) {
        this.articles = articles;
    }
}
