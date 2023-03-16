package com.example.whenandwhattime.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.whenandwhattime.entity.Youtube;
import com.example.whenandwhattime.form.SearchForm;
import com.example.whenandwhattime.repository.YoutubeRepository;

@Controller
public class PagesController {
    @Autowired
    private ModelMapper modelMapper;
    
	@Autowired
    private YoutubeRepository repository;

    @RequestMapping("/")
    public String index(Model model) throws IOException {
    	model.addAttribute("list", list());
        return "pages/home";
    }
    
    private List<SearchForm> list() throws IOException{
    	Iterable<Youtube> schedule = repository.findAll();
    	List<SearchForm> list = new ArrayList<>();
    	for (Youtube entity : schedule) {
    		if(entity.getSchedule()!=null) {
    		SearchForm form = getYoutube(entity);
            list.add(form);
            }
        }
		return list;
    }

	private SearchForm getYoutube(Youtube entity) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        SearchForm form = modelMapper.map(entity, SearchForm.class);
        return form;
	}




}