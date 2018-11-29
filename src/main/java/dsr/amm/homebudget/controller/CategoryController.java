package dsr.amm.homebudget.controller;

import dsr.amm.homebudget.data.dto.CategoryDTO;
import dsr.amm.homebudget.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/category")
public class CategoryController {
    @Autowired
    CategoryService service;

    @RequestMapping(method = RequestMethod.GET)
    public List<CategoryDTO> getAll() {
        return service.getCategories();
    }

    @RequestMapping(method = RequestMethod.POST)
    void createOne(@RequestBody @Valid CategoryDTO category) {
        service.create(category);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    void deleteOne(@PathVariable("id") long categoryId) {
        service.removeById(categoryId);
    }
}
