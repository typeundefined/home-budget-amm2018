package dsr.amm.homebudget.service;

import dsr.amm.homebudget.OrikaMapper;
import dsr.amm.homebudget.data.dto.WithdrawalCategoryDTO;
import dsr.amm.homebudget.data.entity.WithdrawalCategory;
import dsr.amm.homebudget.data.repository.WithdrawalCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WithdrawalCategoryService {

    @Autowired
    WithdrawalCategoryRepository repository;

    @Autowired
    private OrikaMapper mapper;

    public WithdrawalCategoryDTO save(WithdrawalCategoryDTO category) {
        return mapper.map(repository.save(mapper.map(category, WithdrawalCategory.class)), WithdrawalCategoryDTO.class);
    }
    public void delete(Long id) {
        repository.deleteById(id);
    }

    public WithdrawalCategoryDTO getCategory(Long id) {
        return mapper.map(repository.findWithdrawalCategoriesById(id), WithdrawalCategoryDTO.class);
    }

    public List<WithdrawalCategoryDTO> getAll() {
        Iterable<WithdrawalCategory> accList = repository.findAll();
        return mapper.mapAsList(accList, WithdrawalCategoryDTO.class);
    }
}
