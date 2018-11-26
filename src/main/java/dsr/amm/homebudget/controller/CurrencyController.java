package dsr.amm.homebudget.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.beans.factory.annotation.Autowired;

import dsr.amm.homebudget.data.dto.CurrencyDTO;
import dsr.amm.homebudget.service.CurrencyService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/currency")
public class CurrencyController {
    @Autowired
    private CurrencyService service;

    @RequestMapping(method = RequestMethod.GET)
    public List<CurrencyDTO> getAll() {
        return service.getCurrencies();
    }

    @RequestMapping(method = RequestMethod.POST)
    public void getAll(@RequestBody @Valid CurrencyDTO curr) {
        service.create(curr);
    }
}
