package dsr.amm.homebudget.controller;

import dsr.amm.homebudget.data.dto.PersonDTO;
import dsr.amm.homebudget.service.PrincipalDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/person")
public class PersonController {

    @Autowired
    private PrincipalDetailsService service;

    @RequestMapping(method = RequestMethod.GET)
    public List<PersonDTO> getAllPersons() {
        return service.getAllUsers();
    }

}
