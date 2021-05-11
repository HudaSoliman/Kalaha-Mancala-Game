package com.kalaha.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.kalaha.model.Pit;

public interface PitRepository extends CrudRepository<Pit, Long> {

	List<Pit> findAll();

	List<Pit> findAllByGameId(Long id);

	Optional<Pit> findById(Long id);

}
