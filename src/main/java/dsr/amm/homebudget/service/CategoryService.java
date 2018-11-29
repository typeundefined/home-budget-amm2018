package dsr.amm.homebudget.service;

import dsr.amm.homebudget.OrikaMapper;
import dsr.amm.homebudget.data.dto.CategoryDTO;
import dsr.amm.homebudget.data.entity.Category;
import dsr.amm.homebudget.data.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository repository;

    @Autowired
    private OrikaMapper mapper;

    @Transactional
    public List<CategoryDTO> getCategories() {
        Iterable<Category> t = repository.findAll();
        return mapper.mapAsList(t, CategoryDTO.class);
    }

    @Transactional
    public void create(CategoryDTO category) {
        repository.save(mapper.map(category, Category.class));
    }

    public void removeById(long categoryId) {
        repository.deleteById(categoryId);
    }
}
