package dsr.amm.homebudget.service;

import dsr.amm.homebudget.OrikaMapper;
import dsr.amm.homebudget.data.dto.CategoryDTO;
import dsr.amm.homebudget.data.entity.Category;
import dsr.amm.homebudget.data.repository.CategoryRepository;
import dsr.amm.homebudget.util.authentication.PrincipalRetriever;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by rifia on 11/29/2018.
 */


@Service
public class CategoryService {
    @Autowired
    private CategoryRepository repository;

    @Autowired
    private OrikaMapper mapper;

    @Autowired
    private PrincipalRetriever principalRetriever;

    @Transactional
    public List<CategoryDTO> getCategories() {
        Iterable<Category> p = repository.findAll();
        return mapper.mapAsList(p, CategoryDTO.class);
    }

    @Transactional
    public void create(CategoryDTO cater) {
        Category category = mapper.map(cater, Category.class);
        category.setOwner(principalRetriever.getPrincipal().getPerson());
        repository.save(category);
    }

    @Transactional
    public void delete(Long id){
        repository.deleteById(id);
    }

}
