package in.ethanmclaughlin.billingsoftware.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import in.ethanmclaughlin.billingsoftware.io.ItemRequest;
import in.ethanmclaughlin.billingsoftware.io.ItemResponse;

public interface ItemService {

   ItemResponse add(ItemRequest request, MultipartFile file);

   List<ItemResponse> fetchItem();

   void deleteItem(String itemId);

}
