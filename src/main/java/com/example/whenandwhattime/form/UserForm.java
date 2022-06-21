package com.example.whenandwhattime.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.example.whenandwhattime.validation.constraints.PasswordEquals;

import lombok.Data;

@Data
@PasswordEquals
public class UserForm {

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Size(max = 20)
    private String password;

    @NotEmpty
    @Size(max = 20)
    private String passwordConfirmation;

}
