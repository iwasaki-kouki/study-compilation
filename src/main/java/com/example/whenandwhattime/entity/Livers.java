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
@Table(name = "livers")
@Data
public class Livers implements Serializable{
	private static final long serialVersionUID = 1L;

    
    @Id
    @SequenceGenerator(name = "Livers_id_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false)
    private String twitter;
    
    @Column(nullable = false)
    private String youtube;
    
    @Column(nullable = false)
    private String thumbnail;

    @ManyToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private User user;
    
    
}