package mk.com.possystem.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class ItemInOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    @ManyToOne
    private Item item;

    @ManyToOne
    @JsonBackReference
    private Order order;

    public ItemInOrder(int quantity, Item item, Order order) {
        this.quantity = quantity;
        this.item = item;
        this.order = order;
    }




}
