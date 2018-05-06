package services;

import domain.Article;
import domain.NewsPaper;
import domain.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.SearchRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Service
@Transactional
public class SearchService {

    // Managed repository -----------------------------------------------------

    @Autowired
    private SearchRepository searchRepository;

    @Autowired
    private ConfigurationService configurationService;

    // Supporting services ----------------------------------------------------

    // Constructors -----------------------------------------------------------

    public SearchService() {
        super();
    }

    // Simple CRUD methods ----------------------------------------------------

    public Search create() {
        Search res;
        Collection<Article> articles = new ArrayList<>();
        Collection<NewsPaper> newsPapers = new ArrayList<>();

        res= new Search();

        res.setLastUpdate(new Date(System.currentTimeMillis() - 1000));
        res.setArticles(articles);
        res.setNewsPapers(newsPapers);
        return res;
    }

    public Search findOne( int id) {
        return searchRepository.findOne(id);
    }

    public Search save( Search search) {
        search.setLastUpdate(new Date(System.currentTimeMillis() - 1000));
        return searchRepository.save(search);
    }

    public Collection<Search> findAll() {
        return searchRepository.findAll();
    }

    // Other business methods -------------------------------------------------

    public void flush() {
        searchRepository.flush();
    }

    public Search getSearch() {
        Collection<Search> search;

        search = this.findAll();

        return search.iterator().next();
    }

    public void checkCache(Search search) {
        Long diference = Math.abs((new Date()).getTime()-search.getLastUpdate().getTime());
        Long cache = (long) configurationService.getCS().getCache()*60*1000;
        if( diference>=cache) {
            search.setArticles(new ArrayList<Article>());
            search.setNewsPapers(new ArrayList<NewsPaper>());
            save(search);
        }
    }
}

