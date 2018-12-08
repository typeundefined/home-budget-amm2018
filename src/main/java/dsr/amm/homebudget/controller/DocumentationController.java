package dsr.amm.homebudget.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(path = "/docs")
public class DocumentationController {
    @RequestMapping(method = RequestMethod.GET)
    public String getDocs() {
        return "documentation.html";
    }
}
