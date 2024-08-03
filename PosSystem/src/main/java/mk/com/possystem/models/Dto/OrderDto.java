package mk.com.possystem.models.Dto;



import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

import mk.com.possystem.models.Employee;


@Data
@NoArgsConstructor
public class OrderDto {

    @ManyToOne
    @JoinColumn(name = "employee_username", referencedColumnName = "username")
    Employee employee;


    public OrderDto(Employee employee){
        this.employee = employee;
    }
}