package dsr.amm.homebudget.controller;

import dsr.amm.homebudget.data.dto.CurrencyDTO;
import dsr.amm.homebudget.data.entity.Currency;
import dsr.amm.homebudget.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/currency")
public class CurrencyController {
    @Autowired
    private CurrencyService service;

    @RequestMapping(method = RequestMethod.GET)
    public List<CurrencyDTO> getAll() {
        return service.getCurrencies();
    }

    @RequestMapping(method = RequestMethod.POST)
    public void create(@RequestBody @Valid CurrencyDTO curr) {
        service.create(curr);
    }
}
