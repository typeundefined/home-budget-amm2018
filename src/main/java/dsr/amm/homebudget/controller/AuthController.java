package dsr.amm.homebudget.controller;

import dsr.amm.homebudget.data.dto.LoginDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    @RequestMapping(path="/login", method = RequestMethod.POST)
    public void login(@RequestBody LoginDTO loginDTO) {
        LOG.debug("Login attempt: {}", loginDTO.getUsername());
        // FIXME: call the proper service!
    }

}
