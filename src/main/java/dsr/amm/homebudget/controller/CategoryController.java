package dsr.amm.homebudget.controller;

import dsr.amm.homebudget.data.dto.CategoryDTO;
import dsr.amm.homebudget.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/category")
public class CategoryController {
    @Autowired
    private CategoryService service;

    @RequestMapping(method = RequestMethod.GET)
    public List<CategoryDTO> getAll() {
        return service.getMyCategories();
    }

    @RequestMapping(method = RequestMethod.POST)
    public void getAll(@RequestBody @Valid CategoryDTO category) {
        service.create(category);
    }
}
