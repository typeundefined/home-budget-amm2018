package dsr.amm.homebudget;

import dsr.amm.homebudget.service.PrincipalDetailsService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;

@SpringBootApplication
public class HomeBudgetApplication {

    @Bean
    public UserDetailsService userDetailsService() {
        return new PrincipalDetailsService();
    }

    public static void main(String[] args) {
        SpringApplication.run(HomeBudgetApplication.class, args);
    }
}
