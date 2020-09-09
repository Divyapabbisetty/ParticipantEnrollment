package com.divya.spring.datajpa.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.divya.spring.datajpa.model.DependentParticipant;

public interface DependentParticipantRepository extends JpaRepository<DependentParticipant, Long> {
	List<DependentParticipant> findByDateOfBirth(Date dateOfBirth);
	List<DependentParticipant> findByFirstNameContaining(String firstName);
}
