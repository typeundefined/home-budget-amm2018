package dsr.amm.homebudget.controller;

import dsr.amm.homebudget.data.dto.WithdrawalCategoryDTO;
import dsr.amm.homebudget.data.entity.WithdrawalCategory;
import dsr.amm.homebudget.exceptions.EntityAlreadyExistException;
import dsr.amm.homebudget.exceptions.NotFoundException;
import dsr.amm.homebudget.service.WithdrawalCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
//import dsr.amm.homebudget.OrikaMapper;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class WithdrawalCategoryController {

    @Autowired
    WithdrawalCategoryService service;

    @RequestMapping(method = RequestMethod.GET)
    public List<WithdrawalCategoryDTO> getAll() {
        List<WithdrawalCategoryDTO> categories = new ArrayList<>();
        service.getAll().forEach(categories::add);
        if (!categories.isEmpty())
            return categories;
        else
            throw new NotFoundException();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public WithdrawalCategoryDTO getCategory(@PathVariable("id") long id) {
        WithdrawalCategoryDTO category = service.getCategory(id);
        if (category != null)
            return category;
        else
            throw new NotFoundException();
    }

    @RequestMapping(method = RequestMethod.PUT)
    public WithdrawalCategoryDTO putCategory(@RequestBody @Valid WithdrawalCategoryDTO categoryDTO) {
        if (service.getCategory(categoryDTO.getId()) == null) {
            return service.save(categoryDTO);
        } else
            throw new EntityAlreadyExistException();
    }

    @RequestMapping(method = RequestMethod.POST)
    public WithdrawalCategoryDTO postCategory(@RequestBody @Valid WithdrawalCategoryDTO categoryDTO) {
        if (service.getCategory(categoryDTO.getId()) != null) {
            return service.save(categoryDTO);
        } else
            throw new NotFoundException();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteOne(@PathVariable("id") long id) {
        service.delete(id);
    }
}
