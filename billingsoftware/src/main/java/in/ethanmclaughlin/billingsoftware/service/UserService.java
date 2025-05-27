package in.ethanmclaughlin.billingsoftware.service;

import java.util.List;
import java.util.Optional;


import in.ethanmclaughlin.billingsoftware.io.UserRequest;
import in.ethanmclaughlin.billingsoftware.io.UserResponse;



public interface UserService {

    UserResponse createUser(UserRequest userRequest);

    String getUserRole(String email);

    List<UserResponse> readUsers();

    void deleteUser(String id);
    
   

}
