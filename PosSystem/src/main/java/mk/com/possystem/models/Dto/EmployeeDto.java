package mk.com.possystem.models.Dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.com.possystem.models.enumerations.Role;

@NoArgsConstructor
@Data
public class EmployeeDto {

    private String username;
    private String password;
    private String name;


    @Enumerated(EnumType.STRING)
    private Role role;

    public EmployeeDto(String username, String password, String name, Role role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.role = role;
    }

}
