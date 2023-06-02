package com.example.whenandwhattime.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.whenandwhattime.entity.Favorites;
import com.example.whenandwhattime.entity.Livers;
import com.example.whenandwhattime.entity.UserInf;
import com.example.whenandwhattime.form.LiverForm;
import com.example.whenandwhattime.repository.FavoriteRepository;

@Controller
public class FavoritesController {


    @Autowired
    private MessageSource messageSource;

    @Autowired
    private FavoriteRepository repository;

    @Autowired
    private LiversController LiversController;
    
    @GetMapping(path = "/favorites")
    public String index(Principal principal, Model model) throws IOException {
        Authentication authentication = (Authentication) principal;
        UserInf user = (UserInf) authentication.getPrincipal();
        List<Favorites> topics = repository.findByUserId(user.getUserId());
        List<LiverForm> list = new ArrayList<>();
        for (Favorites entity : topics) {
            Livers liver = entity.getLiver();
            LiverForm form =LiversController.getLivers(user,liver);
            list.add(form);
        }
        model.addAttribute("list", list);

        return "liver/index";
    }
    
    @RequestMapping(value = "/favorite", method = RequestMethod.POST)
    public String create(Principal principal, @RequestParam("topic_id") long topicId, RedirectAttributes redirAttrs,
            Locale locale) {
        Authentication authentication = (Authentication) principal;
        UserInf user = (UserInf) authentication.getPrincipal();
        Long userId = user.getUserId();
        List<Favorites> results = repository.findByUserIdAndLiversId(userId, topicId);
        if (results.size() == 0) {
            Favorites entity = new Favorites();
            entity.setUserId(userId);
            entity.setLiversId(topicId);
            repository.saveAndFlush(entity);
        }

        return "redirect:/livers";
    }

    @RequestMapping(value = "/favorite", method = RequestMethod.DELETE)
    @Transactional
    public String destroy(Principal principal, @RequestParam("topic_id") long topicId, RedirectAttributes redirAttrs,
            Locale locale) {
        Authentication authentication = (Authentication) principal;
        UserInf user = (UserInf) authentication.getPrincipal();
        Long userId = user.getUserId();
        List<Favorites> results = repository.findByUserIdAndLiversId(userId, topicId);
        if (results.size() == 1) {
            repository.deleteByUserIdAndLiversId(user.getUserId(), topicId);
        }
        return "redirect:/livers";
    }


    

	
}
