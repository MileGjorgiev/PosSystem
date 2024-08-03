package mk.com.possystem.repository;

import mk.com.possystem.models.Employee;
import mk.com.possystem.models.enumerations.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    Optional<Employee> findByUsername(String username);

    Optional<Employee> findByUsernameAndPassword(String username, String password);
    List<Employee> findAllByRole(Role role);
}
