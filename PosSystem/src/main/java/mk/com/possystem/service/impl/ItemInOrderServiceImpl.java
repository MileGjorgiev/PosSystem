package mk.com.possystem.service.impl;

import mk.com.possystem.models.Dto.ItemInOrderDto;
import mk.com.possystem.models.Item;
import mk.com.possystem.models.ItemInOrder;
import mk.com.possystem.models.Order;
import mk.com.possystem.models.exceptions.ItemInOrderNotFoundException;
import mk.com.possystem.models.exceptions.OrderNotFoundException;
import mk.com.possystem.repository.ItemInOrderRepository;
import mk.com.possystem.repository.ItemRepository;
import mk.com.possystem.repository.OrderRepository;
import mk.com.possystem.service.ItemInOrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemInOrderServiceImpl implements ItemInOrderService {

    private final ItemInOrderRepository itemInOrderRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository ItemRepository;

    public ItemInOrderServiceImpl(ItemInOrderRepository itemInOrderRepository,OrderRepository orderRepository,
                                  ItemRepository ItemRepository) {
        this.itemInOrderRepository = itemInOrderRepository;
        this.orderRepository = orderRepository;
        this.ItemRepository = ItemRepository;
    }

    @Override
    public List<ItemInOrder> getItemInOrder() {
        return itemInOrderRepository.findAll();
    }

    @Override
    public ItemInOrder findItemInOrder(Long id) {
        ItemInOrder itemInOrder = this.itemInOrderRepository.findById(id).orElseThrow(() -> new ItemInOrderNotFoundException(id));
        return itemInOrder;
    }

    @Override
    public ItemInOrder createItemInOrder(ItemInOrderDto itemInOrderDto) {
        Item item = this.ItemRepository.findById(itemInOrderDto.getItem())
                .orElseThrow(() -> new ItemInOrderNotFoundException(itemInOrderDto.getItem()));
        Order order = this.orderRepository.findById(itemInOrderDto.getOrder())
                .orElseThrow(() -> new OrderNotFoundException(itemInOrderDto.getOrder()));
        ItemInOrder itemInOrder = new ItemInOrder(itemInOrderDto.getQuantity(),item,
                order);
        order.setTotalPrice((int) (order.getTotalPrice() + (item.getPrice() * itemInOrderDto.getQuantity())));
        this.itemInOrderRepository.save(itemInOrder);
        return itemInOrder;
    }

    @Override
    public ItemInOrder updateItemInOrder(Long id, ItemInOrderDto itemInOrderDto) {
        ItemInOrder itemInOrder = this.itemInOrderRepository.findById(id).orElseThrow(() -> new ItemInOrderNotFoundException(id));
        Item item = this.ItemRepository.findById(itemInOrderDto.getItem())
                .orElseThrow(() -> new ItemInOrderNotFoundException(itemInOrderDto.getItem()));
        Order order = this.orderRepository.findById(itemInOrderDto.getOrder())
                .orElseThrow(() -> new OrderNotFoundException(itemInOrderDto.getOrder()));

        itemInOrder.setQuantity(itemInOrderDto.getQuantity());
        itemInOrder.setItem(item);
        itemInOrder.setOrder(order);
        this.itemInOrderRepository.save(itemInOrder);
        return itemInOrder;
    }

    @Override
    public void deleteItemInOrder(Long id) {
        ItemInOrder itemInOrder = this.itemInOrderRepository.findById(id).orElseThrow(() -> new ItemInOrderNotFoundException(id));
        Order order = this.orderRepository.findById(itemInOrder.getOrder().getId()).orElseThrow(() -> new OrderNotFoundException(itemInOrder.getOrder().getId()));
        int price = (int) (itemInOrder.getQuantity() * itemInOrder.getItem().getPrice());
        int newTotalPrice = order.getTotalPrice() - price;


        if (newTotalPrice < 0) {
            order.setTotalPrice(0);
        } else {
            order.setTotalPrice(newTotalPrice);
        }




        this.orderRepository.save(order);
        this.itemInOrderRepository.delete(itemInOrder);
    }

    @Override
    public List<ItemInOrder> findAllItemByOrderId(Long orderId) {
        Order order = this.orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
        return this.itemInOrderRepository.findAllByOrder(order);
    }
}
