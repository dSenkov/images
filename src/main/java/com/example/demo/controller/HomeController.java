package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class HomeController {

    private static final String UPLOAD_FOLDER = "D://images//";
    private static final String IMG_NAME = "imageName";

    @GetMapping("/")
    public String homePage()
    {
        return "mainPage";
    }

    @RequestMapping(value = "/uploadedImage/{name:.+}")
    @ResponseBody
    public byte[] img(@PathVariable("name") String imageName) throws IOException
    {
        Path path = Paths.get(UPLOAD_FOLDER + imageName);
        return Files.readAllBytes(path);
    }

    @PostMapping("/upload")
    public String showImage(@RequestParam("file")MultipartFile file,
                            Model model)
    {
        if (file.isEmpty()){
            model.addAttribute("message", "Please select a file to upload.");
            return "uploadStatus";
        }
        String imageName = file.getOriginalFilename();
        try{
            File convertedFile = new File(UPLOAD_FOLDER + imageName);
            file.transferTo(convertedFile);
        } catch (IOException ex){
            ex.printStackTrace();
        }
        model.addAttribute(IMG_NAME, imageName);
        return "retrieve";
    }




}
