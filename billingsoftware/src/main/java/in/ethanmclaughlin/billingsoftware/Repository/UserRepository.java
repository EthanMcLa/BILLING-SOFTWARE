package in.ethanmclaughlin.billingsoftware.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ethanmclaughlin.billingsoftware.Entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByUserId(String userid);

}   
