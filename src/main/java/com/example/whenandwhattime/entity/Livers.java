package com.example.whenandwhattime.entity;

import java.io.Serializable;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "livers")
@Data
public class Livers implements Serializable{
	private static final long serialVersionUID = 1L;

    
    @Id
    @SequenceGenerator(name = "Livers_id_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String twitter_url;
    
    @Column(nullable = false)
    private String youtube_url;
    
    @Column(nullable = false)
    private String thumbnail;

    @Column(nullable = false)
    private String language;


    
}