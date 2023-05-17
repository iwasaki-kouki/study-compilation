package com.example.whenandwhattime.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "schedule")
@Data

public class Youtube implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
    @Id
    @SequenceGenerator(name = "Livers_id_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String videoid;
    
    @Column(nullable = false)
    private String schedule;
    
    @Column(nullable = false)
    private Long Livers_id;
    
    @Column(nullable = false)
    private String title;
    
    
    
    @ManyToOne
    @JoinColumn(name = "livers_id", insertable = false, updatable = false)
    private Livers livers;




    
    
    
}
