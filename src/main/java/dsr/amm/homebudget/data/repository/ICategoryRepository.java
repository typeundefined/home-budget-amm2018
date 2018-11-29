package dsr.amm.homebudget.data.repository;

import dsr.amm.homebudget.data.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Trokhin
 *
 * Здесь я решил использовать {@code JpaRepository}, хотя пока это и не сильно оправдано, но в будущем может пригодиться.
 */

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {
}
