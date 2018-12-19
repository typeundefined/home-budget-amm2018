package dsr.amm.homebudget.controller;

import dsr.amm.homebudget.data.dto.CurrencyDTO;
import dsr.amm.homebudget.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/currency", consumes = MediaType.APPLICATION_JSON_VALUE)
public class CurrencyController {
    @Autowired
    private CurrencyService service;

    @RequestMapping(method = RequestMethod.GET)
    public List<CurrencyDTO> getAll() {
        return service.getCurrencies();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public CurrencyDTO create(@RequestBody @Valid CurrencyDTO curr) {
        return service.create(curr);
    }
}
