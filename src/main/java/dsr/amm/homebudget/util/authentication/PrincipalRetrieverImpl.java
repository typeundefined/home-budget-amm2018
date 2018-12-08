package dsr.amm.homebudget.util.authentication;

import dsr.amm.homebudget.service.Principal;
import org.springframework.security.core.context.SecurityContextHolder;

public class PrincipalRetrieverImpl implements PrincipalRetriever{

    @Override
    public Principal getPrincipal() {
        return (Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
