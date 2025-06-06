package in.ethanmclaughlin.billingsoftware.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import in.ethanmclaughlin.billingsoftware.io.ItemRequest;
import in.ethanmclaughlin.billingsoftware.io.ItemResponse;
import in.ethanmclaughlin.billingsoftware.service.ItemService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor

public class ItemController {

    private final ItemService itemservice;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/admin/items")
    public ItemResponse addItem(@RequestPart("item") String itemString,
                                 @RequestPart("image")MultipartFile file) {
       ObjectMapper objectMapper = new ObjectMapper();
       ItemRequest itemRequest = null;
    try {
        itemRequest =  objectMapper.readValue(itemString, ItemRequest.class);
        //Adding the Item and file to the database
        return itemservice.add(itemRequest, file);
    } catch(JsonProcessingException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Error occured while processing the JSON");
    }
} 
    //We are getting the items from the database by retreiving them
    @GetMapping("/items")
    public List<ItemResponse> readItems() {
        return itemservice.fetchItem();
    }
    //This will delete an item by passing in the item id from the URL we can delete the item successfully and return a no content if successful
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/admin/items/{itemId}")
    public void removeItem(@PathVariable String itemId) {
        try {
           //This will delete the item from the database if successful
            itemservice.deleteItem(itemId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item Not Found");
        }
    }

}


