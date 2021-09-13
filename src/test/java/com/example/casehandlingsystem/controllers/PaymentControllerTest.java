package com.example.casehandlingsystem.controllers;

import com.example.casehandlingsystem.constants.Currency;
import com.example.casehandlingsystem.domain.PaymentCollectionInterface;
import com.example.casehandlingsystem.domain.PaymentRecord;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PaymentController.class)
@ComponentScan(basePackages = {"com.example.casehandlingsystem.domain", "com.example.casehandlingsystem.repositories"})
public class PaymentControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    @Autowired
    PaymentCollectionInterface collection;

    @Test
    public void canCreateANewPayment() throws Exception {
        PaymentRecord payment = new PaymentRecord(50, Currency.DOLLAR);
        payment.setId(10L);

// when
        ResultActions actions = mockMvc.perform(
                post("/payment/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(
                                mapper.writeValueAsString(payment)));


        // then
        MockHttpServletResponse response = actions.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.amount", is(50.0)))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());


    }

    @Test
    public void canGetPayment() throws Exception {
        PaymentRecord payment = new PaymentRecord(40, Currency.EURO);
        payment.setId(2L);
// given
        collection.save(payment);

// when
        ResultActions actions = mockMvc.perform(
                get("/payment/2"));


        // then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(2)));
    }

    @Test
    public void timeInTheFuture() throws Exception {
// when
        ResultActions actions = mockMvc.perform(
                get("/payment/unresolved/sum")
                        .param("year", "2022")
                        .param("month", "1")
                        .param("day", "15"));


        // then
        actions.andExpect(status().isBadRequest());
    }

}