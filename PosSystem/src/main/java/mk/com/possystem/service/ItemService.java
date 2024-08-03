package mk.com.possystem.service;

import mk.com.possystem.models.Dto.ItemDto;
import mk.com.possystem.models.Item;
import mk.com.possystem.models.enumerations.ItemType;
import mk.com.possystem.models.enumerations.TypeSex;

import java.util.List;

public interface ItemService {
    Item getItem(Long id);
    List<Item> getAllItems();
    List<Item> getItemsByCategory(ItemType itemType);
    List<Item> getItemsByTypeSex(TypeSex typeSex);
    List<Item> getItemsByName(String name);
    List<Item> getItemsByDescription(String description);
    Item createItem(ItemDto itemDto,String image);
    Item updateItem(Long id, ItemDto itemDto);
    void deleteItem(Long id);
    List<Item> filter(ItemType itemType, String name, String description, TypeSex typeSex);


}
