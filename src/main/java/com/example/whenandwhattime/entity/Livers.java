package com.example.whenandwhattime.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "Livers")
@Data
public class Livers{
	
    public Livers(String name, String ch_name, String twitter_url,String youtube_url) {
	        this.liversname = name;
	        this.ch_name = ch_name;
	        this.twitter = twitter_url;
	        this.youtube = youtube_url;
    }
    
    @Id
    @SequenceGenerator(name = "Livers_id_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long liverId;
    
    @Column(nullable = false)
    private String liversname;
    
    @Column(nullable = false)
    private String ch_name;

    @Column(nullable = false)
    private String twitter;
    
    @Column(nullable = false)
    private String youtube;
}