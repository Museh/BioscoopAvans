package avans.bioscoop;

import avans.bioscoop.controllers.OverviewController;

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

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ContextConfiguration(classes = BioscoopAvansApplication.class)
@RunWith(SpringRunner.class)

public class test {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        this.mockMvc.perform(get("/overview"));
    }

    @Test
    public void testHomePage() throws Exception {
        this.mockMvc.perform(get("/overview/overview"))
                .andExpect(status().isOk())
                .andExpect(view().name("overview"));
    }
//    @Test
//    public void testIfSearchFindsMovies() throws Exception {
//        this.mockMvc.perform((post("/")
//                .param("searchobject", "Mountain"))
//                .hasItems(Viewing);
//
//    }

//    @Test
//    public void testLoginValidationWithRightValues() throws Exception{
//        this.mockMvc.perform(post("/account/overview")
//                .param("username", "admin")
//                .param("password", "admin"))
//                .andExpect(status().isOk())
//                .andExpect(content().string(containsString("Mijn overzicht")));
//    }
//
//    @Test
//    public void testLoginValidationWithNoValues() throws Exception{
//        this.mockMvc.perform(post("/account/overview")
//                .param("username", "")
//                .param("password", ""))
//                .andExpect(status().isOk())
//                .andExpect(content().string(containsString("is vereist")));
//    }

//    @Test
//    public void testCreateAccountValidationWithRightValues() throws Exception{
//        this.mockMvc.perform(post("/account/registreer")
//                .param("username", "test")
//                .param("password", "test")
//                .param("email", "test@test.nl"))
//                .andExpect(status().isOk())
//                .andExpect(content().string(containsString("succesvol aangemaakt")));
//    }
//
//    @Test
//    public void testCreateAccountValidationWithNoValues() throws Exception{
//        this.mockMvc.perform(post("/account/registreer")
//                .param("username", "")
//                .param("password", "")
//                .param("email", ""))
//                .andExpect(status().isOk())
//                .andExpect(content().string(containsString("is vereist")));
//    }
//
//    @Test
//    public void testCreateAccountValidationWithWrongEmail() throws Exception{
//        this.mockMvc.perform(post("/account/registreer")
//                .param("username", "test")
//                .param("password", "test")
//                .param("email", "test"))
//                .andExpect(status().isOk())
//                .andExpect(content().string(containsString("geldige mail is vereist")));
//    }





}