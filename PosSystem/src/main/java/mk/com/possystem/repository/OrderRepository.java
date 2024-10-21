package mk.com.possystem.repository;

import mk.com.possystem.models.Customer;
import mk.com.possystem.models.Employee;
import mk.com.possystem.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByCustomer(Customer customer);

    List<Order> findAllByEmployee(Employee employee);





}