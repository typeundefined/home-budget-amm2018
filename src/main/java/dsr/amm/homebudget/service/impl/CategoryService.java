package dsr.amm.homebudget.service.impl;

import dsr.amm.homebudget.OrikaMapper;
import dsr.amm.homebudget.data.dto.CategoryDTO;
import dsr.amm.homebudget.data.entity.Category;
import dsr.amm.homebudget.data.repository.ICategoryRepository;
import dsr.amm.homebudget.service.ICategoryService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Trokhin
 */

@Service
public class CategoryService implements ICategoryService {

    private final ICategoryRepository categoryRepository;
    private final OrikaMapper mapper;

    public CategoryService(ICategoryRepository repository, OrikaMapper mapper) {
        this.categoryRepository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public CategoryDTO create(CategoryDTO categoryDTO) {
        Category category = fromDTO(categoryDTO);

        Category savedCategory = categoryRepository.save(category);
        return toDTO(savedCategory);
    }

    @Override
    @Transactional
    public CategoryDTO delete(CategoryDTO categoryDTO) {
        return delete(categoryDTO.getId());
    }

    public CategoryDTO delete(Long id) {
        Optional<Category> category = categoryRepository.findById(id);

        category.ifPresent(categoryRepository::delete);

        return toDTO(category.get());
    }

    @Override
    @Transactional
    public List<CategoryDTO> getAll() {
        return toDTO((List<Category>) categoryRepository.findAll());
    }

    @Override
    @Transactional
    public CategoryDTO get(Long id) {
        Optional<Category> category = categoryRepository.findById(id);

        return category.map(this::toDTO).orElse(null);
    }

    private Category fromDTO(CategoryDTO categoryDTO) {
        return mapper.map(categoryDTO, Category.class);
    }
    private CategoryDTO toDTO(Category category) {
        return mapper.map(category, CategoryDTO.class);
    }
    private List<CategoryDTO> toDTO(List<Category> list) {
        return mapper.mapAsList(list, CategoryDTO.class);
    }
}
