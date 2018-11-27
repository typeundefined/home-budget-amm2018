package dsr.amm.homebudget.service;

import dsr.amm.homebudget.OrikaMapper;
import dsr.amm.homebudget.data.dto.CategoryDTO;
import dsr.amm.homebudget.data.entity.Category;
import dsr.amm.homebudget.data.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

// Category service
@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Autowired
    private OrikaMapper mapper;

    // Get category method
    @Transactional
    public List<CategoryDTO> getCategories() {
        Iterable<Category> t = repository.findAll();
        return mapper.mapAsList(t, CategoryDTO.class);
    }

    // Create category method
    @Transactional
    public void create(CategoryDTO cat) {
        repository.save(mapper.map(cat, Category.class));
    }

    // Delete category method
    @Transactional
    public void delete(long id) {
        //repository.deleteById(id);
    }
}