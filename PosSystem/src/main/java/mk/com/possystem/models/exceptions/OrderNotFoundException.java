package mk.com.possystem.models.exceptions;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Long orderId) {
        super(String.format("Order with id %d not found", orderId));
    }
}
