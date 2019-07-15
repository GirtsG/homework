package io.fourfinanceit.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.fourfinanceit.homework.entity.LoanApplication;
import io.fourfinanceit.homework.service.LoanApplicationService;
import io.fourfinanceit.homework.value.LoanApplicationResource;
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

import java.math.BigDecimal;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(LoanApplicationController.class)
public class LoanApplicationControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private LoanApplicationService service;

    @Test
    public void applyForLoan_successful() throws Exception {
        final BigDecimal amount = BigDecimal.valueOf(150);
        final String ipAddress = "127.0.0.1";
        final Integer termInDays = 30;
        final String borrowerPersonId = "010190-10028";
        final String borrowerFullName = "Juris Ozols";
        LoanApplicationResource resource = new LoanApplicationResource();
        resource.setAmount(amount);
        resource.setIpAddress(ipAddress);
        resource.setTermInDays(termInDays);
        resource.setBorrowerPersonId(borrowerPersonId);
        resource.setBorrowerFullName(borrowerFullName);
        LoanApplication loanApplication = new LoanApplication();
        loanApplication.setAmount(amount);
        loanApplication.setIpAddress(ipAddress);
        loanApplication.setTermInDays(termInDays);

        given(this.service.createLoanApplication(resource))
                .willReturn(loanApplication);
        final ResultActions result = this.mvc.perform(post("/apply-for-loan").accept(MediaTypes.HAL_JSON_UTF8_VALUE)
                .contentType(MediaTypes.HAL_JSON_UTF8_VALUE)
                .content(mapper.writeValueAsBytes(resource)));
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("amount", Is.is(amount.intValue())));
        result.andExpect(jsonPath("ipAddress", Is.is(ipAddress)));
        result.andExpect(jsonPath("termInDays", Is.is(termInDays)));
    }
}
