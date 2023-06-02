package com.example.whenandwhattime.form;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.whenandwhattime.validation.constraints.ImageByte;
import com.example.whenandwhattime.validation.constraints.ImageNotEmpty;

import lombok.Data;

@Data
public class LiverForm {

    private Long id;
    
    
    @ImageNotEmpty
    @ImageByte(max = 2000000)
    private MultipartFile image;

    private String imageData;

    private String path;
    
    private String name;
    
    private String twitter_URL;
    
    private String youtube_URL;
    
    private String language;    
    
    private List<FavoriteForm> favorites;
    
    private FavoriteForm favorite;



}