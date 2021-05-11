package com.kalaha.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kalaha.model.Pit;
import com.kalaha.repository.PitRepository;
import com.kalaha.rest.exception.PitNotFoundException;

@Service
public class PitServiceImpl implements PitService {


	@Autowired
	private PitRepository pitRepository;

	public PitServiceImpl(PitRepository repository) {
	}

	@Override
	@Transactional
	public Pit createPit() {
		Pit pit = new Pit();
		return pitRepository.save(pit);
	}

	@Override
	public Pit findPit(Long pitId) {
		Optional<Pit> g =  pitRepository.findById(pitId);
		if(g.isPresent()) {
			return g.get();
		}
		throw new PitNotFoundException(pitId);
	}
}
