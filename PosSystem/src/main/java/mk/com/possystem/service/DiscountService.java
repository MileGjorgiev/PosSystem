package mk.com.possystem.service;

import mk.com.possystem.models.Discount;
import mk.com.possystem.models.Dto.DiscountDto;
import mk.com.possystem.models.Item;

import java.time.LocalDateTime;
import java.util.List;

public interface DiscountService {
    Discount getDiscount(Long id);
    List<Discount> getAllDiscounts();
    Discount addDiscount(DiscountDto discountDto);
    Discount updateDiscount(Long id,DiscountDto discountDto);
    void deleteDiscount(Long id);
    void addDiscountToItem(Long id, Long itemId);
    void removeDiscountFromItem(Long id, Long itemId);
    List<Discount> findExpiredDiscounts(LocalDateTime currentTime);
    void removeAllDiscounts(Discount discount, List<Item> items);
}
