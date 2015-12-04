package com.realtimestudio.transport.web;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=App.class)
@WebAppConfiguration
public class RouteControllerTest {
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	private MockMvc mockMvc;
	private HttpMessageConverter mappingJackson2HttpMessageConverter;

	@Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

      //  this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream().filter(
       //         hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();

       // Assert.assertNotNull("the JSON message converter must not be null",
       //         this.mappingJackson2HttpMessageConverter);
    }
	
	@Test
	public void test() throws Exception {
		String result = mockMvc.perform(get("/transport/1311000002/lastlocation")).toString();
		System.out.println(mockMvc.perform(get("/transport/1311000002/lastlocation")));
	}

}
