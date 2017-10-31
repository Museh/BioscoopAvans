package avans.bioscoop;

import avans.bioscoop.BioscoopAvansApplication;
import avans.bioscoop.controllers.OverviewController;
import avans.bioscoop.models.Movie;
import avans.bioscoop.models.Viewing;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Created by Frank van Vugt on 04/05/2017.
 */
@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = BioscoopAvansApplication.class)

public class BioscoopAvansApplicationTests {

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private OverviewController overviewController;

	private InternalResourceViewResolver viewResolver;

	private MockMvc mockMvc;
	@Before
	public void setup() throws Exception{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		//this.mockMvc.perform(get("overview/overview"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now1 =sdf.parse("2017-10-31 19:15:00");
		Date now2 =sdf.parse("2017-10-31 14:15:00");
		Date now3 =sdf.parse("2017-10-31 16:15:00");
		Movie movie1 = new Movie("blade runner", 116, true, 18, "Engels", true, null);
		Movie movie2 = new Movie("Lord of the Rings", 116, true, 18, "Engels", true, null);
		Movie movie3 = new Movie("Star Wars", 116, true, 18, "Engels", true, null);

		List<Viewing> viewings = Arrays.asList(
				new Viewing(now1,movie1),
				new Viewing(now2,movie2),
				new Viewing(now3,movie3));

	}
	@Test
	public void testHomePage() throws Exception {
		this.mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(view().name("overview/overview"));
	}

	@Test
	public void testSearchWithResult() throws Exception {
		this.mockMvc.perform(post("/")
				.param("searchobject", "blade"))
				.andExpect(status().isOk())
				.andExpect(model().size(1));
	}

//	@Test
//	public void testGetLoginPage() throws Exception{
//		this.mockMvc.perform(get("/account/login")).andExpect(status().isOk())
//				.andExpect(content().string(containsString("<title>inloggen</title>")));
//	}
//
//	@Test
//	public void testGetRegisterPage() throws Exception{
//		this.mockMvc.perform(get("/account/registreer")).andExpect(status().isOk())
//				.andExpect(content().string(containsString("<title>Registreren</title>")));
//	}

}