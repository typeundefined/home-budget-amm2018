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

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @RequestMapping(method = RequestMethod.GET)
    public List<CategoryDTO> getAll() {
        return service.getCategories();
    }

    @RequestMapping(method = RequestMethod.POST)
    public void postCat(@RequestBody @Valid CategoryDTO cat) {
        service.create(cat);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void delCat(@RequestBody @Valid CategoryDTO cat) {
        service.delete(cat);
    }
}
