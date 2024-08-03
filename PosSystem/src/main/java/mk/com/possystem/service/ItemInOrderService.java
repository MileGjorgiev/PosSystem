package mk.com.possystem.service;

import mk.com.possystem.models.Dto.ItemInOrderDto;
import mk.com.possystem.models.ItemInOrder;

import java.util.List;

public interface ItemInOrderService {
    ItemInOrder findItemInOrder(Long id);
    ItemInOrder createItemInOrder(ItemInOrderDto itemInOrderDto);
    ItemInOrder updateItemInOrder(Long id,ItemInOrderDto itemInOrderDto);
    void deleteItemInOrder(Long id);
    List<ItemInOrder> findAllItemByOrderId(Long orderId);
}
