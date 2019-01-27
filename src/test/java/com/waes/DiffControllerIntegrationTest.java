package com.waes;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.waes.service.DiffService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = DiffServiceApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
  locations = "classpath:application-integrationtest.properties")
public class DiffControllerIntegrationTest {

	@Autowired
    private MockMvc mvc;
 
    @Autowired
    private DiffService diffService;

    
    @Test
    public void givenBody_whenSaveLeft_thenReturn200() throws Exception {
		mvc.perform(post("/v1/diff/1/left")
				.content("abc")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
    	
    }
}
