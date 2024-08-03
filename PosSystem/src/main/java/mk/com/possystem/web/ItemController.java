package mk.com.possystem.web;

import mk.com.possystem.models.Dto.ItemDto;
import mk.com.possystem.models.Item;
import mk.com.possystem.models.enumerations.ItemType;
import mk.com.possystem.models.enumerations.TypeSex;
import mk.com.possystem.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    private final String uploadDir = "src/main/resources/uploads/";

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }


    @PostMapping("/add")
    public ResponseEntity<String> handleFileUpload(@RequestBody ItemDto itemDto,@RequestParam("file") MultipartFile file) {
        File uploadDirFile = new File(uploadDir);

        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdirs();
        }

        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        String filePath = uploadDir + File.separator + fileName;


        try {
            file.transferTo(new File(filePath));
            this.itemService.createItem(itemDto,filePath);
            return ResponseEntity.ok("File uploaded successfully: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to upload file");
        }
    }





    @GetMapping
    public List<Item> getItems() {
        return this.itemService.getAllItems();
    }



    @PutMapping("/edit/{id}")
    public ResponseEntity<Item> save(@PathVariable Long id,@RequestBody ItemDto itemDto) {
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

}
