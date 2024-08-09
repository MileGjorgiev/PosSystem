package mk.com.possystem.service.impl;

import mk.com.possystem.models.Employee;
import mk.com.possystem.models.exceptions.InvalidArgumentsException;
import mk.com.possystem.models.exceptions.InvalidUserCredentialsException;
import mk.com.possystem.repository.EmployeeRepository;
import mk.com.possystem.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {
    private final EmployeeRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(EmployeeRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Employee login(String username, String password) {
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            throw new InvalidArgumentsException();
        }

        Employee user = userRepository.findByUsername(username)
                .orElseThrow(InvalidUserCredentialsException::new);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidUserCredentialsException();
        }

        return user;
    }

    @Override
    public List<Employee> findAll() {
        return userRepository.findAll();
    }

}
