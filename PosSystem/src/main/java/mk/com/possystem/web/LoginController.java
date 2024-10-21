package mk.com.possystem.web;

import jakarta.servlet.http.HttpServletRequest;
import mk.com.possystem.models.Dto.LoginRequest;
import mk.com.possystem.models.Employee;
import mk.com.possystem.models.exceptions.InvalidArgumentsException;
import mk.com.possystem.models.exceptions.InvalidUserCredentialsException;
import mk.com.possystem.service.AuthService;
import mk.com.possystem.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
@CrossOrigin(origins = "http://localhost:3000")
public class LoginController {

    private final AuthService authService;
    private final EmployeeService employeeService;

    public LoginController(AuthService authService, EmployeeService employeeService) {
        this.authService = authService;
        this.employeeService = employeeService;
    }


    @PostMapping
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        try {
            Employee user = authService.login(loginRequest.getUsername(), loginRequest.getPassword());
            return ResponseEntity.ok(user.getUsername());
        } catch (InvalidUserCredentialsException | InvalidArgumentsException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

}