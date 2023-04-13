package com.example.whenandwhattime.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import com.google.api.services.youtube.model.Video;

import com.example.whenandwhattime.youtube.Search;
import com.example.whenandwhattime.entity.Livers;
import com.example.whenandwhattime.entity.Youtube;
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
    public static Iterator<Video> schedule;

    
    @Autowired
    private YoutubeRepository yourepository;
    
    @Scheduled(cron = "0 0 * * * *", zone = "Asia/Tokyo")
    public void main() throws IOException {
    	Iterable<Livers> livers = liversrepository.findAll();
    	/*youtubeURLの取得リスト*/
    	for(Livers entity : livers) {
    		Rss.parseXML(Rss.path+entity.getYoutube_url());
    			/*ビデオIDを入れてテーブルを更新*/
		    	for(String id : Rss.video_id) {
		    		Youtube list=new Youtube();
		    		list.setVideoid(id);
		        	list.setLivers_id(entity.getId());
		            	if(!yourepository.existsByVideoid(id)) {
				            List<Youtube> post = new ArrayList<>();
				            post.clear();
				            list.setSchedule(null);
		            		Search.setvideoid(id);
		            		Search.Searching(null);
		            		list.setSchedule(Search.liveschedule);
				    		post.add(list);
				    	   	yourepository.saveAllAndFlush(post);
				    		
		            		
			    	   	}
		    		}   
	   	}
    }
    
    
    
}
