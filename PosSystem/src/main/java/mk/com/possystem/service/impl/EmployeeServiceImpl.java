package mk.com.possystem.service.impl;

import mk.com.possystem.models.Dto.EmployeeDto;
import mk.com.possystem.models.Employee;
import mk.com.possystem.models.enumerations.Role;
import mk.com.possystem.models.exceptions.EmployeeNotFoundException;
import mk.com.possystem.models.exceptions.InvalidUsernameOrPasswordException;
import mk.com.possystem.models.exceptions.UsernameAlreadyExistsException;
import mk.com.possystem.repository.EmployeeRepository;
import mk.com.possystem.service.EmployeeService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService, UserDetailsService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository,PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Employee register(EmployeeDto employeeDto) {
        if (employeeDto.getUsername() == null || employeeDto.getPassword() == null
                || employeeDto.getUsername().isEmpty() || employeeDto.getPassword().isEmpty()) {
            throw new InvalidUsernameOrPasswordException();
        }


        if(this.employeeRepository.findByUsername(employeeDto.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException(employeeDto.getUsername());
        }

        Employee user = new Employee(
                employeeDto.getUsername(),
                passwordEncoder.encode(employeeDto.getPassword()),
                employeeDto.getName(),
                employeeDto.getRole()
        );

        return employeeRepository.save(user);
    }

    @Override
    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                return ((UserDetails) principal).getUsername();
            } else {
                return principal.toString();
            }
        }
        return null;
    }

    @Override
    public Employee getEmployeeByUsername(String username) {
        Employee employee = this.employeeRepository.findByUsername(username).orElseThrow(() -> new EmployeeNotFoundException(username));
        return employee;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return this.employeeRepository.findAll();
    }

    @Override
    public List<Employee> getEmployeesByRole(Role role) {
        return this.employeeRepository.findAllByRole(role);
    }

    @Override
    public void deleteEmployee(String username) {
        Employee employee = this.employeeRepository.findByUsername(username).orElseThrow(() -> new EmployeeNotFoundException(username));
        this.employeeRepository.delete(employee);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return employeeRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException(username));
    }
}
