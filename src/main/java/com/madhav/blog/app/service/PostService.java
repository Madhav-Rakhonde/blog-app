package com.madhav.blog.app.service;

import com.madhav.blog.app.payloads.PostDto;
import com.madhav.blog.app.payloads.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto,Integer userId ,Integer categoryId);
    PostDto updatePost(PostDto postDto , Integer postId);
    void deletePost(Integer postId);
    PostResponse getAllPost(Integer pageNumber , Integer pageSize,String sortBy , String sortDir);
    PostDto getPostById(Integer postId);
    PostResponse getPostsByCategory( Integer categoryId,Integer pageNumber , Integer pageSize);
    PostResponse getPostsByUser( Integer userId,Integer pageNumber , Integer pageSize);
    List<PostDto> searchPosts(String keyword);









}
