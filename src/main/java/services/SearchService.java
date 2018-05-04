/*
package services;

import domain.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.SearchRepository;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class SearchService {

    // Managed repository -----------------------------------------------------

    @Autowired
    private SearchRepository searchRepository;

    // Supporting services ----------------------------------------------------

    @Autowired
    private ArticleService articleService;

    @Autowired
    private NewsPaperService newsPaperService;

    // Constructors -----------------------------------------------------------

    public SearchService() {
        super();
    }

    // Simple CRUD methods ----------------------------------------------------

    public Search create() {
        Search res;

        res= new Search();

        return res;
    }

    public Search findOne( int id) {
        return searchRepository.findOne(id);
    }

    public Search save( Search search) {
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


}
*/
