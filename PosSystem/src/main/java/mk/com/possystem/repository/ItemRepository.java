package mk.com.possystem.repository;

import mk.com.possystem.models.Item;
import mk.com.possystem.models.enumerations.ItemType;
import mk.com.possystem.models.enumerations.TypeSex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Type;
import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByItemType(ItemType itemType);
    List<Item> findAllByTypeSex(TypeSex typeSex);
    List<Item> findByNameContaining(String name);
    List<Item> findAllByDescriptionContaining(String description);
    List<Item> findAllByItemTypeAndTypeSex(ItemType itemType, TypeSex typeSex);
}
