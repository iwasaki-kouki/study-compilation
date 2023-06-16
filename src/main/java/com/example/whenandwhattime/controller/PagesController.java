package com.example.whenandwhattime.controller;

import java.io.IOException;
import java.security.Principal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.whenandwhattime.entity.Favorites;
import com.example.whenandwhattime.entity.Livers;
import com.example.whenandwhattime.entity.UserInf;
import com.example.whenandwhattime.entity.Youtube;
import com.example.whenandwhattime.form.LiverForm;
import com.example.whenandwhattime.form.SearchForm;
import com.example.whenandwhattime.repository.FavoriteRepository;
import com.example.whenandwhattime.repository.LiversRepository;
import com.example.whenandwhattime.repository.YoutubeRepository;
import com.example.whenandwhattime.rss.Rss;
import com.example.whenandwhattime.youtube.Search;

@Controller
public class PagesController {
    @Autowired
    private ModelMapper modelMapper;
    
	@Autowired
    private YoutubeRepository repository;
	
    @Autowired
    private FavoriteRepository farepository;
	

	
	/*ホームに行くとhtmlに今日のスケジュールを送る*/
    @GetMapping("/")
    public String index(Principal principal,Model model) throws IOException {
    	model.addAttribute("list", list(principal));
    	model.addAttribute("img", img("img"));
    	model.addAttribute("hiimg", img("hiimg"));
    	return "pages/home";
    }
    


	private SearchForm getYoutube(Youtube entity) {
	    modelMapper.getConfiguration().setAmbiguityIgnored(true);
	    SearchForm form = modelMapper.map(entity, SearchForm.class);
	    return form;
	}
	
	


	/*今日のスケジュールをHTMLに送るためにまとめる*/
    private List<SearchForm> list(Principal principal) throws IOException{
    	Iterable<Youtube> schedule = repository.findAll();
    	List<SearchForm> list = new ArrayList<>();
    	ZonedDateTime nowday = ZonedDateTime.now();
    	if(principal==null) {
	    	for (Youtube entity : schedule) {
	    		if(entity.getSchedule()!=null) {
	        	String substr = entity.getSchedule().substring(0, 10);
	    			if(nowday.toLocalDate().toString().equals(substr)) {
	    				SearchForm form = getYoutube(entity);
	    	        	form.setSchedule(entity.getSchedule().substring(11, 16));
	    				list.add(form);
	    			}
	            }
	        }
    	}else {
    		Authentication authentication = (Authentication) principal;
    		UserInf user = (UserInf) authentication.getPrincipal();
    		Long userId = user.getUserId();
    		List<Favorites> results = farepository.findByUserId(userId);
    		
	    	for (Youtube entity : schedule) {
	    		for(Favorites Fav : results) {
	    			if((entity.getSchedule()!=null)&&(entity.getLivers_id()==Fav.getLiversId())) {
		        	String substr = entity.getSchedule().substring(0, 10);
		    			if(nowday.toLocalDate().toString().equals(substr)) {
		    				SearchForm form = getYoutube(entity);
		    	        	form.setSchedule(entity.getSchedule().substring(11, 16));
		    				list.add(form);
		    			}
		            }
	    		}
	        }    	
			
    	}

    	return list;
    }

    
	private List<String> img(String quality) throws IOException{
	    	Iterable<Youtube> schedule = repository.findAll();
	    	List<String>img=new ArrayList<>();
	    	ZonedDateTime nowday = ZonedDateTime.now();
	    	for (Youtube entity : schedule) {

	    		if(entity.getSchedule()!=null) {
	        	String substr = entity.getSchedule().substring(0, 10);
	    			if(nowday.toLocalDate().toString().equals(substr)) {
	    				if(quality=="img"){
	    				img.add("https://img.youtube.com/vi/"+entity.getVideoid()+"/default.jpg");
	    				}else {
	    				img.add("https://img.youtube.com/vi/"+entity.getVideoid()+"/hqdefault.jpg");
	    				}
	    				
	    				
	    			}
	            }
	        }
			return img;
	    }
	   

	   
}