package mk.com.possystem.web;

import mk.com.possystem.models.Customer;
import mk.com.possystem.models.Dto.CustomerDto;
import mk.com.possystem.models.Dto.ItemDto;
import mk.com.possystem.models.Item;
import mk.com.possystem.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerController {

    private final CustomerService customerService;
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getCustomers() {
        return customerService.getCustomers();
    }

    @PostMapping("/add")
    public ResponseEntity<Customer> addCustomer(@RequestBody CustomerDto customerDto) {
        Customer customer = this.customerService.createCustomer(customerDto);
        if(customer != null){
            return ResponseEntity.ok(customer);
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Customer> update(@PathVariable Long id, @RequestBody CustomerDto customerDto) {
        Customer customer = this.customerService.updateCustomer(id,customerDto);
        if (customer != null) {
            return ResponseEntity.ok().body(customer);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Customer> delete(@PathVariable Long id) {
        try {
            this.customerService.deleteCustomer(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getItem(@PathVariable Long id) {
        Customer customer = this.customerService.getCustomer(id);
        if (customer != null) {
            return ResponseEntity.ok().body(customer);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/emailSearch")
    public ResponseEntity<Customer> getItemByEmail(@RequestBody CustomerDto customerDto) {
        Customer customer = this.customerService.getCustomerByEmail(customerDto.getEmail());
        if (customer != null) {
            return ResponseEntity.ok().body(customer);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }



}
