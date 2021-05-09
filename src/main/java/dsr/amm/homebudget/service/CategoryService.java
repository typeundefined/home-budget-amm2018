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
    CategoryRepository repository;

    @Autowired
    OrikaMapper mapper;

    @Transactional
    public CategoryDTO save(CategoryDTO category) {
        return mapper.map(repository.save(mapper.map(category, Category.class)), CategoryDTO.class);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public CategoryDTO getCategory(Long id) {
        return mapper.map(repository.findCategoryById(id), CategoryDTO.class);
    }

    @Transactional
    public List<CategoryDTO> getAll() {
        Iterable<Category> accList = repository.findAll();
        return mapper.mapAsList(accList, CategoryDTO.class);
    }
}
