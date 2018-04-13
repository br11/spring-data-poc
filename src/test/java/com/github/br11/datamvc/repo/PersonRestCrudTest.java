package com.github.br11.datamvc.repo;

import java.util.Date;

import org.springframework.boot.test.context.SpringBootTest;

import com.github.br11.datamvc.App;
import com.github.br11.datamvc.model.Address;
import com.github.br11.datamvc.model.PublicPerson;

/**
 * 
 * @author voluntários
 *
 */
@SpringBootTest(classes = App.class)
public class PersonRestCrudTest extends SimpleRestCrudTest<PublicPerson> {

	@Override
	public void setup() throws Exception {
		Address address = new Address("location", new Date());
		setEntities(new PublicPerson("Doe", "John", 30, address, address), //
				new PublicPerson("Doe", "Joe", 30, address, address), //
				new PublicPerson("Doe", "Bob", 30, address, address), //
				new PublicPerson("Doe", "Mary", 30, address, address), //
				new PublicPerson("Doe", "John", 45, address, address));
		super.setup();
	}

}