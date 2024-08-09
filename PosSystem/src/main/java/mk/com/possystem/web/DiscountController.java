package mk.com.possystem.web;

import mk.com.possystem.models.Discount;
import mk.com.possystem.models.Dto.DiscountDto;
import mk.com.possystem.models.Dto.ItemDto;
import mk.com.possystem.service.DiscountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/discounts")
@CrossOrigin(origins = "http://localhost:3000")
public class DiscountController {

    public final DiscountService discountService;

    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @GetMapping
    public List<Discount> getAllDiscounts(){
        return this.discountService.getAllDiscounts();
    }

    @PostMapping("/add")
    public ResponseEntity<Discount> createDiscount(@RequestBody DiscountDto discountDto){
        Discount discount = this.discountService.addDiscount(discountDto);
        if (discount != null){
            return ResponseEntity.ok(discount);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Discount> editDiscount(@PathVariable Long id,@RequestBody DiscountDto discountDto){
        Discount discount = this.discountService.updateDiscount(id,discountDto);
        if (discount != null){
            return ResponseEntity.ok(discount);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Discount> deleteDiscount(@PathVariable Long id){
        try {
            this.discountService.deleteDiscount(id);
            return ResponseEntity.ok().build();
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Discount> getDiscountById(@PathVariable Long id){
        Discount discount = this.discountService.getDiscount(id);
        if (discount != null){
            return ResponseEntity.ok(discount);
        }
        return ResponseEntity.badRequest().build();
    }


    @PostMapping("/addDiscount/{id}")
    public ResponseEntity<Void> addDiscount(@PathVariable Long id, @RequestBody Map<String, Long> requestBody) {
        try {
            Long itemId = requestBody.get("itemId");
            this.discountService.addDiscountToItem(id, itemId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/removeDiscount/{id}")
    public ResponseEntity<Discount> remove(@PathVariable Long id, @RequestBody Map<String, Long> requestBody){
        try {
            Long itemId = requestBody.get("itemId");
            this.discountService.removeDiscountFromItem(id, itemId);
            return ResponseEntity.ok().build();
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }













}
