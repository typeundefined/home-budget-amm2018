package dsr.amm.homebudget.service;

import dsr.amm.homebudget.OrikaMapper;
import dsr.amm.homebudget.data.dto.CategoryDTO;
import dsr.amm.homebudget.data.entity.Category;
import dsr.amm.homebudget.data.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository repository;

    @Autowired
    private OrikaMapper mapper;

    @Transactional
    public List<CategoryDTO> getMyCategories(){
        Iterable<Category> categoryList = repository.findAll();
        return mapper.mapAsList(categoryList, CategoryDTO.class);
    }

    @Transactional
    public void create(CategoryDTO category) {
        repository.save(mapper.map(category, Category.class));
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

}
