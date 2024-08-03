package mk.com.possystem.service.impl;

import mk.com.possystem.models.Dto.ItemDto;
import mk.com.possystem.models.Item;
import mk.com.possystem.models.enumerations.ItemType;
import mk.com.possystem.models.enumerations.TypeSex;
import mk.com.possystem.models.exceptions.ItemNotFoundException;
import mk.com.possystem.repository.ItemRepository;
import mk.com.possystem.service.ItemService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Item getItem(Long id) {
        Item item = this.itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(id));
        return item;
    }

    @Override
    public List<Item> getAllItems() {
        return this.itemRepository.findAll();
    }

    @Override
    public List<Item> getItemsByCategory(ItemType itemType) {
        return this.itemRepository.findAllByItemType(itemType);
    }

    @Override
    public List<Item> getItemsByTypeSex(TypeSex typeSex) {
        return this.itemRepository.findAllByTypeSex(typeSex);
    }

    @Override
    public List<Item> getItemsByName(String name) {
        return this.itemRepository.findByNameContaining(name);
    }

    @Override
    public List<Item> getItemsByDescription(String description) {
        return this.itemRepository.findAllByDescriptionContaining(description);
    }

    @Override
    public Item createItem(ItemDto itemDto, String image) {
        Item item = new Item(itemDto.getName(), itemDto.getDescription(), itemDto.getQuantityInStock(),
                itemDto.getPrice(), itemDto.getItemType(),itemDto.getTypeSex(),image);
        this.itemRepository.save(item);
        return item;
    }

    @Override
    public Item updateItem(Long id, ItemDto itemDto) {
        Item item = this.itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(id));
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setQuantityInStock(itemDto.getQuantityInStock());
        item.setPrice(itemDto.getPrice());
        item.setItemType(itemDto.getItemType());
        item.setItemImage(itemDto.getItemImage());
        item.setTypeSex(itemDto.getTypeSex());
        this.itemRepository.save(item);
        return item;
    }

    @Override
    public void deleteItem(Long id) {
        Item item = this.itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(id));
        this.itemRepository.delete(item);
    }

    @Override
    public List<Item> filter(ItemType itemType, String name, String description, TypeSex typeSex) {

        if (itemType == null && (name == null || name.isEmpty()) &&
                (description == null || description.isEmpty()) && typeSex == null) {
            return this.itemRepository.findAll();
        }

        Set<Item> items = new HashSet<>();

        if (itemType != null) {
            items.addAll(this.itemRepository.findAllByItemType(itemType));
        }
        if (name != null && !name.isEmpty()) {
            items.addAll(this.itemRepository.findByNameContaining(name));
        }
        if (description != null && !description.isEmpty()) {
            items.addAll(this.itemRepository.findAllByDescriptionContaining(description));
        }
        if (typeSex != null) {
            items.addAll(this.itemRepository.findAllByTypeSex(typeSex));
        }


        return new ArrayList<>(items);
    }

}
