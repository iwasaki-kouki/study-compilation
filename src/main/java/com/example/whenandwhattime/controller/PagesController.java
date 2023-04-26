package com.example.whenandwhattime.controller;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.whenandwhattime.entity.Youtube;
import com.example.whenandwhattime.form.SearchForm;
import com.example.whenandwhattime.repository.YoutubeRepository;

@Controller
public class PagesController {
    @Autowired
    private ModelMapper modelMapper;
    
	@Autowired
    private YoutubeRepository repository;
	
	/*ホームに行くとhtmlに今日のスケジュールを送る*/
    @GetMapping("/")
    public String index(Model model) throws IOException {
    	model.addAttribute("list", list());
    	model.addAttribute("img", img());
    	return "pages/home";
    }
    


	/*今日のスケジュールをHTMLに送るためにまとめる*/
    private List<SearchForm> list() throws IOException{
    	Iterable<Youtube> schedule = repository.findAll();
    	List<SearchForm> list = new ArrayList<>();
    	ZonedDateTime nowday = ZonedDateTime.now();
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
		return list;
    }

    
	private SearchForm getYoutube(Youtube entity) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        SearchForm form = modelMapper.map(entity, SearchForm.class);
        return form;
	}

	   private List<String> img() throws IOException{
	    	Iterable<Youtube> schedule = repository.findAll();
	    	List<String>img=new ArrayList<>();
	    	ZonedDateTime nowday = ZonedDateTime.now();
	    	for (Youtube entity : schedule) {

	    		if(entity.getSchedule()!=null) {
	        	String substr = entity.getSchedule().substring(0, 10);
	    			if(nowday.toLocalDate().toString().equals(substr)) {
	    				img.add("https://img.youtube.com/vi/"+entity.getVideoid()+"/default.jpg");
	    			}
	            }
	        }
			return img;
	    }

}