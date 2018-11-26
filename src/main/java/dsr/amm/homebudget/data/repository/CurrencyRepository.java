package dsr.amm.homebudget.data.repository;

import org.springframework.stereotype.Repository;
import dsr.amm.homebudget.data.entity.Currency;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface CurrencyRepository extends CrudRepository<Currency, String>{
}
