package com.divya.spring.datajpa.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.divya.spring.datajpa.model.DependentParticipant;
import com.divya.spring.datajpa.model.Participant;
import com.divya.spring.datajpa.repository.DependentParticipantRepository;
import com.divya.spring.datajpa.repository.ParticipantRepository;

@RestController
@RequestMapping("/api")
public class ParticipantController {

	@Autowired
	ParticipantRepository participantRepository;

	@Autowired
	DependentParticipantRepository dependentParticipantRepository;
	
	@GetMapping("/participant/enroll")
	public ResponseEntity<List<Participant>> getAllParticipants(@RequestParam(required = false) String title) {
		try {
			List<Participant> tutorials = new ArrayList<Participant>();

			if (title == null)
				participantRepository.findAll().forEach(tutorials::add);
			else
				participantRepository.findByFirstNameContaining(title).forEach(tutorials::add);

			if (tutorials.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(tutorials, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/participant/enroll/{id}")
	public ResponseEntity<Participant> getPartcipantById(@PathVariable("id") long id) {
		Optional<Participant> tutorialData = participantRepository.findById(id);

		if (tutorialData.isPresent()) {
			return new ResponseEntity<>(tutorialData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/participant")
	public ResponseEntity<Participant> createParticipantEnroll(@RequestBody Participant tutorial) {
		try {
			Participant participant = new Participant(tutorial.getFirstName(), tutorial.getLastName(),tutorial.getPhoneNumber(),tutorial.getDateOfBirth());
			if(!CollectionUtils.isEmpty(tutorial.getDependents())) {
				participant.setDependents(tutorial.getDependents());
			}
			Participant _tutorial = participantRepository
					.save(participant);
			return new ResponseEntity<>(_tutorial, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/partcipants/{id}/update")
	public ResponseEntity<Participant> updateParticipant(@PathVariable("id") long id, @RequestBody Participant participantEnroll) {
		Optional<Participant> retrievedParticipantData = participantRepository.findById(id);

		if (retrievedParticipantData.isPresent()) {
			Participant _enrollupdate = retrievedParticipantData.get();
			_enrollupdate.setFirstName(participantEnroll.getFirstName());
			_enrollupdate.setLastName(participantEnroll.getLastName());
			_enrollupdate.setPhoneNumber(participantEnroll.getPhoneNumber());
			_enrollupdate.setDateOfBirth(participantEnroll.getDateOfBirth());
			_enrollupdate.setActivationStatus(participantEnroll.isActivationStatus());
			return new ResponseEntity<>(participantRepository.save(_enrollupdate), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/partcipants/{id}/addDependent")
	public ResponseEntity<Participant> addDependents(@PathVariable("id") long id, @RequestBody DependentParticipant participantEnroll) {
		Optional<Participant> retrievedParticipantData = participantRepository.findById(id);

		if (retrievedParticipantData.isPresent()) {
			Participant _enrollupdate = retrievedParticipantData.get();
			_enrollupdate.getDependents().add(participantEnroll);
			return new ResponseEntity<>(participantRepository.save(_enrollupdate), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/partcipants/{id}/updateDependent")
	public ResponseEntity<DependentParticipant> updateDependent(@PathVariable("id") long id, @RequestBody DependentParticipant participantEnroll) {
		Optional<DependentParticipant> retrievedParticipantData = dependentParticipantRepository.findById(id);

		if (retrievedParticipantData.isPresent()) {
			DependentParticipant _enrollupdate = retrievedParticipantData.get();
			_enrollupdate.setDateOfBirth(participantEnroll.getDateOfBirth());
			return new ResponseEntity<>(dependentParticipantRepository.save(_enrollupdate), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/partcipants/{id}/dependent/{depId}")
	public ResponseEntity<HttpStatus> deleteDependentParticipant(@PathVariable("id") long id,@PathVariable("depId") long depId) {
		try {
			Optional<Participant> retrievedParticipantData = participantRepository.findById(id);
			if (retrievedParticipantData.isPresent()) {
				Participant _enrollupdate = retrievedParticipantData.get();
				_enrollupdate.getDependents().removeIf(dept -> dept.getId() == depId);
				participantRepository.save(_enrollupdate);
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/partcipants/{id}")
	public ResponseEntity<HttpStatus> deleteParticipant(@PathVariable("id") long id) {
		try {
			participantRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/participants")
	public ResponseEntity<HttpStatus> deleteAllParticipants() {
		try {
			participantRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/participants/dateOfBirth")
	public ResponseEntity<List<Participant>> findByPublished() {
		try {
			List<Participant> tutorials = participantRepository.findByDateOfBirth(new Date(new java.util.Date().getTime()));

			if (tutorials.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(tutorials, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
