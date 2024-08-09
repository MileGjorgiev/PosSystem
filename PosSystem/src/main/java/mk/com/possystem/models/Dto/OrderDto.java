package mk.com.possystem.models.Dto;



import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

import mk.com.possystem.models.Employee;


@Data
@NoArgsConstructor
public class OrderDto {


    String username;


    public OrderDto(String employee){
        this.username = employee;
    }
}
