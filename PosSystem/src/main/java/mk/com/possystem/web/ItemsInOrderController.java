package mk.com.possystem.web;

import mk.com.possystem.models.Dto.ItemInOrderDto;
import mk.com.possystem.models.ItemInOrder;
import mk.com.possystem.service.ItemInOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/itemsInOrder")
@CrossOrigin(origins = "http://localhost:3000")
public class ItemsInOrderController {

    private final ItemInOrderService itemInOrderService;
    public ItemsInOrderController(ItemInOrderService itemInOrderService) {
        this.itemInOrderService = itemInOrderService;
    }
    @GetMapping
    public List<ItemInOrder> getItemsInOrder() {
        return this.itemInOrderService.getItemInOrder();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemInOrder> getItemsInOrder(@PathVariable Long id) {
        ItemInOrder item = this.itemInOrderService.findItemInOrder(id);
        if (item != null) {
            return ResponseEntity.ok(item);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/add")
    public ResponseEntity<ItemInOrder> addItemsInOrder(@RequestBody ItemInOrderDto itemInOrderDto) {
        ItemInOrder item = this.itemInOrderService.createItemInOrder(itemInOrderDto);
        if (item != null) {
            return ResponseEntity.ok(item);
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<ItemInOrder> editItemsInOrder(@PathVariable Long id, @RequestBody ItemInOrderDto itemInOrderDto) {
        ItemInOrder item = this.itemInOrderService.updateItemInOrder(id, itemInOrderDto);
        if (item != null) {
            return ResponseEntity.ok(item);
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ItemInOrder> deleteItemsInOrder(@PathVariable Long id) {
        try {
            itemInOrderService.deleteItemInOrder(id);
            return ResponseEntity.ok().build();
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/order/{id}")
    public List<ItemInOrder> getByOrderId(@PathVariable Long id) {
        return this.itemInOrderService.findAllItemByOrderId(id);
    }

}
