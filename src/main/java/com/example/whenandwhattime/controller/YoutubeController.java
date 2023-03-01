package com.example.whenandwhattime.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.example.whenandwhattime.youtube.Search;
import com.example.whenandwhattime.entity.Livers;
import com.example.whenandwhattime.entity.Youtube;
import com.example.whenandwhattime.form.LiverForm;
import com.example.whenandwhattime.form.SearchForm;
import com.example.whenandwhattime.repository.LiversRepository;
import com.example.whenandwhattime.repository.YoutubeRepository;
import com.example.whenandwhattime.rss.Rss;

@Controller
public class YoutubeController {
	

	protected static Logger log = LoggerFactory.getLogger(LiversController.class);

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private  LiversRepository liversrepository;
    
    
    @Autowired
    private YoutubeRepository yourepository;
    
    @GetMapping(path="/test")
    public String main(Model model) throws IOException {
    	Iterable<Livers> livers = liversrepository.findAll();
    	/*youtubeURLの取得リスト*/
    	for(Livers entity : livers) {
    	   	Youtube list =new Youtube();
    		Rss.parseXML(Rss.path+entity.getYoutube_url());
    		list.setLivers_id(entity.getId());
    		list.setSchedule("a");
    		/*ビデオIDを入れてテーブルを更新*/
		    	for(String id : Rss.video_id) {
		    		list=new Youtube();
		    		list.setVideoid(id);
		        	list.setLivers_id(entity.getId());
		        	list.setSchedule("a");
		            List<Youtube> post = new ArrayList<>();
		            	if(!yourepository.existsByVideoid(id)) {
			    		post.add(list);
			    	   	yourepository.saveAllAndFlush(post);
			    	   	}	
		    		}
	   	}


    	return "liver/index";
    }
    
}
