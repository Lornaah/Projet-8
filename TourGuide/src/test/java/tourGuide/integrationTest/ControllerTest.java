package tourGuide.integrationTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testGreetings() throws Exception {
		mockMvc.perform(post("/")).andExpect(status().isOk());
	}

	@Test
	public void testGetLocation() throws Exception {

		MvcResult result = mockMvc.perform(post("/getLocation").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("userName", "internalUser3")).andExpect(status().isOk()).andReturn();

		String response = result.getResponse().getContentAsString();

		ObjectMapper mapper = new ObjectMapper();
		JsonNode json = mapper.readTree(response);

		assertFalse(json.get("longitude") == null);
		assertFalse(json.get("latitude") == null);
	}

	@Test
	public void testGetNearbyAttractions() throws Exception {
		MvcResult result = mockMvc.perform(post("/getNearbyAttractions")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED).param("userName", "internalUser3"))
				.andExpect(status().isOk()).andReturn();

		String response = result.getResponse().getContentAsString();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode json = mapper.readTree(response);
		json.forEach(n -> {
			assertFalse(n.get("name") == null);
			assertFalse(n.get("rewardPoints") == null);
			assertFalse(n.get("attractionLongitude") == null);
			assertFalse(n.get("userLatitude") == null);
		});
	}

	@Test
	public void testGetRewards() throws Exception {
		mockMvc.perform(post("/getRewards").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("userName",
				"internalUser3")).andExpect(status().isOk()).andExpect(jsonPath("$").isArray());
	}

	@Test
	public void testGetAllCurrentLocations() throws Exception {
		MvcResult result = mockMvc.perform(post("/getAllCurrentLocations")).andExpect(status().isOk())
				.andExpect(jsonPath("$").exists()).andExpect(jsonPath("$").isArray()).andReturn();

		String response = result.getResponse().getContentAsString();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode json = mapper.readTree(response);

		json.forEach(n -> {
			assertFalse(n.get("location") == null);
			assertFalse(n.get("userId") == null);
			for (JsonNode visitedLocation : n.get("location")) {
				assertFalse(visitedLocation.get("latitude") == null);
				assertFalse(visitedLocation.get("longitude") == null);
			}
		});
	}

	@Test
	public void testGetTripDeals() throws Exception {
		MvcResult result = mockMvc.perform(post("/getTripDeals").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("userName", "internalUser3")).andExpect(status().isOk()).andExpect(jsonPath("$").isArray())
				.andReturn();
		String response = result.getResponse().getContentAsString();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode json = mapper.readTree(response);
		json.forEach(n -> {
			assertFalse(n.get("name") == null);
			assertFalse(n.get("price") == null);
			assertFalse(n.get("tripId") == null);
		});
	}
}
