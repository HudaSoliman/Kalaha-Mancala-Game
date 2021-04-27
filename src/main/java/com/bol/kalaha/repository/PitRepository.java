package com.bol.kalaha.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.bol.kalaha.model.Pit;

public interface PitRepository extends CrudRepository<Pit, Integer> {

	List<Pit> findAll();

	List<Pit> findAllByGameId(int id);

	Optional<Pit> findById(long id);

}
