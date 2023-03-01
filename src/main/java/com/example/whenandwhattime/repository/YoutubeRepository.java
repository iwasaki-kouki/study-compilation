package com.example.whenandwhattime.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.whenandwhattime.entity.Youtube;

@Repository
public interface YoutubeRepository extends JpaRepository<Youtube, Long> {

	 Iterable<Youtube> findAllByOrderByIdDesc();

	boolean existsByVideoid(String id);
}