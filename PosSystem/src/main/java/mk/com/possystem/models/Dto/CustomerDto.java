package mk.com.possystem.models.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerDto {
    private String name;
    private String email;
    private String phoneNumber;
    private int points;


    public CustomerDto(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.points = 0;
    }
}
