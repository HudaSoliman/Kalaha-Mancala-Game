package com.kalaha.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.kalaha.model.Game;

public interface GameRepository extends CrudRepository<Game, Long> {

	Optional<Game> findById(Long id);
	
	
}
