package dsr.amm.homebudget.controller;

import dsr.amm.homebudget.data.entity.Category;
import dsr.amm.homebudget.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by rifia on 11/29/2018.
 */

@RestController("/")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @RequestMapping(value = "/category", method = RequestMethod.GET)
    public List<Category> getAll() {
        return service.getCategories();
    }

    @RequestMapping(value = "/category", method = RequestMethod.POST)
    public void create(@RequestBody @Valid Category curr) {
        service.create(curr);
    }


    @RequestMapping(value = "/category/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) {
        service.delete(id);
    }
}
