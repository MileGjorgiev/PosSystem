package mk.com.possystem.service;

import mk.com.possystem.models.Employee;

import java.util.List;

public interface AuthService {
    Employee login(String username, String password);

    List<Employee> findAll();

}