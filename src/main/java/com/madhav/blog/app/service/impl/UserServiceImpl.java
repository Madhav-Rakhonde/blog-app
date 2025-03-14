package com.madhav.blog.app.service.impl;

import com.madhav.blog.app.config.AppConstants;
import com.madhav.blog.app.exceptions.ResourceNotFoundException;
import com.madhav.blog.app.model.Role;
import com.madhav.blog.app.model.User;
import com.madhav.blog.app.payloads.UserDto;
import com.madhav.blog.app.repository.RoleRepo;
import com.madhav.blog.app.repository.UserRepo;
import com.madhav.blog.app.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepo roleRepo;

    @Override
    public UserDto registerNewUser(UserDto userDto) {

        User user = this.modelMapper.map(userDto, User.class);

        // encoded the password
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        // roles
        Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();

        user.getRoles().add(role);

        User newUser = this.userRepo.save(user);

        return this.modelMapper.map(newUser, UserDto.class);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user =this.dtoTOUser(userDto);
        User saveduser = this.userRepo.save(user);

        return this.userToDto(saveduser);
    }

    @Override
    public UserDto updateUser(UserDto user, Integer userid) {
        User user1 = this.userRepo.findById(userid).orElseThrow(()-> new ResourceNotFoundException("User" , "Id",userid));
        user1.setName(user.getName());
        user1.setEmail(user.getEmail());
        user1.setPassword(user.getPassword());
        user1.setAbout(user.getAbout());
        User updatedUser = this.userRepo.save(user1);
        UserDto userDto = this.userToDto(updatedUser);
        return userDto;
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User" , "Id",userId));
        return  this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = this.userRepo.findAll();
        List<UserDto> userDtos =  users.stream().map(user-> this.userToDto(user)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public ResponseEntity<?> deleteUser(Integer userid) {
        User user = this.userRepo.findById(userid).orElseThrow(()-> new ResourceNotFoundException("User","ID",userid));
        this.userRepo.deleteById(userid);

        return null;
    }

    private User dtoTOUser(UserDto userDto){
        User user = this.modelMapper.map(userDto , User.class);
//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setPassword(userDto.getPassword());
//        user.setAbout(userDto.getAbout());
//        user.setEmail(userDto.getEmail());
        return user;
    }
    public UserDto userToDto(User user){
        UserDto userDto=this.modelMapper.map(user ,UserDto.class );

        return userDto;
    }
}
