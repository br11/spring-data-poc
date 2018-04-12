package com.github.br11.datamvc.repo;

import java.util.Date;

import org.springframework.boot.test.context.SpringBootTest;

import com.github.br11.datamvc.App;
import com.github.br11.datamvc.model.Address;
import com.github.br11.datamvc.model.Person;

/**
 * 
 * @author voluntários
 *
 */
@SpringBootTest(classes = App.class)
public class PersonControllerTest extends SimpleControllerTest<Person> {

	@Override
	public void setup() throws Exception {
		Address address = new Address("location", new Date());
		setEntities(new Person("Doe", "John", 30, address, address), //
				new Person("Doe", "Joe", 30, address, address), //
				new Person("Doe", "Bob", 30, address, address), //
				new Person("Doe", "Mary", 30, address, address), //
				new Person("Doe", "John", 45, address, address));
		super.setup();
	}

}