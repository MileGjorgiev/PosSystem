package mk.com.possystem.models.exceptions;

public class ItemInOrderNotFoundException extends RuntimeException {
    public ItemInOrderNotFoundException(Long id) {
        super(String.format("ItemInOrder with id %d not found", id));
    }
}
