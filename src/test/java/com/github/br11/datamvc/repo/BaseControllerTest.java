package com.github.br11.datamvc.repo;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

/**
 * 
 */
@RunWith(SpringRunner.class)
@WebAppConfiguration
public class BaseControllerTest {
	private MediaType contentType = MediaType.APPLICATION_JSON_UTF8;

	private MockMvc mockMvc;

	@SuppressWarnings("rawtypes")
	private HttpMessageConverter mappingJackson2HttpMessageConverter;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private void setConverters(HttpMessageConverter<?>[] converters) {
		this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
				.filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().orElse(null);

		assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
	}

	@Before
	public void setup() throws Exception {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
	}

	protected <T> T testCreate(String path, T entity) throws Exception {
		mockMvc.perform(post(path) //
				.content(this.json(entity)) //
				.contentType(contentType)) //
				.andExpect(status().isCreated()).andDo(print());

		return entity;
	}

	@SuppressWarnings("unchecked")
	protected <T> T[] testFindAll(String path, T... entities) throws Exception {
		for (T entity : entities) {
			testCreate(path, entity);
		}

		mockMvc.perform(get(path) //
				.contentType(contentType)) //
				.andExpect(status().isOk()).andDo(print());

		return entities;
	}

	protected <T> T testUpdate(String path, T entityBefore, T entityAfter) throws Exception {

		testCreate(path, entityBefore);

		mockMvc.perform(put("/data/people/1") //
				.content(this.json(entityAfter)) //
				.contentType(contentType)) //
				.andExpect(status().isNoContent());

		testGet(path, 1); // TODO

		return entityAfter;
	}

	protected <T> T testCreateAndGet(String path, T entity) throws Exception {
		testCreate(path, entity);

		mockMvc.perform(get(path + "/1")) // TODO
				.andExpect(status().isOk());

		return entity;
	}

	protected void testGet(String path, Object id) throws Exception {

		mockMvc.perform(get(path + "/" + id)) //
				.andExpect(status().isOk()).andDo(print());

	}

	@SuppressWarnings("unchecked")
	protected String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}
}