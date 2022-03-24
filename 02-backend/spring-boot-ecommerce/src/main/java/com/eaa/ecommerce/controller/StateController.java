package com.eaa.ecommerce.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.eaa.ecommerce.exception.NotFoundException;
import com.eaa.ecommerce.model.State;
import com.eaa.ecommerce.repository.StateRepository;

@RestController
@CrossOrigin(origins = "https://localhost:4200")
public class StateController {

	@Autowired
	private StateRepository stateRepository;

	@GetMapping("/states")
	public List<State> getStates() {
		List<State> stateResponse = stateRepository.findAll();
		return stateResponse;
	}
	
	@GetMapping("/state/{id}")
	public Optional<State> getState(@PathVariable("id") int id) {
		Optional<State> stateResponse = stateRepository.findById(id);
		if (!stateResponse.isPresent()) {
			throw new NotFoundException("id - " + id);
		}
		return stateResponse;
	}
	
	@GetMapping("/states/search/findByCountryCode/{code}")
	public List<State> getStatesByCountryCode(@PathVariable("code") String code) {
		List<State> stateResponse = stateRepository.findByCountryCode(code);
		return stateResponse;
	}
	
	

}
