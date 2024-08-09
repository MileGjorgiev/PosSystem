package mk.com.possystem.models.Dto;

import lombok.Data;

@Data
public class ApplyCustomerDiscount {
    private Long id;
    private Long customerId;

    public ApplyCustomerDiscount(Long id, Long customerId) {
        this.id = id;
        this.customerId = customerId;
    }
}
