package com.madhav.blog.app.controller;

import com.madhav.blog.app.payloads.ApiResponse;
import com.madhav.blog.app.payloads.UserDto;
import com.madhav.blog.app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        UserDto createduser = this.userService.createUser(userDto);
        return new ResponseEntity<>(createduser, HttpStatus.CREATED);
    }
    @PutMapping("/{userid}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto ,@PathVariable Integer userid){
        UserDto updatedUser= this.userService.updateUser(userDto,userid);
        return ResponseEntity.ok(updatedUser);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userid}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer userid){
        ResponseEntity<?> deleteUser = this.userService.deleteUser(userid);
        return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Sucessfully",true),HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUser(){
        return ResponseEntity.ok(this.userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getAUser(@PathVariable Integer id){
        return ResponseEntity.ok(this.userService.getUserById(id));
    }







}
