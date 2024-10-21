package mk.com.possystem.jobs;

import mk.com.possystem.models.Discount;
import mk.com.possystem.service.DiscountService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DiscountScheduler {


    private final DiscountService discountService;

    public DiscountScheduler(DiscountService discountService) {
        this.discountService = discountService;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void checkForExpiredDiscounts() {
            List<Discount> expiredDiscounts = discountService.findExpiredDiscounts(LocalDateTime.now());
            for (Discount discount : expiredDiscounts) {
                discountService.removeAllDiscounts(discount,discount.getItems());
                discountService.deleteDiscount(discount.getId());
            }
        }
}