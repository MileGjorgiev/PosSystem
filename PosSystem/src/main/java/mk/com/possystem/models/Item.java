package mk.com.possystem.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.com.possystem.models.enumerations.ItemType;
import mk.com.possystem.models.enumerations.TypeSex;

@Entity
@Data
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private int quantityInStock;
    private Long price;
    private int numberOfOrders;

    @Enumerated(EnumType.STRING)
    private ItemType itemType;

    @Enumerated(EnumType.STRING)
    private TypeSex typeSex;

    private String itemImage;


    public Item(String name,String description,
                int quantityInStock, Long price,
                ItemType itemType,
                TypeSex typeSex,String itemImage) {
        this.name = name;
        this.description = description;
        this.quantityInStock = quantityInStock;
        this.price = price;
        this.itemType = itemType;
        this.itemImage = itemImage;
        this.typeSex = typeSex;
        this.numberOfOrders = 0;

    }




}
