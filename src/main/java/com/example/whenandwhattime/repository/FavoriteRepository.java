package com.example.whenandwhattime.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.whenandwhattime.entity.Favorites;


public interface FavoriteRepository extends JpaRepository<Favorites, Long>{

	public Optional<Favorites> findById(String id);

	public List<Favorites> findByUserId(Long userId);
	
	public List<Favorites> findByUserIdAndLiversId(Long userId,Long liversId);

    public void deleteByUserIdAndLiversId(long userId,Long liversId);

}
