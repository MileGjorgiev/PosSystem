package mk.com.possystem.models.exceptions;

public class DiscountNotFoundException extends RuntimeException{
    public DiscountNotFoundException(Long id) {
        super(String.format("Discount with id %d not found", id));
    }
}
