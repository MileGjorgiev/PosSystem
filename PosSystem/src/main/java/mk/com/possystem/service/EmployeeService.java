package mk.com.possystem.service;

import mk.com.possystem.models.Dto.EmployeeDto;
import mk.com.possystem.models.Employee;
import mk.com.possystem.models.enumerations.Role;

import java.util.List;

public interface EmployeeService {
    Employee register(EmployeeDto employeeDto);
    String getCurrentUsername();
    Employee getEmployeeByUsername(String username);

    List<Employee> getAllEmployees();
    List<Employee> getEmployeesByRole(Role role);
    void deleteEmployee(String username);
}