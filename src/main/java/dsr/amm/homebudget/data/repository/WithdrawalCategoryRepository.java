package dsr.amm.homebudget.data.repository;

import dsr.amm.homebudget.data.entity.WithdrawalCategory;
import org.springframework.data.repository.CrudRepository;

public interface WithdrawalCategoryRepository extends CrudRepository<WithdrawalCategory, Long> {
    WithdrawalCategory findWithdrawalCategoriesById(Long id);
    Iterable<WithdrawalCategory> findAll();
}
