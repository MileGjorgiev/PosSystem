package mk.com.possystem.service;

import mk.com.possystem.models.Customer;
import mk.com.possystem.models.Dto.CustomerDto;

import java.util.List;

public interface CustomerService {
    Customer createCustomer(CustomerDto customerDto);
    Customer updateCustomer(Long id,CustomerDto customerDto);
    void deleteCustomer(Long id);
    Customer getCustomer(Long id);
    List<Customer> getCustomers();
    Customer getCustomerByEmail(String email);
}
