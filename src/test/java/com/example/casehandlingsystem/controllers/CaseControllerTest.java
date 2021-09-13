package com.example.casehandlingsystem.controllers;

import com.example.casehandlingsystem.constants.Country;
import com.example.casehandlingsystem.constants.Currency;
import com.example.casehandlingsystem.domain.CaseCollectionInterface;
import com.example.casehandlingsystem.domain.CaseRecord;
import com.example.casehandlingsystem.domain.PaymentCollectionInterface;
import com.example.casehandlingsystem.domain.PaymentRecord;
import com.example.casehandlingsystem.helpers.CasePost;
import com.example.casehandlingsystem.helpers.CasePut;
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

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CaseController.class)
@ComponentScan(basePackages = {"com.example.casehandlingsystem.repositories", "com.example.casehandlingsystem.domain"})
public class CaseControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private CaseCollectionInterface caseCollection;
    @Autowired
    private PaymentCollectionInterface paymentCollection;

    @Test
    public void canCreateANewCase() throws Exception {
        PaymentRecord payment = new PaymentRecord(50, Currency.DOLLAR);
        payment.setId(1L);
        // given
        paymentCollection.save(payment);
// when
        MockHttpServletResponse response = mockMvc.perform(
                post("/case/new").contentType(MediaType.APPLICATION_JSON).content(
                        new ObjectMapper().writeValueAsString(new CasePost(1L, "CODE1", Country.DENMARK))
                )).andReturn().getResponse();
        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    public void canRetrieveByIdWhenExists() throws Exception {
        CaseRecord record = new CaseRecord(new PaymentRecord(6, Currency.DOLLAR), Country.FINLAND);
        record.setId(5L);
        // given
        caseCollection.save(record);
// when
        MockHttpServletResponse response = mockMvc.perform(
                get("/case/id/5")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).startsWith("{\"id\":5,");

    }

    @Test
    public void canRetrieveByIdWhenDoesNotExist() throws Exception {
// given
        // when
        MockHttpServletResponse response = mockMvc.perform(
                get("/case/id/3")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());

    }

    @Test
    public void canRetrieveListByCountryWhenExists() throws Exception {
        List<CaseRecord> cases = new ArrayList<>();
        PaymentRecord payment = new PaymentRecord();
        cases.add(new CaseRecord(payment, Country.SWEDEN));
        cases.add(new CaseRecord(payment, Country.SWEDEN));
        cases.add(new CaseRecord(payment, Country.SWEDEN));
// given
        Long i = 0L;
        for (CaseRecord c : cases) {
            ++i;
            c.setId(i);
            caseCollection.save(c);
        }
        // when
        ResultActions actions = mockMvc.perform(
                get("/case/resolve/SWEDEN"));

        // then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void canRetrieveListByCountryWhenDoesNotExist() throws Exception {
// given
        // when
        ResultActions actions = mockMvc.perform(
                get("/case/resolve/SWEDEN"));

        // then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void canUpdateCase() throws Exception {
        CaseRecord record = new CaseRecord();
        record.setId(6L);
        record.setState(false);
        record.setNote("NOTE");
        record.setPayment(new PaymentRecord());
        // given
        caseCollection.save(record);
        // when
        MockHttpServletResponse response = mockMvc.perform(
                put("/case/resolve")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new CasePut(6L, true, "NOTE"))))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    }

}