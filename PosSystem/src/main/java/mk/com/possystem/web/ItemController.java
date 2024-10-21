package mk.com.possystem.web;


import mk.com.possystem.models.Dto.ItemDto;
import mk.com.possystem.models.Item;
import mk.com.possystem.models.enumerations.ItemType;
import mk.com.possystem.models.enumerations.TypeSex;
import mk.com.possystem.service.ItemService;
import org.springframework.beans.factory.annotation.Value;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/items")
@CrossOrigin(origins = "http://localhost:3000")
public class ItemController {

    private final ItemService itemService;

    @Value("${file.upload-dir}")
    private String uploadDir;


    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/filterByTypes")
    public List<Item> filterByTypes(@RequestParam(required = false) ItemType itemType,
                                    @RequestParam(required = false) TypeSex typeSex) {

        List<Item> items = this.itemService.filterByType(itemType,typeSex);
        return items;
    }


    @GetMapping("/itemType")
    public List<ItemType> getItemTypes() {
        return List.of(ItemType.values());
    }
    @GetMapping("/typeSex")
    public List<TypeSex> getTypeSexs() {
        return List.of(TypeSex.values());
    }


    @PostMapping("/add")
    public ResponseEntity<String> handleFileUpload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("quantityInStock") int quantityInStock,
            @RequestParam("price") Long price,
            @RequestParam("itemType") ItemType itemType,
            @RequestParam("typeSex") TypeSex typeSex) {

        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdirs();
        }

        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        String filePath = uploadDir + File.separator + fileName;

        try {
            file.transferTo(new File(filePath));
            this.itemService.createItem(name, description, quantityInStock, price, itemType, typeSex, fileName);
            return ResponseEntity.ok("File uploaded successfully: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to upload file: " + e.getMessage());
        }
    }




    @GetMapping
    public List<Item> getFormItems() {
        List<Item> items = this.itemService.getAllItems();

        items.removeIf(item -> item.getQuantityInStock() <= 0);

        return items;
    }

    @GetMapping("/allItems")
    public List<Item> getAllItems () {
        return this.itemService.getAllItems();
    }



    @PutMapping("/edit/{id}")
    public ResponseEntity<Item> edit(@PathVariable Long id,@RequestBody ItemDto itemDto) {
        Item item = this.itemService.updateItem(id,itemDto);
        if (item != null) {
            return ResponseEntity.ok().body(item);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Item> delete(@PathVariable Long id) {
        try {
            this.itemService.deleteItem(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItem(@PathVariable Long id) {
        Item item = this.itemService.getItem(id);
        if (item != null) {
            return ResponseEntity.ok().body(item);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


    @PostMapping("/filter")
    public List<Item> getItemsByFilter(@RequestParam (required = false) ItemType itemType,
                                       @RequestParam (required = false) TypeSex typeSex,
                                       @RequestParam (required = false) String description,
                                       @RequestParam (required = false) String name) {
        return this.itemService.filter(itemType,name,description,typeSex);
    }

    @GetMapping("/filterByName")
    public List<Item> filterByName(@RequestParam String name) {
        return this.itemService.getItemsByName(name);
    }


}
