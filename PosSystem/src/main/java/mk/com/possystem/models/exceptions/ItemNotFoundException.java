package mk.com.possystem.models.exceptions;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException(Long itemId) {
        super(String.format("Item not found with id %d", itemId));
    }
}
