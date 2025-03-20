package com.madhav.blog.app.payloads;

import com.madhav.blog.app.model.Category;
import com.madhav.blog.app.model.Comment;
import com.madhav.blog.app.model.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Integer postId;
    private String title;
    private String content;
    private String imageName;
    private Date addDate;

    private CategoryDto category;

    private UserDto user;
    private Set<Comment> comments=new HashSet<>();
}
