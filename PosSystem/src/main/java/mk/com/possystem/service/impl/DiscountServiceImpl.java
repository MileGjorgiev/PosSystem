package mk.com.possystem.service.impl;

import mk.com.possystem.models.Discount;
import mk.com.possystem.models.Dto.DiscountDto;
import mk.com.possystem.models.Item;
import mk.com.possystem.models.exceptions.DiscountNotFoundException;
import mk.com.possystem.models.exceptions.ItemNotFoundException;
import mk.com.possystem.repository.DiscountRepository;
import mk.com.possystem.repository.ItemRepository;
import mk.com.possystem.service.DiscountService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DiscountServiceImpl implements DiscountService {

    public final DiscountRepository discountRepository;
    public final ItemRepository itemRepository;

    public DiscountServiceImpl(DiscountRepository discountRepository, ItemRepository itemRepository) {
        this.discountRepository = discountRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public void addDiscountToItem(Long id, Long itemId) {
        Discount discount = this.discountRepository.findById(id).orElseThrow(() -> new DiscountNotFoundException(id));
        Item item = this.itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException(itemId));
        discount.getItems().add(item);
        double discAmount = discount.getDiscountAmount() / 100.0;
        double finalAmount = item.getPrice() * discAmount;
        item.setPrice((long) (item.getPrice() - finalAmount));
        this.itemRepository.save(item);
        this.discountRepository.save(discount);
    }

    @Override
    public void removeDiscountFromItem(Long id, Long itemId) {
        Discount discount = this.discountRepository.findById(id).orElseThrow(() -> new DiscountNotFoundException(id));
        Item item = this.itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException(itemId));
        discount.getItems().remove(item);
        double discAmount = discount.getDiscountAmount()/100.0;
        double finalAmount = item.getPrice()/(1-discAmount);
        item.setPrice((long)finalAmount);
        this.discountRepository.save(discount);
        this.itemRepository.save(item);
    }

    @Override
    public List<Discount> findExpiredDiscounts(LocalDateTime currentTime) {
        return discountRepository.findByValidUntilBefore(currentTime);
    }

    @Override
    public Discount getDiscount(Long id) {
        Discount discount = this.discountRepository.findById(id).orElseThrow(() -> new DiscountNotFoundException(id));
        return discount;
    }

    @Override
    public List<Discount> getAllDiscounts() {
        return this.discountRepository.findAll();
    }

    @Override
    public Discount addDiscount(DiscountDto discountDto) {
        Discount discount = new Discount(discountDto.getValidUntil(),discountDto.getDiscountAmount());
        this.discountRepository.save(discount);
        return discount;
    }

    @Override
    public Discount updateDiscount(Long id,DiscountDto discountDto) {
        Discount discount = this.discountRepository.findById(id).orElseThrow(() -> new DiscountNotFoundException(id));
        discount.setDiscountAmount(discountDto.getDiscountAmount());
        discount.setValidUntil(discountDto.getValidUntil());
        this.discountRepository.save(discount);
        return discount;
    }

    @Override
    public void deleteDiscount(Long id) {
        Discount discount = this.discountRepository.findById(id).orElseThrow(() -> new DiscountNotFoundException(id));
        this.discountRepository.delete(discount);
    }



    public void removeAllDiscounts(Discount discount, List<Item> items) {
        for (Item item : items) {
            double discAmount = discount.getDiscountAmount()/100.0;
            double finalAmount = item.getPrice()/(1-discAmount);
            item.setPrice((long)finalAmount);
            this.itemRepository.save(item);
        }
    }
}
