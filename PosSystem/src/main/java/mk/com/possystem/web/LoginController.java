package mk.com.possystem.web;

import jakarta.servlet.http.HttpServletRequest;
import mk.com.possystem.models.Employee;
import mk.com.possystem.models.exceptions.InvalidArgumentsException;
import mk.com.possystem.models.exceptions.InvalidUserCredentialsException;
import mk.com.possystem.service.AuthService;
import mk.com.possystem.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    private final AuthService authService;
    private final EmployeeService employeeService;

    public LoginController(AuthService authService, EmployeeService employeeService) {
        this.authService = authService;
        this.employeeService = employeeService;
    }


    @PostMapping
    public ResponseEntity<String> login(HttpServletRequest request) {
        Employee user;
        try {
            user = authService.login(request.getParameter("username"), request.getParameter("password"));
        } catch (InvalidUserCredentialsException | InvalidArgumentsException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }

        request.getSession().setAttribute("user", user);
        return ResponseEntity.ok("Login successful");
    }
}