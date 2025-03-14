package com.madhav.blog.app.service;

import com.madhav.blog.app.payloads.CommentDto;

public interface CommentService {
    CommentDto create(CommentDto commentDto , Integer postId);
    void delete(Integer commentId);
}
