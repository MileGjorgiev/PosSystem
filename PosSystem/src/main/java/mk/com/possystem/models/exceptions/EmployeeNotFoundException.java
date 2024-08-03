package mk.com.possystem.models.exceptions;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(String employeeId) {
        super(String.format("Employee with id %s not found", employeeId));
    }
}
