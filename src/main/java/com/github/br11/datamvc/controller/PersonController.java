package com.github.br11.datamvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.br11.datamvc.model.PublicPerson;
import com.github.br11.datamvc.repo.PersonRepository;

@RestController
public class PersonController {

	@Autowired
	private PersonRepository personRepo;

//	@PostMapping("/data/people")
	@ResponseStatus(HttpStatus.CREATED)
	public PublicPerson create(@RequestBody PublicPerson model) {
		System.out.println(">>> PersonController <<<");
		 
		return personRepo.save(model);
	}	

}
