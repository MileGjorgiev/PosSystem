package mk.com.possystem.service.impl;

import mk.com.possystem.models.Customer;
import mk.com.possystem.models.Dto.ApplyCustomerDiscount;
import mk.com.possystem.models.Dto.EmployeeDto;
import mk.com.possystem.models.Dto.OrderDto;
import mk.com.possystem.models.Dto.OrderFinished;
import mk.com.possystem.models.Employee;
import mk.com.possystem.models.ItemInOrder;
import mk.com.possystem.models.Order;
import mk.com.possystem.models.exceptions.CustomerNotFoundException;
import mk.com.possystem.models.exceptions.EmployeeNotFoundException;
import mk.com.possystem.models.exceptions.OrderNotFoundException;
import mk.com.possystem.repository.*;
import mk.com.possystem.service.OrderService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final ItemRepository itemRepository;
    private final ItemInOrderRepository itemInOrderRepository;

    public OrderServiceImpl(OrderRepository orderRepository, CustomerRepository customerRepository
            , EmployeeRepository employeeRepository,
                            ItemRepository itemRepository, ItemInOrderRepository itemInOrderRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
        this.itemRepository = itemRepository;
        this.itemInOrderRepository = itemInOrderRepository;
    }

    @Override
    public Order createOrder(OrderDto orderDto) {
        if (orderDto == null) {
            throw new IllegalArgumentException("OrderDto cannot be null");
        }

        Employee employee = this.employeeRepository.findByUsername(orderDto.getUsername())
                .orElseThrow(() -> new EmployeeNotFoundException(orderDto.getUsername()));

        if (employee == null) {
            throw new IllegalArgumentException("Employee in OrderDto cannot be null");
        }

        Order order = new Order(employee);
        this.orderRepository.save(order);
        return order;
    }

    @Override
    public Order updateOrder(Long id, OrderDto orderDto) {
        Order order = this.orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        Employee employee = this.employeeRepository.findByUsername(orderDto.getUsername()).
                orElseThrow(() -> new EmployeeNotFoundException(orderDto.getUsername()));
        order.setEmployee(employee);
        this.orderRepository.save(order);
        return order;
    }

    @Override
    public void deleteOrder(Long id) {
        Order order = this.orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        List<ItemInOrder> itemInOrders = order.getItemInOrders();
        this.itemInOrderRepository.deleteAll(itemInOrders);
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
        if (employeeId.isEmpty() || employeeId.equals("All")) {
            return this.orderRepository.findAll();
        }

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

    @Override
    public Order finishOrder(OrderFinished orderFinished) {
        Order order = this.orderRepository.findById(orderFinished.getId())
                .orElseThrow(() -> new OrderNotFoundException(orderFinished.getId()));
        Customer customer = order.getCustomer();

        for (ItemInOrder item : order.getItemInOrders()) {
            if (customer != null) {
                customer.setPoints(customer.getPoints() + (item.getQuantity() * 5));
            }
            item.getItem().setNumberOfOrders(item.getItem().getNumberOfOrders() + item.getQuantity());
            item.getItem().setQuantityInStock(item.getItem().getQuantityInStock() - item.getQuantity());

            if (item.getItem().getQuantityInStock() <= 0) {
                item.getItem().setQuantityInStock(0);
            }
            
            this.itemRepository.save(item.getItem());
        }
        if (customer != null) {
            this.customerRepository.save(customer);
        }

        Employee employee = this.employeeRepository.findByUsername(orderFinished.getUsername())
                .orElseThrow(() -> new EmployeeNotFoundException(orderFinished.getUsername()));
        Order newOrder = new Order(employee);
        this.orderRepository.save(newOrder);
        return newOrder;

    }


    @Override
    public Order applyEmpDiscount(OrderFinished orderFinished) {
        Order order = this.orderRepository.findById(orderFinished.getId()).orElseThrow(() -> new OrderNotFoundException(orderFinished.getId()));
        Employee employee = this.employeeRepository.findByUsername(orderFinished.getUsername()).orElseThrow(() -> new EmployeeNotFoundException(orderFinished.getUsername()));
        double discount = employee.getEmployeeDiscount() / 100.0;
        double discountAmount = order.getTotalPrice() * discount;

        order.setTotalPrice(order.getTotalPrice() - (int) discountAmount);

        this.orderRepository.save(order);
        return order;
    }

    @Override
    public Order applyCustomerDiscount(ApplyCustomerDiscount applyCustomerDiscount) {
        Order order = this.orderRepository.findById(applyCustomerDiscount.getId())
                .orElseThrow(() -> new OrderNotFoundException(applyCustomerDiscount.getId()));

        Customer customer = this.customerRepository.findById(applyCustomerDiscount.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException(applyCustomerDiscount.getCustomerId()));

        int points = (int) (customer.getPoints() * 0.1);

        double discountPercentage = points / 100.0;
        double totalDiscount = order.getTotalPrice() * discountPercentage;

        int newTotalPrice = (int) Math.round(order.getTotalPrice() - totalDiscount);

        newTotalPrice = Math.max(newTotalPrice, 0);

        order.setTotalPrice(newTotalPrice);
        customer.setPoints(0);

        this.customerRepository.save(customer);
        return this.orderRepository.save(order);
    }
}
