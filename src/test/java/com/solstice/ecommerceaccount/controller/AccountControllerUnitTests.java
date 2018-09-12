package com.solstice.ecommerceaccount.controller;

import com.solstice.ecommerceaccount.domain.Account;
import com.solstice.ecommerceaccount.service.AccountServices;
import com.solstice.ecommerceaccount.service.AddressServices;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountController.class)
public class AccountControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountServices accountServices;

    @MockBean
    private AddressServices addressServices;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveAccount() throws Exception{
        String accountJson = "{ \"firstName\" : \"John\", \"lastName\" : \"Smith\", \"emailAddress\" : \"jsmith@aol.org\" }";

        Account mockAccount = new Account("John", "Smith", "jsmith@aol.org");
        mockAccount.setAccountId(1L);

        when(accountServices.saveAccount(ArgumentMatchers.any())).thenReturn(mockAccount);

        mockMvc.perform(post("/accounts").content(accountJson)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.firstName", is("John")))
                .andExpect(jsonPath("$.lastName", is("Smith")))
                .andExpect(jsonPath("$.emailAddress", is("jsmith@aol.org")))
                .andExpect(jsonPath("$.accountId", is(1)));
    }

    @Test
    public void testGetAllAccounts() throws Exception {

        List<Account> mockAccountList = new ArrayList<>();

        Account mockAccount1 = new Account("John", "Smith", "jsmith@aol.net");
        mockAccount1.setAccountId(1L);
        mockAccountList.add(mockAccount1);

        Account mockAccount2 = new Account("Jane", "Doe", "jdoe@aol.net");
        mockAccount2.setAccountId(2L);
        mockAccountList.add(mockAccount2);

        when(accountServices.getAllAccounts()).thenReturn(mockAccountList);

        mockMvc.perform(get("/accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].accountId", is(1)))
                .andExpect(jsonPath("$[0].firstName", is("John")))
                .andExpect(jsonPath("$[0].lastName", is("Smith")))
                .andExpect(jsonPath("$[0].emailAddress", is("jsmith@aol.net")))
                .andExpect(jsonPath("$[1].accountId", is(2)))
                .andExpect(jsonPath("$[1].firstName", is("Jane")))
                .andExpect(jsonPath("$[1].lastName", is("Doe")))
                .andExpect(jsonPath("$[1].emailAddress", is("jdoe@aol.net")));
    }

    @Test
    public void testGetOneAccount() throws Exception{
        Account mockAccount1 = new Account("John", "Smith", "jsmith@aol.org");
        mockAccount1.setAccountId(1L);

        when(accountServices.getOneAccount(anyLong())).thenReturn(mockAccount1);

        mockMvc.perform(get("/accounts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.accountId", is(1)))
                .andExpect(jsonPath("$.firstName", is("John")))
                .andExpect(jsonPath("$.lastName", is("Smith")))
                .andExpect(jsonPath("$.emailAddress", is("jsmith@aol.org")));
    }

}
