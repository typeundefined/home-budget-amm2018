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

        service.getAll().forEach(it ->
                categories.add(new WithdrawalCategoryDTO(it.getId(), it.getUserId(), it.getName(), it.getDescription()))
        );

        if (!categories.isEmpty())
            return categories;
        else
            throw new NotFoundException();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public WithdrawalCategoryDTO getCategory(@PathVariable("id") long id) {
        WithdrawalCategory category = service.getCategory(id);
        if (category != null)
            return new WithdrawalCategoryDTO(category.getId(), category.getUserId(), category.getName(), category.getDescription());
        else
            throw new NotFoundException();
    }

    @RequestMapping(method = RequestMethod.PUT)
    public WithdrawalCategoryDTO putCategory(@RequestBody @Valid WithdrawalCategoryDTO categoryDTO) {
        if (service.getCategory(categoryDTO.getId()) == null) {
            return insertCategory(categoryDTO);
        } else
            throw new EntityAlreadyExistException();
    }

    @RequestMapping(method = RequestMethod.POST)
    public WithdrawalCategoryDTO postCategory(@RequestBody @Valid WithdrawalCategoryDTO categoryDTO) {
        if (service.getCategory(categoryDTO.getId()) != null) {
            return insertCategory(categoryDTO);
        } else
            throw new NotFoundException();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteOne(@PathVariable("id") long id) {
        service.delete(id);
    }

    private WithdrawalCategoryDTO insertCategory(WithdrawalCategoryDTO categoryDTO) {
        WithdrawalCategory category = new WithdrawalCategory();
        category.setId(categoryDTO.getId());
        category.setName(categoryDTO.getName());
        category.setUserId(categoryDTO.getUserId());
        category.setDescription(categoryDTO.getDescription());
        category = service.save(category);
        return new WithdrawalCategoryDTO(category.getId(), category.getUserId(), category.getName(), category.getDescription());
    }
}
