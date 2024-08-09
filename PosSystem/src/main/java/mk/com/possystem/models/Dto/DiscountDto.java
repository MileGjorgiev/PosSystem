package mk.com.possystem.models.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class DiscountDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime validUntil;
    private Long discountAmount;

    public DiscountDto(LocalDateTime validUntil, Long discountAmount) {
        this.validUntil = validUntil;
        this.discountAmount = discountAmount;
    }
}
