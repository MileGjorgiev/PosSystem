package mk.com.possystem.models.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class DiscountDto {
    private LocalDateTime validUntil;
    private Long discountAmount;

    public DiscountDto(LocalDateTime validUntil, Long discountAmount) {
        this.validUntil = validUntil;
        this.discountAmount = discountAmount;
    }
}
