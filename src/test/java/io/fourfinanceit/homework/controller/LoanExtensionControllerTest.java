package io.fourfinanceit.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.fourfinanceit.homework.entity.LoanExtension;
import io.fourfinanceit.homework.service.LoanExtensionService;
import io.fourfinanceit.homework.value.LoanExtensionResource;
import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(LoanExtensionController.class)
public class LoanExtensionControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private LoanExtensionService service;

    @Test
    public void extendLoan_successful() throws Exception {
        LoanExtensionResource resource = new LoanExtensionResource();
        resource.setLoanId(1L);
        resource.setTermInDays(15);
        LoanExtension loanExtension = new LoanExtension();
        loanExtension.setTermInDays(15);
        given(this.service.extendLoan(resource))
                .willReturn(loanExtension);
        final ResultActions result = this.mvc.perform(post("/extend-loan").accept(MediaTypes.HAL_JSON_UTF8_VALUE)
                .contentType(MediaTypes.HAL_JSON_UTF8_VALUE)
                .content(mapper.writeValueAsBytes(resource)));
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("termInDays", Is.is(15)));
    }
}
