package com.madhav.blog.app.service.impl;

import com.madhav.blog.app.exceptions.ResourceNotFoundException;
import com.madhav.blog.app.model.Category;
import com.madhav.blog.app.model.Post;
import com.madhav.blog.app.model.User;
import com.madhav.blog.app.payloads.PostDto;
import com.madhav.blog.app.payloads.PostResponse;
import com.madhav.blog.app.repository.CategoryRepo;
import com.madhav.blog.app.repository.PostRepo;
import com.madhav.blog.app.repository.UserRepo;
import com.madhav.blog.app.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CategoryRepo categoryRepo;


    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
        User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","user Id",userId));
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","category Id",categoryId));
        Post post= this.modelMapper.map(postDto,Post.class);
        post.setAddDate(new Date());
        post.setImageName("default.png");
        post.setUser(user);
        post.setCategory(category);
        Post createdPost =this.postRepo.save(post);

        return this.modelMapper.map(createdPost,PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post ","Post Id", postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        Post updatedPost = this.postRepo.save(post);
        return this.modelMapper.map(updatedPost,PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post ","Post Id", postId));
        this.postRepo.deleteById(postId);

    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort=(sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
//        if(sortDir.equalsIgnoreCase("asc")){
//            sort=Sort.by(sortBy).ascending();
//        }else{
//            sort=Sort.by(sortBy).descending();
//        }
        Pageable p= PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> pagePost=this.postRepo.findAll(p);
        List<Post> posts=pagePost.getContent();
        List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        PostResponse postResponse=new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());
        return postResponse;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post ","Post Id", postId));
        return this.modelMapper.map(post,PostDto.class);
    }

    @Override
    public PostResponse getPostsByCategory(Integer categoryId, Integer pageNumber, Integer pageSize) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", " Category Id", categoryId));
        Pageable p= PageRequest.of(pageNumber, pageSize);
        Page<Post> pagePost = this.postRepo.findByCategory(category, p);
        List<PostDto> postDtos = pagePost.stream().map((post)-> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        PostResponse postResponse=new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());
        return postResponse;

    }

    @Override
    public PostResponse getPostsByUser(Integer userId, Integer pageNumber, Integer pageSize) {
        // Fetch user or throw exception if not found
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));

        // Create pageable request
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        // Fetch paginated posts by user
        Page<Post> posts = this.postRepo.findByUser(user, pageable);

        // Convert entity list to DTO list
        List<PostDto> postDtos = posts.stream()
                .map(post -> this.modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());

        // Prepare response object
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLastPage(posts.isLast());

        return postResponse;
    }


    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> posts=this.postRepo.findByTitleContaining(keyword);
        List<PostDto> postDtoList = posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        return postDtoList;
    }
}
