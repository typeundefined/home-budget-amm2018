package dsr.amm.homebudget;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by knekrasov on 10/15/2018.
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // TODO [KN] setup a proper authorization rules afterwards
        http.antMatcher("/**").anonymous().and().csrf().disable();
    }
}
