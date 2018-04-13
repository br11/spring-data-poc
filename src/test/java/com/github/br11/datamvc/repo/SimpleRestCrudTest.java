package com.github.br11.datamvc.repo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.atteo.evo.inflector.English;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.br11.datamvc.App;

/**
 * 
 * @author voluntários
 *
 */
@SpringBootTest(classes = App.class)
public class SimpleRestCrudTest<T> extends BaseRestCrudTest {

	@SuppressWarnings("rawtypes")
	private static SimpleRestCrudTest singleton;

	@Value(value = "${spring.data.rest.basePath:null}") // TODO evitando warnnings
	private String restDataPath;

	private String path;
	private List<T> entities = new ArrayList<T>();
	private T entityBeforeUpd;
	private T entityAfterUpd;

	public SimpleRestCrudTest() {
		singleton = this;
	}

	@SuppressWarnings("unchecked")
	protected void setEntities(T... entities) {
		String entityPath = English.plural(entities[0].getClass().getSimpleName());
		entityPath = "/" + entityPath.substring(0, 1).toLowerCase() + entityPath.substring(1);

		// TODO evitando warnnings
		if (restDataPath.equals("null")) {
			restDataPath = "";
		}
		//

		setEntities(restDataPath + entityPath, entities);
	}

	@SuppressWarnings("unchecked")
	protected void setEntities(String path, T... entities) {
		setEntities(path, Arrays.asList(entities));
	}

	@BeforeClass
	public static void resetIndex() {
		index = 0;
	}

	@AfterClass
	public static void showData() throws Exception {
		singleton.testRetrieveAll(singleton.path);
	}

	private static int index;

	protected void setEntities(String path, Collection<T> entities) {
		if (entities.size() < 5) {
			throw new IllegalArgumentException(
					"Entity list must contains at least 5 items otherwise not all the CRUD methods can be tested. The list contains "
							+ entities.size() + "items.");
		}

		this.path = path;
		this.entities.clear();
		for (T entity : entities) {
			int i = this.entities.indexOf(entity);
			if (i == -1) {
				this.entities.add(entity);
			} else {
				entityBeforeUpd = this.entities.get(i);
				entityAfterUpd = entity;
				this.entities.remove(i);
			}
		}

		if (entityBeforeUpd == null) {
			throw new IllegalArgumentException(
					"Entity list must contains two equal entities otherwise the Update method cannot be tested.");
		}
	}

	@Test
	public void testCreate() throws Exception {
		testCreate(path, nextEntity());
	}

	@Test
	public void testUpdate() throws Exception {
		testUpdate(testCreate(path, entityBeforeUpd), entityAfterUpd);
	}

	@Test
	public void testRetrieve() throws Exception {
		testRetrieve(testCreate(path, nextEntity()));
	}

	protected T nextEntity() {
		try {
			return entities.get(index++);
		} catch (IndexOutOfBoundsException e) {
			throw new IllegalStateException(
					"No more unused entity in the list.");
		}
	}

	@Test
	public void testDelete() throws Exception {
		testDelete(testCreate(path, nextEntity()));
	}
}