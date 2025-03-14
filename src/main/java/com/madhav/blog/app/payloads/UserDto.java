package com.madhav.blog.app.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private int id;
    @NotEmpty
    @Size(min=4 , message = "Username must be min of 4 characters")
    private String name;
    @NotEmpty
    @Size(min=3,message = "Password must be min of 3 chars and max of 10 chars")
    private String password;
    @Email(message = "Email address is not valid !!")
    private String email;
    @NotEmpty
    private String about;

}
