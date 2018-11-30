package dsr.amm.homebudget.data.repository;

import dsr.amm.homebudget.data.entity.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    Category findCategoryById(Long id);
}
