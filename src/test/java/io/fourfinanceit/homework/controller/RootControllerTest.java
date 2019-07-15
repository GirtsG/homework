package io.fourfinanceit.homework.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.h2.H2ConsoleProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.rest.webmvc.BaseUri;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.net.URI;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RootController.class)
public class RootControllerTest {

    @Value("${spring.data.rest.base-path}")
    private String dataBasePath;
    @Value("${spring.h2.console.path}")
    private String h2ConsolePath;
    @Autowired
    private MockMvc mvc;

    @MockBean
    private RepositoryRestMvcConfiguration repositoryRestMvcConfiguration;
    @MockBean
    private H2ConsoleProperties h2ConsoleProperties;

    @Test
    public void index_ok() throws Exception {
        given(this.repositoryRestMvcConfiguration.baseUri())
                .willReturn(new BaseUri(URI.create(dataBasePath)));
        given(this.h2ConsoleProperties.getPath())
                .willReturn(h2ConsolePath);
        final ResultActions result = this.mvc.perform(get(URI.create("/")).accept(MediaTypes.HAL_JSON_UTF8_VALUE)
                .contentType(MediaTypes.HAL_JSON_UTF8_VALUE));
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("_links.self.href").exists());
        result.andExpect(jsonPath("_links.data.href").exists());
        result.andExpect(jsonPath("_links." + h2ConsolePath + ".href").exists());
    }
}
