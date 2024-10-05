package com.example.blogback.controller;

import com.example.blogback.common.EditorR;
import com.example.blogback.common.R;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@ResponseBody
@RestController
@RequestMapping("/api/upload")
public class UploadController {

    @PostMapping
    public R upload( @RequestPart MultipartFile file){

        String fileName = file.getOriginalFilename();
        String suffixName = null;
        if (fileName != null) {
            suffixName = fileName.substring(fileName.lastIndexOf("."));
        }
        fileName = UUID.randomUUID() + suffixName;
        String filePath = "F:\\elmImg\\";
        try {
            file.transferTo(new File(filePath + fileName));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        System.out.println("http://localhost:8080/img/"+fileName);
        return R.success("http://localhost:8080/img/"+fileName);
    }

    @PostMapping("/editor")
    public EditorR editorUpload(@RequestPart MultipartFile file){
        String fileName = file.getOriginalFilename();
        String suffixName = null;
        if (fileName != null) {
            suffixName = fileName.substring(fileName.lastIndexOf("."));
        }
        fileName = UUID.randomUUID() + suffixName;
        String filePath = "F:\\elmImg\\";
        try {
            file.transferTo(new File(filePath + fileName));
        } catch (IOException e) {
            e.printStackTrace();
            return EditorR.fail("上传失败");
        }

        System.out.println("http://localhost:8080/img/"+fileName);
        return EditorR.success("http://localhost:8080/img/"+fileName);
    }
}
