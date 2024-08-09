package mk.com.possystem.models.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderFinished {
    private Long id;
    private String username;

    public OrderFinished(Long id, String username) {
        this.id = id;
        this.username = username;
    }
}
