package com.madhav.blog.app.controller;

import com.madhav.blog.app.payloads.ApiResponse;
import com.madhav.blog.app.payloads.CommentDto;
import com.madhav.blog.app.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;


    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto , @PathVariable Integer postId){
        CommentDto createComment=  this.commentService.create(commentDto,postId);
        return  new ResponseEntity<CommentDto>(createComment , HttpStatus.CREATED);

    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){
        this.commentService.delete(commentId);
        return  new ResponseEntity<ApiResponse>(new ApiResponse("Comment deleted successfully", true), HttpStatus.OK);

    }

}
