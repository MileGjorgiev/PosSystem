package mk.com.possystem.models;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime validUntil;
    private Long discountAmount;

    @ManyToMany(fetch = FetchType.EAGER)
    List<Item> items;

    public Discount(LocalDateTime validUntil,
                    Long discountAmount){
        creationDate = LocalDateTime.now();
        this.validUntil = validUntil;
        this.discountAmount = discountAmount;
        items = new ArrayList<>();
    }

}
