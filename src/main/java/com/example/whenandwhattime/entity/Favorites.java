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
@Table(name = "favorites")
@Data
public class Favorites implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "favorite_id_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long liversId;
    
    @ManyToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private User user;
    

    @ManyToOne
    @JoinColumn(name = "liversId",insertable = false, updatable = false)
    private Livers liver;
    


}
