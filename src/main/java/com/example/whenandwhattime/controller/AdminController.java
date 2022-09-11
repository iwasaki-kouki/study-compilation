package com.example.whenandwhattime.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.whenandwhattime.entity.Admin;
import com.example.whenandwhattime.entity.Admin.Authority;
import com.example.whenandwhattime.form.UserForm;
import com.example.whenandwhattime.repository.AdminRepository;

@Controller
public class AdminController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AdminRepository repository;

    @GetMapping(path = "/admin/new")
    public String newUser(Model model) {
        model.addAttribute("form", new UserForm());
        return "admin/new";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.POST)
    public String create(@Validated @ModelAttribute("form") UserForm form, BindingResult result, Model model) {
        String email = form.getEmail();
        String password = form.getPassword();
        String passwordConfirmation = form.getPasswordConfirmation();

        if (repository.findByUsername(email) != null) {
            FieldError fieldError = new FieldError(result.getObjectName(), "email", "その E メールはすでに使用されています。");
            result.addError(fieldError);
        }
        if (result.hasErrors()) {
            return "admin/new";
        }

        Admin entity = new Admin(email, passwordEncoder.encode(password), Authority.ROLE_ADMIN);
        repository.saveAndFlush(entity);

        return "layouts/complete";
    }
}

