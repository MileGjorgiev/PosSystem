package mk.com.possystem.web;

import mk.com.possystem.models.Dto.OrderDto;
import mk.com.possystem.models.Order;
import mk.com.possystem.service.CustomerService;
import mk.com.possystem.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final CustomerService customerService;

    public OrderController(OrderService orderService,
                           CustomerService customerService) {
        this.orderService = orderService;
        this.customerService = customerService;
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return this.orderService.getOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        Order order = this.orderService.getOrder(id);
        if (order != null){
            return ResponseEntity.ok(order);
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Order> deleteOrder(@PathVariable Long id) {
        try{
            this.orderService.deleteOrder(id);
            return ResponseEntity.ok().build();
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Order> addOrder(@RequestBody OrderDto orderDto) {
        Order order = this.orderService.createOrder(orderDto);
        if (order != null){
            return ResponseEntity.ok(order);
        }
        return ResponseEntity.badRequest().build();
    }
    @PutMapping("/edit/{id}")
    public ResponseEntity<Order> editOrder(@PathVariable Long id, @RequestBody OrderDto orderDto) {
        Order order = this.orderService.updateOrder(id, orderDto);
        if (order != null){
            return ResponseEntity.ok(order);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/customerOrders/{customerId}")
    public List<Order> getCustomerOrders(@PathVariable Long customerId) {
        return this.orderService.getOrdersForCustomer(customerId);
    }

    @GetMapping("/employeeOrders/{employeeId}")
    public List<Order> getEmployeeOrders(@PathVariable String employeeId) {
        return this.orderService.getOrdersByEmployee(employeeId);
    }

    @PostMapping("/addCustomer/{id}")
    public ResponseEntity<Order> addCustomer(@PathVariable Long id, @RequestParam Long customerId) {
        try {
            this.orderService.addCustomerToOrder(id, customerId);
            return ResponseEntity.ok().build();
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
