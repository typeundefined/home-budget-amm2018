package dsr.amm.homebudget.data.repository;

import dsr.amm.homebudget.data.entity.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
}
