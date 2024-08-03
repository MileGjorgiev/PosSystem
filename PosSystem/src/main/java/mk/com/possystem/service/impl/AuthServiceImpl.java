package mk.com.possystem.service.impl;

import mk.com.possystem.models.Employee;
import mk.com.possystem.models.exceptions.InvalidArgumentsException;
import mk.com.possystem.models.exceptions.InvalidUserCredentialsException;
import mk.com.possystem.repository.EmployeeRepository;
import mk.com.possystem.service.AuthService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {
    private final EmployeeRepository userRepository;

    public AuthServiceImpl(EmployeeRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Employee login(String username, String password) {
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            throw new InvalidArgumentsException();
        }

        return userRepository.findByUsernameAndPassword(username, password)
                .orElseThrow(InvalidUserCredentialsException::new);
    }

    @Override
    public List<Employee> findAll() {
        return userRepository.findAll();
    }

}
