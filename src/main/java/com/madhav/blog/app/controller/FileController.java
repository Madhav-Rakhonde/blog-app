package com.madhav.blog.app.controller;

import com.madhav.blog.app.payloads.FileResponse;
import com.madhav.blog.app.service.FileService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStream;
//
//@RestController
//@RequestMapping("/file")
//public class FileController {
//
//    @Autowired
//    private FileService fileService;
//
//    @Value("${project.image}")
//    private String path;
//    @PostMapping("/image/upload/{postId]")
//    public ResponseEntity<FileResponse> fileUpload(@RequestParam("image")MultipartFile image){
//        String fileName = null;
//        try{
//            fileName = this.fileService.uploadImage(path,image);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(new FileResponse(null ,"Image is not uploaded due to server error !!"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//        return new ResponseEntity<>(new FileResponse(fileName ,"Image is successfully Success uploaded !!"), HttpStatus.OK);
//    }
//    //method to serve file
//    @GetMapping("/images/{imageName}")
//    public void downloadImage(@PathVariable("imageName") String imageName , HttpServletResponse response) throws IOException {
//        InputStream resource = this.fileService.getResource(path ,imageName);
//        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
//        StreamUtils.copy(resource,response.getOutputStream());
//
//    }
//
//}
