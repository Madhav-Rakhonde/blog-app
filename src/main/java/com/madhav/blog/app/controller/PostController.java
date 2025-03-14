package com.madhav.blog.app.controller;

import com.madhav.blog.app.config.AppConstants;
import com.madhav.blog.app.payloads.ApiResponse;
import com.madhav.blog.app.payloads.PostDto;
import com.madhav.blog.app.payloads.PostResponse;
import com.madhav.blog.app.service.FileService;
import com.madhav.blog.app.service.PostService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private FileService fileService;
    @Value("${project.image}")
    private String path;

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto , @PathVariable Integer userId, @PathVariable Integer categoryId){
        PostDto createdPost = this.postService.createPost(postDto,userId,categoryId);
        return new ResponseEntity<PostDto>(createdPost, HttpStatus.CREATED);

    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<PostResponse> getPostByUser(@PathVariable Integer userId,@RequestParam(value="pageNumber" , defaultValue=AppConstants.PAGE_NUMBER, required=false) Integer pageNumber, @RequestParam(value="pageSize" , defaultValue=AppConstants.PAGE_SIZE, required=false)Integer pageSize){
        PostResponse postResponse= this.postService.getPostsByUser(userId,pageNumber, pageSize);
        return new ResponseEntity<>(postResponse,HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<PostResponse> getPostByCategory(@PathVariable Integer categoryId,@RequestParam(value="pageNumber" , defaultValue=AppConstants.PAGE_NUMBER, required=false) Integer pageNumber, @RequestParam(value="pageSize" , defaultValue=AppConstants.PAGE_SIZE, required=false)Integer pageSize){
        PostResponse postResponse = this.postService.getPostsByCategory(categoryId,pageNumber, pageSize);
        return new ResponseEntity<>(postResponse,HttpStatus.OK);

    }
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPost(@RequestParam(value="pageNumber" , defaultValue= AppConstants.PAGE_NUMBER, required=false) Integer pageNumber, @RequestParam(value="pageSize" , defaultValue=AppConstants.PAGE_SIZE, required=false)Integer pageSize,
                                                   @RequestParam(value="sortBy",defaultValue = AppConstants.SORT_BY,required = false)String sortBy,
                                                   @RequestParam(value="sortDir",defaultValue =AppConstants.SORT_DIR,required = false)String sortDir ){
        PostResponse response=this.postService.getAllPost(pageNumber, pageSize,sortBy,sortDir );
        return new ResponseEntity<PostResponse>(response,HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
        PostDto post =this.postService.getPostById(postId);
        return new ResponseEntity<PostDto>(post,HttpStatus.OK);
    }
    @DeleteMapping("/posts/{postId}")
    public ApiResponse deletePost(@PathVariable Integer postId){
        this.postService.deletePost(postId);
        return  new ApiResponse("Post is successfully deleted ",true);
    }
    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto ,@PathVariable Integer postId){
        PostDto updatePost = this.postService.updatePost(postDto,postId);
        return  new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
    }
    @GetMapping("/posts/search/{keywords}")
    public ResponseEntity<List<PostDto>> searchPost(@PathVariable String keywords){
        List<PostDto> postDtoList = this.postService.searchPosts(keywords);
        return new ResponseEntity<List<PostDto>>(postDtoList , HttpStatus.OK);

    }


    @PostMapping("post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image, @PathVariable Integer postId ) throws IOException {
        PostDto postDto = this.postService.getPostById(postId);
        String fileName = this.fileService.uploadImage(path , image);
//        PostDto postDto = this.postService.getPostById(postId);
        postDto.setImageName(fileName);
        PostDto updatedPost = this.postService.updatePost(postDto,postId);
        return  new ResponseEntity<PostDto>(updatedPost,HttpStatus.OK);
    }
    @GetMapping(value="post/images/{imageName}", produces =MediaType.IMAGE_JPEG_VALUE)
   public void downloadImage(@PathVariable("imageName") String imageName , HttpServletResponse response) throws IOException {
        InputStream resource = this.fileService.getResource(path ,imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());

   }

}
