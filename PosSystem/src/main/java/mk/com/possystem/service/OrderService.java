package mk.com.possystem.service;

import mk.com.possystem.models.Dto.ApplyCustomerDiscount;
import mk.com.possystem.models.Dto.EmployeeDto;
import mk.com.possystem.models.Dto.OrderDto;
import mk.com.possystem.models.Dto.OrderFinished;
import mk.com.possystem.models.Order;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {
    Order createOrder(OrderDto orderDto);
    Order updateOrder(Long id,OrderDto orderDto);
    void deleteOrder(Long id);
    Order getOrder(Long id);
    List<Order> getOrders();
    List<Order> getOrdersForCustomer(Long customerId);
    List<Order> getOrdersByEmployee(String employeeId);
    void addCustomerToOrder(Long orderId, Long customerId);
    Order finishOrder(OrderFinished orderFinished);
    Order applyEmpDiscount(OrderFinished orderFinished);
    Order applyCustomerDiscount(ApplyCustomerDiscount applyCustomerDiscount);
}
