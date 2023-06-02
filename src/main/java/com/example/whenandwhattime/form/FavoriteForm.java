package com.example.whenandwhattime.form;

import lombok.Data;

@Data
public class FavoriteForm {
    private Long userId;

    private Long liversId;
    
    private LiverForm liver;

}
