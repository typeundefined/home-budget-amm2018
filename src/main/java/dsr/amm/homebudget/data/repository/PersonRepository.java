package dsr.amm.homebudget.data.repository;

import dsr.amm.homebudget.data.entity.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {
    Optional<Person> findByUsername(String name);
}
