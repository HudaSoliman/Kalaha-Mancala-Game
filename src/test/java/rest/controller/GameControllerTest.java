package rest.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.Assert;

import com.bol.kalaha.KalahaApplication;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(classes = KalahaApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
		"management.server.port=0" })
public class GameControllerTest {

	@Autowired
	private MockMvc mvc;

	@LocalServerPort
	private int port;


	@Test
	public void getGame() throws Exception {
		String uri = "/api/games/1";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(HttpStatus.OK.value(), status);
		String content = mvcResult.getResponse().getContentAsString();
		JSONObject response = new JSONObject(content);
		Assert.isTrue(response.get("id").equals(1), "wrong object retrieved");
	}
	
	@Test
	public void createGame() throws Exception {
		String uri = "/api/games/";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(HttpStatus.CREATED.value(), status);
	}

	@Test
	public void playGameWithInvalidTurn() throws Exception {
		String uri = "/api/games/1/pits/8";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(HttpStatus.BAD_REQUEST.value(), status);
		String content = mvcResult.getResponse().getContentAsString();
		Assert.hasText(content, "Illegal move: It's First Player's turn");
	}
	
	@Test
	public void playGameWithRightTurn() throws Exception {
		String uri = "/api/games/2/pits/15";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(HttpStatus.OK.value(), status);
		String content = mvcResult.getResponse().getContentAsString();
		JSONObject response = new JSONObject(content);
		Assert.isTrue(response.get("turn").equals("SECOND_PLAYER"), "turn has NOT changed");
	}
	
	
	
	


}