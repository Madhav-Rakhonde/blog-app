package com.madhav.blog.app.service.impl;

import com.madhav.blog.app.exceptions.ResourceNotFoundException;
import com.madhav.blog.app.model.Comment;
import com.madhav.blog.app.model.Post;
import com.madhav.blog.app.payloads.CommentDto;
import com.madhav.blog.app.repository.CommentRepo;
import com.madhav.blog.app.repository.PostRepo;
import com.madhav.blog.app.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostRepo postRepo;
    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto create(CommentDto commentDto, Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","post id",postId));
        Comment comment = this.modelMapper.map(commentDto,Comment.class);
        comment.setPost(post);
        Comment savedComment = this.commentRepo.save(comment);
        return this.modelMapper.map(savedComment,CommentDto.class);

    }

    @Override
    public void delete(Integer commentId) {
        Comment comment = this.commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("comment","comment id " , commentId));
        this.commentRepo.deleteById(commentId);
    }
}
