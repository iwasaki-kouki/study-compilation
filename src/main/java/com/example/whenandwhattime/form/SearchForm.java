package com.example.whenandwhattime.form;

import javax.validation.constraints.NotEmpty;

import com.example.whenandwhattime.validation.constraints.PasswordEquals;

import lombok.Data;

@Data
@PasswordEquals
public class SearchForm {

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    @NotEmpty
    private String passwordConfirmation;

}
