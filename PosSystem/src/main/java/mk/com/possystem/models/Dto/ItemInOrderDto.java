package mk.com.possystem.models.Dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.com.possystem.models.Item;
import mk.com.possystem.models.Order;

@Data
@NoArgsConstructor
public class ItemInOrderDto {
    private int quantity;

    @ManyToOne
    private Item item;

    @ManyToOne
    @JsonBackReference
    private Order order;

    public ItemInOrderDto(int quantity, Item item, Order order) {
        this.quantity = quantity;
        this.item = item;
        this.order = order;
    }
}
