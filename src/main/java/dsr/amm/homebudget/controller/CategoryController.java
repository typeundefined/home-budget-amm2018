package dsr.amm.homebudget.controller;

import dsr.amm.homebudget.data.dto.CategoryDTO;
import dsr.amm.homebudget.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    CategoryService service;

    @RequestMapping(method = RequestMethod.GET)
    public List<CategoryDTO> getAll() {
        return service.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CategoryDTO getCategory(@PathVariable("id") long id) {
        return service.getCategory(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public CategoryDTO postCategory(@RequestBody @Valid CategoryDTO categoryDTO) {
            return service.save(categoryDTO);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteOne(@PathVariable("id") long id) {
        service.delete(id);
    }
}
