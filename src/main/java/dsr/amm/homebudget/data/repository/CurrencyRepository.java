package dsr.amm.homebudget.data.repository;

import dsr.amm.homebudget.data.entity.Currency;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// Currency repository
@Repository
public interface CurrencyRepository extends CrudRepository<Currency, String> {
}