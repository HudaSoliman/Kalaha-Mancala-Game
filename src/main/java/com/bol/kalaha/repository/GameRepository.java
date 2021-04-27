package com.bol.kalaha.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.bol.kalaha.model.Game;

public interface GameRepository extends CrudRepository<Game, Integer> {

	Optional<Game> findById(long id);
	
	
}
