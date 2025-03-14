package com.madhav.blog.app.repository;

import com.madhav.blog.app.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment,Integer> {

}
