package com.example.whenandwhattime.form;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class SearchForm {

    @NotEmpty
    private String id;

    private String vedioid;
    
    private String schedule;
    
    private Long livers_id;

}
