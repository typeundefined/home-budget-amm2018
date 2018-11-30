package dsr.amm.homebudget.service;

import dsr.amm.homebudget.data.entity.WithdrawalCategory;
import dsr.amm.homebudget.data.repository.WithdrawalCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WithdrawalCategoryService {

    @Autowired
    WithdrawalCategoryRepository repository;

    public WithdrawalCategory save(WithdrawalCategory category) {
        return repository.save(category);
    }
    public void delete(Long id) {
        repository.deleteById(id);
    }

    public WithdrawalCategory getCategory(Long id) {
        return repository.findWithdrawalCategoriesById(id);
    }

    public Iterable<WithdrawalCategory> getAll() {
        return repository.findAll();
    }
}
