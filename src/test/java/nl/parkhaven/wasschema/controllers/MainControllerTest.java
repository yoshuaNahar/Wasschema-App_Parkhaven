package nl.parkhaven.wasschema.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import nl.parkhaven.wasschema.modules.appointment.AppointmentService;
import nl.parkhaven.wasschema.modules.bulletinboard.BulletinBoardService;
import nl.parkhaven.wasschema.modules.misc.MetaDataDao;
import nl.parkhaven.wasschema.modules.schema.SchemaService;
import nl.parkhaven.wasschema.modules.user.ModifyUserService;
import nl.parkhaven.wasschema.modules.user.User;

@RunWith(MockitoJUnitRunner.class)
public class MainControllerTest {

	@Mock
	private AppointmentService appointmentService;
	@Mock
	private ModifyUserService modifyUserService;
	@Mock
	private BulletinBoardService bulletinBoardService;
	@Mock
	private SchemaService schemaService;
	@Mock
	private MetaDataDao miscDbOperations;

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

		RequestBuilder request = get("/index.010").session(session)
				.param("week", "current")
				.param("laundryRoom", "1");

		String[][] washDates = new String[][] { { "1", "11" }, { "2", "22" } };

		when(schemaService.getTimes()).thenReturn(new HashMap<Long, String>());
		when(schemaService.getWashingMachines()).thenReturn(new HashMap<Long, String>());
		when(schemaService.getData(ArgumentMatchers.anyInt())).thenReturn(washDates);

		mockMvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(view().name("afterlogin"))
				.andExpect(model().attribute("huis_nummer1", is(washDates[0])));
	}

}
