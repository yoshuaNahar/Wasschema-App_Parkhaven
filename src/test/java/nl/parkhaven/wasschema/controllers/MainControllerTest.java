package nl.parkhaven.wasschema.controllers;

import nl.parkhaven.wasschema.modules.bulletinboard.BulletinBoardService;
import nl.parkhaven.wasschema.modules.bulletinboard.Message;
import nl.parkhaven.wasschema.modules.schema.SchemaService;
import nl.parkhaven.wasschema.modules.user.LoginService;
import nl.parkhaven.wasschema.modules.user.User;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;

import static org.mockito.Mockito.when;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(MockitoJUnitRunner.Silent.class)
// Because Stubbing is performed indirectly for the housenumbersMachines
public class MainControllerTest {

    @Mock
    private SchemaService schemaService;
    @Mock
    private BulletinBoardService bulletinBoardService;

    @InjectMocks
    private MainController mainController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(mainController)
                .build();
    }

    @Test
    public void testGetLoginPage() throws Exception {
        User user = new User();
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", user);

        when(schemaService.getData(ArgumentMatchers.any(Integer.class))).thenReturn(new String[2][91]);
        when(bulletinBoardService.getMessages()).thenReturn(new HashMap<Long, Message>());

        RequestBuilder request = get("/index.010/loggedin").session(session)
                .param("week", "current")
                .param("laundryRoom", "1");

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/index.010"));
    }

}
