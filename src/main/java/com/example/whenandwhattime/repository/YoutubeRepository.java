package com.example.whenandwhattime.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.whenandwhattime.entity.Youtube;

@Repository
public interface YoutubeRepository extends JpaRepository<Youtube, Long> {

	 Iterable<Youtube> findAllByOrderByIdDesc();
	 
	 Iterable<Youtube> deleteALLByLivers_id(long id);

	boolean existsByVideoid(String id);
}