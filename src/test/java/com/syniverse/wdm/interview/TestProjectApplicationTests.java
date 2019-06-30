package com.syniverse.wdm.interview;

import static org.assertj.core.api.Assertions.assertThat;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.syniverse.wdm.interview.armedforces.api.ArmedForcesController;
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestProjectApplicationTests {
	private static String ARMED_FORCES_URL = "http://localhost:9099/armed-forces/v1/";
	@Autowired
    private MockMvc mockMvc;
	@Autowired
    private ArmedForcesController controller;
	 	@Test
	    public void contexLoads() throws Exception {
	        assertThat(controller).isNotNull();
	    }
	 	@Test
	    public void testGetArmedForces() throws Exception {
	 		mockMvc.perform(MockMvcRequestBuilders.get(ARMED_FORCES_URL+"armies")
	                .contentType(MediaType.APPLICATION_JSON_UTF8))
	                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	    }
	 	
	 	@Test
	    public void testGetArmedForcesById1() throws Exception {
	 		mockMvc.perform(MockMvcRequestBuilders.get(ARMED_FORCES_URL+"armies/1")
	                .contentType(MediaType.APPLICATION_JSON_UTF8))
	                .andExpect(MockMvcResultMatchers.status().isOk())
	                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1)) 
	                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("NAVY"))
	                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("North navy")); 
	    }
	 	@Test
	    public void testGetArmedForcesById2() throws Exception {
	 		mockMvc.perform(MockMvcRequestBuilders.get(ARMED_FORCES_URL+"armies/2")
	                .contentType(MediaType.APPLICATION_JSON_UTF8))
	                .andExpect(MockMvcResultMatchers.status().isOk())
	                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2)) 
	                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("NAVY"))
	                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("South navy")); 
	    }
	 	
	 	@Test
	    public void testPostArmedForces() throws Exception {
	 		String json = "{ \"name\": \"Tien\", \"type\": \"NAVY\" }";
	 		mockMvc.perform(MockMvcRequestBuilders.post(ARMED_FORCES_URL+"armies")
	        		.content(json)
	                .contentType(MediaType.APPLICATION_JSON_UTF8))
	                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
//	 		isUnprocessableEntity()
	 		
	       
	        
	    }
	 	@Test
	    public void testPostArmedForcesWrongType() throws Exception {
	 		String json = "{ \"name\": \"Tien\", \"type\": \"NAVYYYYYYY\" }";
	 		mockMvc.perform(MockMvcRequestBuilders.post(ARMED_FORCES_URL+"armies")
	        		.content(json)
	                .contentType(MediaType.APPLICATION_JSON_UTF8))
	                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
	    }
	 	
	 	@Test
	    public void testPostRecruitUnit() throws Exception {
	 		String json = "{ \"name\": \"Tien\", \"type\": \"NAVYYYYYYY\" }";
	 		mockMvc.perform(MockMvcRequestBuilders.post(ARMED_FORCES_URL+"armies")
	        		.content(json)
	                .contentType(MediaType.APPLICATION_JSON_UTF8))
	                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
	    }
	 	
	 	@Test
	    public void testPostRecruitUnitOfArmy() throws Exception {
	 		String json = "{ \"combatPower\":1, \"type\": \"AIRCRAFT_CARRIER\" }";
	 		mockMvc.perform(MockMvcRequestBuilders.post(ARMED_FORCES_URL+"armies/1/units")
	        		.content(json)
	                .contentType(MediaType.APPLICATION_JSON_UTF8))
	                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	 		
	    }
	  
	 	@Test
	    public void testGetUnitIOfArmyId1() throws Exception {
	 		mockMvc.perform(MockMvcRequestBuilders.get(ARMED_FORCES_URL+"armies/1/units")
	                .contentType(MediaType.APPLICATION_JSON_UTF8))
	                .andExpect(MockMvcResultMatchers.status().isOk())
	                .andExpect(MockMvcResultMatchers.jsonPath("$.*", Matchers.hasSize(3)))
	                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value(1)) 
	                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].combatPower").value(20))
	                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].type").value("CORVETTE"))
	                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].id").value(2)) 
	                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].combatPower").value(80))
	                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].type").value("AIRCRAFT_CARRIER"))
	                .andExpect(MockMvcResultMatchers.jsonPath("$.[2].id").value(3)) 
	                .andExpect(MockMvcResultMatchers.jsonPath("$.[2].combatPower").value(30))
	                .andExpect(MockMvcResultMatchers.jsonPath("$.[2].type").value("CORVETTE"))
	                .andReturn();
	    }
	 	
	 	@Test
	    public void testGetUnitPower50OfArmyId1() throws Exception {
	 		mockMvc.perform(MockMvcRequestBuilders.get(ARMED_FORCES_URL+"armies/1/units/power50")
	                .contentType(MediaType.APPLICATION_JSON_UTF8))
	                .andExpect(MockMvcResultMatchers.status().isOk())
	                .andExpect(MockMvcResultMatchers.jsonPath("$.*", Matchers.hasSize(1)))
	                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value(2)) 
	                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].combatPower").value(80))
	                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].type").value("AIRCRAFT_CARRIER"))
	                .andReturn();
	    }
	 	
	 	@Test
	    public void testGetUnitPower50OfArmyId100() throws Exception {
	 		mockMvc.perform(MockMvcRequestBuilders.get(ARMED_FORCES_URL+"armies/100/units/power50")
	                .contentType(MediaType.APPLICATION_JSON_UTF8))
	                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
	    }
	 	
	 	
	 	@Test
	    public void testRemoveArmy() throws Exception {
	 		mockMvc.perform(MockMvcRequestBuilders.delete(ARMED_FORCES_URL+"armies/1")
	                .contentType(MediaType.APPLICATION_JSON_UTF8))
	                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
	    }
	 	
	 	@Test
	    public void testRemoveArmy5() throws Exception {
	 		mockMvc.perform(MockMvcRequestBuilders.delete(ARMED_FORCES_URL+"armies/5")
	                .contentType(MediaType.APPLICATION_JSON_UTF8))
	                .andExpect(MockMvcResultMatchers.status().isOk());
	    }

}
