package mk.com.possystem.service.impl;

import mk.com.possystem.models.Customer;
import mk.com.possystem.models.Dto.CustomerDto;
import mk.com.possystem.models.exceptions.CustomerNotFoundException;
import mk.com.possystem.repository.CustomerRepository;
import mk.com.possystem.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer createCustomer(CustomerDto customerDto) {
        Customer customer = new Customer(
                customerDto.getName(),customerDto.getEmail(),
                customerDto.getPhoneNumber()
        );
        customerRepository.save(customer);
        return customer;
    }

    @Override
    public Customer updateCustomer(Long id, CustomerDto customerDto) {
        Customer customer = this.customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        customerRepository.save(customer);
        return customer;
    }

    @Override
    public void deleteCustomer(Long id) {
        this.customerRepository.deleteById(id);
    }

    @Override
    public Customer getCustomer(Long id) {
        Customer customer = this.customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
        return customer;
    }

    @Override
    public List<Customer> getCustomers() {
        return this.customerRepository.findAll();
    }

    @Override
    public Customer getCustomerByEmail(String email) {
        Customer customer = this.customerRepository.findByEmail(email);
        return customer;
    }
}
