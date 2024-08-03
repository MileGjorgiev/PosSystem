package mk.com.possystem.repository;

import mk.com.possystem.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
Customer findByEmail(String email);
}
