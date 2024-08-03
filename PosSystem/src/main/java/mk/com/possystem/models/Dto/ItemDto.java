package mk.com.possystem.models.Dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.com.possystem.models.enumerations.ItemType;
import mk.com.possystem.models.enumerations.TypeSex;

@Data
@NoArgsConstructor
public class ItemDto {

    private String name;
    private String description;
    private int quantityInStock;
    private Long price;

    @Enumerated(EnumType.STRING)
    private ItemType itemType;

    @Enumerated(EnumType.STRING)
    private TypeSex typeSex;

    private String itemImage;

    public ItemDto(String name,String description,
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

    }

}
