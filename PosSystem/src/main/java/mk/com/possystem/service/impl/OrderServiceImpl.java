package mk.com.possystem.service.impl;

import mk.com.possystem.models.Customer;
import mk.com.possystem.models.Dto.OrderDto;
import mk.com.possystem.models.Employee;
import mk.com.possystem.models.Order;
import mk.com.possystem.models.exceptions.CustomerNotFoundException;
import mk.com.possystem.models.exceptions.EmployeeNotFoundException;
import mk.com.possystem.models.exceptions.OrderNotFoundException;
import mk.com.possystem.repository.CustomerRepository;
import mk.com.possystem.repository.EmployeeRepository;
import mk.com.possystem.repository.OrderRepository;
import mk.com.possystem.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;

    public OrderServiceImpl(OrderRepository orderRepository, CustomerRepository customerRepository
                            ,EmployeeRepository employeeRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Order createOrder(OrderDto orderDto) {
        Order order = new Order(orderDto.getEmployee());
        this.orderRepository.save(order);
        return order;
    }

    @Override
    public Order updateOrder(Long id, OrderDto orderDto) {
        Order order = this.orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        order.setEmployee(orderDto.getEmployee());
        this.orderRepository.save(order);
        return order;
    }

    @Override
    public void deleteOrder(Long id) {
        Order order = this.orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        this.orderRepository.delete(order);
    }

    @Override
    public Order getOrder(Long id) {
        Order order = this.orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        return order;
    }

    @Override
    public List<Order> getOrders() {
        return this.orderRepository.findAll();
    }

    @Override
    public List<Order> getOrdersForCustomer(Long customerId) {
        Customer customer = this.customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));
        return this.orderRepository.findAllByCustomer(customer);
    }

    @Override
    public List<Order> getOrdersByEmployee(String employeeId) {
        Employee employee = this.employeeRepository.findByUsername(employeeId).orElseThrow(() ->
                new EmployeeNotFoundException(employeeId));

        return this.orderRepository.findAllByEmployee(employee);
    }

    @Override
    public void addCustomerToOrder(Long orderId, Long customerId) {
        Customer customer = this.customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));
        Order order = this.orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
        order.setCustomer(customer);
        this.orderRepository.save(order);
    }
}
