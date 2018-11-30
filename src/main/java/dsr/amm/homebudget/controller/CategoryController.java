package dsr.amm.homebudget.controller;

import dsr.amm.homebudget.data.dto.CurrencyDTO;
import dsr.amm.homebudget.data.entity.Category;
import dsr.amm.homebudget.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/category")
public class CategoryController {

        @Autowired
        private CategoryService service;

        @RequestMapping(method = RequestMethod.GET)
        public List<Category> getAll() {
            return service.getCategory();
        }

        @RequestMapping(method = RequestMethod.POST)
        public void getAll(@RequestBody @Valid Category category) {
            service.create(category);
        }

        @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
        public void getAll(@PathVariable("id") Long id) {
        service.delete(id);
    }

}
