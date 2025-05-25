package in.ethanmclaughlin.billingsoftware.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ethanmclaughlin.billingsoftware.Entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {


    Optional<CategoryEntity> findByCategoryId(String categoryId);

}
