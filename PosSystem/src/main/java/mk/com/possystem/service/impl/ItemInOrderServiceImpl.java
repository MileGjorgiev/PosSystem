package mk.com.possystem.service.impl;

import mk.com.possystem.models.Dto.ItemInOrderDto;
import mk.com.possystem.models.ItemInOrder;
import mk.com.possystem.models.Order;
import mk.com.possystem.models.exceptions.ItemInOrderNotFoundException;
import mk.com.possystem.models.exceptions.OrderNotFoundException;
import mk.com.possystem.repository.ItemInOrderRepository;
import mk.com.possystem.repository.OrderRepository;
import mk.com.possystem.service.ItemInOrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemInOrderServiceImpl implements ItemInOrderService {

    private final ItemInOrderRepository itemInOrderRepository;
    private final OrderRepository orderRepository;

    public ItemInOrderServiceImpl(ItemInOrderRepository itemInOrderRepository,OrderRepository orderRepository) {
        this.itemInOrderRepository = itemInOrderRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public ItemInOrder findItemInOrder(Long id) {
        ItemInOrder itemInOrder = this.itemInOrderRepository.findById(id).orElseThrow(() -> new ItemInOrderNotFoundException(id));
        return itemInOrder;
    }

    @Override
    public ItemInOrder createItemInOrder(ItemInOrderDto itemInOrderDto) {
        ItemInOrder itemInOrder = new ItemInOrder(itemInOrderDto.getQuantity(),itemInOrderDto.getItem(),
                itemInOrderDto.getOrder());
        this.itemInOrderRepository.save(itemInOrder);
        return itemInOrder;
    }

    @Override
    public ItemInOrder updateItemInOrder(Long id, ItemInOrderDto itemInOrderDto) {
        ItemInOrder itemInOrder = this.itemInOrderRepository.findById(id).orElseThrow(() -> new ItemInOrderNotFoundException(id));
        itemInOrder.setQuantity(itemInOrderDto.getQuantity());
        itemInOrder.setItem(itemInOrderDto.getItem());
        itemInOrder.setOrder(itemInOrderDto.getOrder());
        this.itemInOrderRepository.save(itemInOrder);
        return itemInOrder;
    }

    @Override
    public void deleteItemInOrder(Long id) {
        ItemInOrder itemInOrder = this.itemInOrderRepository.findById(id).orElseThrow(() -> new ItemInOrderNotFoundException(id));
        this.itemInOrderRepository.delete(itemInOrder);
    }

    @Override
    public List<ItemInOrder> findAllItemByOrderId(Long orderId) {
        Order order = this.orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
        return this.itemInOrderRepository.findAllByOrder(order);
    }
}
