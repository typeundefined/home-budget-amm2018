package dsr.amm.homebudget.controller;


import dsr.amm.homebudget.data.dto.CategoryDTO;
import dsr.amm.homebudget.service.impl.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Trokhin
 *
 */

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/all")
    public List<CategoryDTO> getAll() {
        return categoryService.getAll();
    }
    @GetMapping("/{id}")
    public CategoryDTO getByID(@PathVariable("id") Long id) {
        CategoryDTO categoryDTO = categoryService.get(id);

        if(categoryDTO == null)
            throw new IllegalArgumentException();
        else
            return categoryDTO;
    }
    @PostMapping
    public CategoryDTO create(@RequestBody CategoryDTO categoryDTO) {
        return categoryService.create(categoryDTO);
    }
    @DeleteMapping("/{id}")
    public CategoryDTO delete(@PathVariable("id") Long id) {
        return categoryService.delete(id);
    }
}
