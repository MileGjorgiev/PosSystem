package mk.com.possystem.web;

import mk.com.possystem.models.Dto.EmployeeDto;
import mk.com.possystem.models.Employee;
import mk.com.possystem.models.enumerations.Role;
import mk.com.possystem.models.exceptions.InvalidArgumentsException;
import mk.com.possystem.models.exceptions.UsernameAlreadyExistsException;
import mk.com.possystem.service.AuthService;
import mk.com.possystem.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/register")
public class RegisterController {

    private final AuthService authService;
    private final EmployeeService userService;

    public RegisterController(AuthService authService, EmployeeService userService) {
        this.authService = authService;
        this.userService = userService;
    }



    @PostMapping
    public ResponseEntity<String> register(@RequestBody EmployeeDto employeeDto) {
        try {
            this.userService.register(employeeDto);
            return ResponseEntity.ok("Registration successful. Please login.");
        } catch (InvalidArgumentsException | UsernameAlreadyExistsException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }
}