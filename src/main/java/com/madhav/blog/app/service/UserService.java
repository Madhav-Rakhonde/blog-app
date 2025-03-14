package com.madhav.blog.app.service;

import com.madhav.blog.app.payloads.UserDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    UserDto registerNewUser(UserDto user);
    UserDto createUser(UserDto user);
    UserDto updateUser(UserDto user , Integer userid);
    UserDto getUserById(Integer userId);
    List<UserDto> getAllUsers();
    ResponseEntity<?> deleteUser(Integer userid);

}
