package com.github.br11.datamvc.repo;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.github.br11.datamvc.model.Address;

//@RepositoryRestResource(collectionResourceRel = "Addresses", path = "Addresses")
public interface AddressRepository extends PagingAndSortingRepository<Address, Long> {

}