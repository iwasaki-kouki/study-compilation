package com.example.whenandwhattime.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.whenandwhattime.entity.Livers;

@Repository
public interface LiversRepository extends JpaRepository<Livers, Long> {

	 Iterable<Livers> findAllByOrderByIdDesc();
}