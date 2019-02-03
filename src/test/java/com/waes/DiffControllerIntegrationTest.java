package com.waes;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waes.model.Body;
import com.waes.model.DiffSide;
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

    @Autowired
    ObjectMapper objectMapper;
    
    @Test
    public void givenBody_whenSaveLeft_thenReturn200() throws Exception {
		mvc.perform(post("/v1/diff/1/left")
				.content(objectMapper.writeValueAsString(new DataPayload("abc")))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
    	
    }
    
    @Test
    public void givenBody_whenSaveRight_thenReturn200() throws Exception {
		mvc.perform(post("/v1/diff/1/right")
				.content(objectMapper.writeValueAsString(new DataPayload("abc")))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
    	
    }

    @Test
    public void givenSameBody_whenGetDiff_thenReturnEqual() throws Exception {
    		// Given same body
    		diffService.saveBody(new Body(1L, DiffSide.LEFT, "abc".getBytes()));
    		diffService.saveBody(new Body(1L, DiffSide.RIGHT, "abc".getBytes()));
    		
    		mvc.perform(get("/v1/diff/1")
    				.contentType(MediaType.APPLICATION_JSON))
    				.andExpect(status().isOk());    		
    }
    
    @Test
    public void givenBody_whenSaveLeftWithOtherContentType_thenReturn415() throws Exception {
		mvc.perform(post("/v1/diff/1/left")
				.contentType(MediaType.APPLICATION_XML))
				.andExpect(status().is(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()));
    }

    @Test
    public void givenBody_whenSaveWithInvalidBase64Payload_thenReturn422() throws Exception {
		mvc.perform(post("/v1/diff/1/left")
				.content(objectMapper.writeValueAsString(new DataPayload("SGVsbG8gV0FFUw=")))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(HttpStatus.UNPROCESSABLE_ENTITY.value()));
    }
}
