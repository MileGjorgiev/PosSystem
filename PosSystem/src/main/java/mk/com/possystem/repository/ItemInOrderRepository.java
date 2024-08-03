package mk.com.possystem.repository;

import mk.com.possystem.models.ItemInOrder;
import mk.com.possystem.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemInOrderRepository extends JpaRepository<ItemInOrder, Long> {
    List<ItemInOrder> findAllByOrder(Order order);
}
