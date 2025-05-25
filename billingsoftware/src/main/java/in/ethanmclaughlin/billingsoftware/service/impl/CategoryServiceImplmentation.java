package in.ethanmclaughlin.billingsoftware.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import in.ethanmclaughlin.billingsoftware.Entity.CategoryEntity;
import in.ethanmclaughlin.billingsoftware.Repository.CategoryRepository;
import in.ethanmclaughlin.billingsoftware.io.CategoryRequest;
import in.ethanmclaughlin.billingsoftware.io.CategoryResponse;
import in.ethanmclaughlin.billingsoftware.service.CategoryService;
import in.ethanmclaughlin.billingsoftware.service.FileUploadService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImplmentation implements CategoryService {


    private final CategoryRepository CategoryRepository;
    private final FileUploadService fileuploadservice;

    @Override
    public CategoryResponse add(CategoryRequest request, MultipartFile file) {
       String imgUrl = fileuploadservice.uploadFile(file);
       CategoryEntity  newCategory = convertToEntity(request);
       newCategory.setImgUrl(imgUrl); 
        newCategory = CategoryRepository.save(newCategory);
        return convertToResponse(newCategory);
    }

        private CategoryEntity convertToEntity(CategoryRequest request) {
       
       return CategoryEntity.builder()
            .categoryId(UUID.randomUUID().toString())
            .name(request.getName())
            .description(request.getDescription())
            .bgColor(request.getBgColor())
            .build();
    }

    private CategoryResponse convertToResponse(CategoryEntity newCategory) {
      return  CategoryResponse.builder()
        .categoryId(newCategory.getCategoryId())
        .name(newCategory.getName())
        .description(newCategory.getDescription())
        .bgColor(newCategory.getBgColor())
        .imgUrl(newCategory.getImgUrl())
        .createdAt(newCategory.getCreatedAt())
        .updatedAt(newCategory.getUpdatedAt())
        .build();
    }

    @Override
    public List<CategoryResponse> read() {
       return CategoryRepository.findAll()
        .stream()
        .map(CategoryEntity->convertToResponse(CategoryEntity))
        .collect(Collectors.toList());
    }
    
    @Override
    public void delete(String categoryId) {
     CategoryEntity existingCategory =   CategoryRepository.findByCategoryId(categoryId)
        .orElseThrow(()-> new RuntimeException("Category not found: " + categoryId));
        fileuploadservice.deleteFile(existingCategory.getImgUrl());
    CategoryRepository.delete(existingCategory);
    
    }

}
