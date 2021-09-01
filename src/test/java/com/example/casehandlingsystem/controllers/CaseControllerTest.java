package com.example.casehandlingsystem.controllers;

import com.example.casehandlingsystem.constants.Country;
import com.example.casehandlingsystem.constants.Currency;
import com.example.casehandlingsystem.exceptions.ErrorHandler;
import com.example.casehandlingsystem.exceptions.ItemNotFoundException;
import com.example.casehandlingsystem.helpers.CasePost;
import com.example.casehandlingsystem.helpers.CasePut;
import com.example.casehandlingsystem.models.Case;
import com.example.casehandlingsystem.models.Payment;
import com.example.casehandlingsystem.services.ICaseService;
import com.example.casehandlingsystem.services.IPaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CaseControllerTest {
    private MockMvc mvc;

    @Mock
    private ICaseService caseService;
    @Mock
    private IPaymentService paymentService;
    @InjectMocks
    private CaseController caseController;
    private JacksonTester<CasePost> jsonCasePost;
    private JacksonTester<Case> jsonCase;

    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(caseController)
                .setControllerAdvice(new ErrorHandler())
                .build();
    }

    @Test
    public void canCreateANewCase() throws Exception {
        Payment payment = new Payment(50, Currency.DOLLAR);
        payment.setId(1L);
        payment.setCode("CODE1");
        Case c = new Case(payment, Country.DENMARK);
        // given
        given(paymentService.findById(1)).willReturn(payment);
        given(paymentService.findByCode("CODE1")).willReturn(new ArrayList<>());
        given(paymentService.save(payment)).willReturn(payment);
        given(caseService.save(c)).willReturn(c);
        // when
        MockHttpServletResponse response = mvc.perform(
                post("/case/new").contentType(MediaType.APPLICATION_JSON).content(
                        jsonCasePost.write(new CasePost(1L, "CODE1", Country.DENMARK)).getJson()
                )).andReturn().getResponse();
        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    public void canRetrieveByIdWhenExists() throws Exception {
        Case c = new Case(new Payment(6, Currency.DOLLAR), Country.FINLAND);
        c.setId(5L);
        // given
        given(caseService.findById(5L)).willReturn(c);
// when
        MockHttpServletResponse response = mvc.perform(
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
        given(caseService.findById(3L)).willThrow(new ItemNotFoundException("case", 3L));
        // when
        MockHttpServletResponse response = mvc.perform(
                get("/case/id/3")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());

    }

    @Test
    public void canRetrieveListByCountryWhenExists() throws Exception {
        List<Case> cases = new ArrayList<>();
        cases.add(new Case(null, Country.SWEDEN));
        cases.add(new Case(null, Country.SWEDEN));
        cases.add(new Case(null, Country.SWEDEN));
// given
        given(caseService.findByCountryAndResolved(Country.SWEDEN, null)).willReturn(cases);
        // when
        ResultActions actions = mvc.perform(
                get("/case/resolve/SWEDEN"));

        // then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void canRetrieveListByCountryWhenDoesNotExist() throws Exception {
// given
        given(caseService.findByCountryAndResolved(Country.SWEDEN, null)).willReturn(new ArrayList<>());
        // when
        ResultActions actions = mvc.perform(
                get("/case/resolve/SWEDEN"));

        // then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void canUpdateCase() throws Exception {
        Case c = new Case(null, Country.DENMARK);
        c.setId(6L);
        c.setNote("NOTE");
        // given
        given(caseService.findById(6)).willReturn(c);

        given(caseService.save(c)).willReturn(c);

        // when
        MockHttpServletResponse response = mvc.perform(
                put("/case/resolve")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new CasePut(6L, true, "NOTE"))))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    }

}