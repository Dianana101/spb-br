package com.example.spb_br;

import com.example.spb_br.constant.AccountStatus;
import com.example.spb_br.constant.Currency;
import com.example.spb_br.constant.PurchaseType;
import com.example.spb_br.controller.AccountController;
import com.example.spb_br.dto.AccountDto;
import com.example.spb_br.service.AccountServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.mockito.BDDMockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = {AccountController.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountServiceImpl accountService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    FormattingConversionService mvcConversionService;

    AccountDto account;

    @BeforeEach
    public void setup(){
        account = AccountDto.builder()
                .id(1L)
                .mainBalance(5000)
                .currency(Currency.RUR)
                .status(AccountStatus.ACTIVE)
                .build();
    }

    @Test
    @Order(1)
    public void createAccountTest() throws Exception {
        given(accountService.saveAccount(any(AccountDto.class))).willReturn(account);

        ResultActions response = mockMvc.perform(post("/api/account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(account)));

        response.andExpect(status().isCreated());
    }

    @Test
    @Order(2)
    public void paymentTest() throws Exception {
        Mockito.when(accountService.findAccountById(1)).thenReturn(account);
        ResultActions response = mockMvc.perform(get("/api/account/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(account)));
        response.andDo(print());

        Mockito.when(accountService.getAccountAfterPurchase(1l, PurchaseType.ONLINE_PURCHASE, 100)).thenReturn(account);

        ResultActions response2 = mockMvc.perform(get("/api/payment/online/100/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(account)));
        mockMvc.perform(get("/api/payment/online/100/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(account)));
    }
}
