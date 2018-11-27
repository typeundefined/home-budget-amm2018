package dsr.amm.homebudget.controller;

import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;

import dsr.amm.homebudget.data.dto.CurrencyDTO;
import dsr.amm.homebudget.service.CurrencyService;

import javax.validation.Valid;
import java.util.List;

//Currency controller
@RestController
@RequestMapping("/currency")
public class CurrencyController {

    @Autowired
    private CurrencyService service;

    // Get currency
    @RequestMapping(method = RequestMethod.GET)
    public List<CurrencyDTO> getCurrency() {
        return service.getCurrencies();
    }

    // Post currency
    @RequestMapping(method = RequestMethod.POST)
    public void createCurrency(@RequestBody @Valid CurrencyDTO curr) {
        service.create(curr);
    }

    // Delete currency
    @GetMapping("/currency/{id}")
    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteCurrency(@PathVariable long id) {
        service.delete(id);
    }
}
