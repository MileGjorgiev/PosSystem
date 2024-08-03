package mk.com.possystem.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime creationDate;
    private LocalDateTime validUntil;
    private Long discountAmount;

    @ManyToMany
    List<Item> items;

    public Discount(LocalDateTime validUntil,
                    Long discountAmount){
        creationDate = LocalDateTime.now();
        this.validUntil = validUntil;
        this.discountAmount = discountAmount;
        items = new ArrayList<>();
    }

}
