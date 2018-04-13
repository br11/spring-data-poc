package com.github.br11.datamvc.repo;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.github.br11.datamvc.model.PublicPerson;

//@RepositoryRestResource(collectionResourceRel = "people", path = "people")
public interface PersonRepository extends PagingAndSortingRepository<PublicPerson, Long> {

	List<PublicPerson> findByLastName(@Param("name") String name);
	
	List<PublicPerson> findByLastNameAndFirstName(@Param("lname") String lname,@Param("fname") String fname);

}