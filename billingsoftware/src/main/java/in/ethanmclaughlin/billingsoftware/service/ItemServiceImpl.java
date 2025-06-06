package in.ethanmclaughlin.billingsoftware.service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import in.ethanmclaughlin.billingsoftware.Entity.CategoryEntity;
import in.ethanmclaughlin.billingsoftware.Entity.ItemEntity;
import in.ethanmclaughlin.billingsoftware.Repository.CategoryRepository;
import in.ethanmclaughlin.billingsoftware.Repository.ItemRepository;
import in.ethanmclaughlin.billingsoftware.io.ItemRequest;
import in.ethanmclaughlin.billingsoftware.io.ItemResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    
    private final FileUploadService fileUploadService;
    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;

    @Override
    public ItemResponse add(ItemRequest request, MultipartFile file) {
        String imgUrl = fileUploadService.uploadFile(file);
        ItemEntity newItem = convertToEntity(request);
        CategoryEntity existingCategory = categoryRepository.findByCategoryId(request.getCategoryId())
            .orElseThrow(() -> new RuntimeException("Category not found: " + request.getCategoryId()));
            newItem.setCategory(existingCategory);
            newItem.setImgUrl(imgUrl);
            newItem = itemRepository.save(newItem);
            return convertToResponse(newItem);
        }

    private ItemEntity convertToEntity(ItemRequest request) {
        return ItemEntity.builder()
            .itemId(UUID.randomUUID().toString())
            .name(request.getName())
            .description(request.getDescription())
            .price(request.getPrice())
            .build();
    }

    private ItemResponse convertToResponse(ItemEntity newItem) {
           return  ItemResponse.builder()
            .itemId(newItem.getItemId())
            .name(newItem.getName())
            .description(newItem.getDescription())
            .price(newItem.getPrice())
            .categoryName(newItem.getCategory().getName())
            .categoryId(newItem.getCategory().getCategoryId())
            .imgUrl(newItem.getImgUrl())
            .createdAt(newItem.getCreatedAt())
            .updatedAt(newItem.getUpdatedAt())
            .build();    

    }

    @Override
    public List<ItemResponse> fetchItem() {
        return itemRepository.findAll()
            .stream()
            .map(ItemEntity-> convertToResponse(ItemEntity))
            .collect(Collectors.toList());
    }

    @Override
    public void deleteItem(String itemId) {
        ItemEntity existingItem = itemRepository.findByItemId(itemId)
            .orElseThrow(() -> new RuntimeException("Item not found: " + itemId));
        Boolean isFileDeleted = fileUploadService.deleteFile(existingItem.getImgUrl());

            if(isFileDeleted) {
                itemRepository.delete(existingItem);
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable To delete the Image");
            }
        }   

}
