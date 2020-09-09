package com.divya.spring.datajpa.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.divya.spring.datajpa.model.DependentParticipant;
import com.divya.spring.datajpa.model.Participant;

public interface DependentParticipantRepository extends JpaRepository<DependentParticipant, Long> {
	List<Participant> findByDateOfBirth(Date dateOfBirth);
	List<Participant> findByFirstNameContaining(String firstName);
}
