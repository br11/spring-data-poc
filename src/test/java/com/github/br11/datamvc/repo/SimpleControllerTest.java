package com.github.br11.datamvc.repo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.br11.datamvc.App;

/**
 * 
 * @author voluntários
 *
 */
@SpringBootTest(classes = App.class)
public class SimpleControllerTest<T> extends BaseControllerTest {

	private List<T> entities = new ArrayList<T>();
	private T entityBeforeUpd;
	private T entityAfterUpd;

	@SuppressWarnings("unchecked")
	protected void setEntities(T... entities) {
		setEntities(Arrays.asList(entities));
	}

	protected void setEntities(Collection<T> entities) {
		this.entities.clear();
		for (T entity : entities) {
			int index = this.entities.indexOf(entity);
			if (index == -1) {
				this.entities.add(entity);
			} else {
				entityBeforeUpd = this.entities.get(index);
				entityAfterUpd = entity;
				this.entities.remove(index);
			}
		}
	}

	@Test
	public void testCreate() throws Exception {
		testCreate("/data/people/", entities.get(0));
	}

	@Test
	public void testFindAll() throws Exception {
		testFindAll("/data/people/", entities.toArray()); 
	}

	@Test
	public void testUpdate() throws Exception {
		testUpdate("/data/people/", entityBeforeUpd, entityAfterUpd);
		testFindAll();
	}

	@Test
	public void testGet() throws Exception {
		testCreateAndGet("/data/people/", entities.get(0));
	}
}