package dsr.amm.homebudget.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.beans.factory.annotation.Autowired;

import dsr.amm.homebudget.data.dto.CategoryDTO;
import dsr.amm.homebudget.service.CategoryService;

import javax.validation.Valid;
import java.util.List;

// Category controller
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService service;

    // Get category
    @RequestMapping(method = RequestMethod.GET)
    public List<CategoryDTO> getCategory() {
        return service.getCategories();
    }

    // Post category
    @RequestMapping(method = RequestMethod.POST)
    public void createCategory(@RequestBody @Valid CategoryDTO cat) {
        service.create(cat);
    }

    // Delete category
    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteCategory(@RequestBody @Valid CategoryDTO cat) {
        service.delete(cat);
    }
}
