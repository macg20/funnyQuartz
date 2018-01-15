package pl.funnyqrz.tests.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.funnyqrz.rest.EchoRestService;
import pl.funnyqrz.tests.AbstractTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EchoTest extends AbstractTest {

    private final static String URL = "/echo";
    private final static String ECHO_RESPONSE = "Up";

    @InjectMocks
    private EchoRestService rest;

    private MockMvc mockMvc;

    @BeforeEach
    public void initMock() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(rest).build();
    }

    @Test
    public void echoTest() throws Exception {
        mockMvc.perform(get(URL)).andExpect(status().isOk());
        String response = mockMvc.perform(get(URL)).andReturn().getResponse().getContentAsString();
        assertThat(response).isEqualTo(ECHO_RESPONSE);
    }
}