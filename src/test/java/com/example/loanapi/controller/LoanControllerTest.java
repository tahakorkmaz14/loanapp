package com.example.loanapi.controller;

import java.util.Base64;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.loanapi.config.TestSecurityConfig;
import com.example.loanapi.dto.LoanResponse;
import com.example.loanapi.service.LoanService;

@WebMvcTest(LoanController.class)
@Import(TestSecurityConfig.class) // Use test-specific security config
class LoanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoanService loanService;

    @Test
    void shouldCreateLoanSuccessfully() throws Exception {
        LoanResponse response = new LoanResponse(1L, 1000.0, 6, false);

        Mockito.when(loanService.createLoan(Mockito.anyLong(), Mockito.anyDouble(), Mockito.anyDouble(), Mockito.anyInt()))
                .thenReturn(response);

        mockMvc.perform(post("/api/loans")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"customerId\":1,\"loanAmount\":1000.0,\"interestRate\":0.2,\"installments\":6}")
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("admin:adminpass".getBytes())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.loanAmount").value(1000.0))
                .andExpect(jsonPath("$.numberOfInstallments").value(6));

    }
}
