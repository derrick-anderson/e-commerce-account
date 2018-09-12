package com.solstice.ecommerceaccount.controller;

import com.solstice.ecommerceaccount.domain.Address;
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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(AccountController.class)
public class AddressControllerUnitTests {

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
    public void testSaveAddress() throws Exception{

        Address mockAddress = new Address("123 Freedom Street", "", "Dallas", "Texas", "12345", "USA" );
        mockAddress.setAccountId(1L);
        mockAddress.setAddressId(1L);

        when(addressServices.saveAddress(anyLong(), ArgumentMatchers.any())).thenReturn(mockAddress);

        String addressJson = "{\"street\" : \"123 Freedom Street\",\"unit\" : \"\",\"city\" : \"Dallas\",\"state\" : \"Texas\",\"postal\" : \"12345\",\"country\" : \"USA\"}";

        mockMvc.perform(
                post("/accounts/1/addresses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(addressJson)
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.accountId", is(1)))
                .andExpect(jsonPath("$.addressId", is(1)))
                .andExpect(jsonPath("$.street", is("123 Freedom Street")))
                .andExpect(jsonPath("$.unit", is("")))
                .andExpect(jsonPath("$.city", is("Dallas")))
                .andExpect(jsonPath("$.state", is("Texas")))
                .andExpect(jsonPath("$.postal", is("12345")))
                .andExpect(jsonPath("$.country", is("USA")));
    }

    @Test
    public void testGetAllAddressesForAccount() throws Exception {

        List<Address> mockAddressList = new ArrayList<>();

        Address mockAddress1 = new Address("123 Freedom Street", "", "Dallas", "Texas", "12345", "USA" );
        mockAddress1.setAccountId(1L);
        mockAddress1.setAddressId(1L);
        mockAddressList.add(mockAddress1);

        Address mockAddress2 = new Address("456 State Avenue", "2b", "Cameron", "Missouri", "67890", "USA");
        mockAddress2.setAccountId(1L);
        mockAddress2.setAddressId(2L);
        mockAddressList.add(mockAddress2);

        when(addressServices.getAllAddressesForAccount(anyLong())).thenReturn(mockAddressList);

        mockMvc.perform(get("/accounts/1/addresses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].accountId", is(1)))
                .andExpect(jsonPath("$[0].addressId", is(1)))
                .andExpect(jsonPath("$[0].street", is("123 Freedom Street")))
                .andExpect(jsonPath("$[0].unit", is("")))
                .andExpect(jsonPath("$[0].city", is("Dallas")))
                .andExpect(jsonPath("$[0].state", is("Texas")))
                .andExpect(jsonPath("$[0].postal", is("12345")))
                .andExpect(jsonPath("$[0].country", is("USA")))
                .andExpect(jsonPath("$[1].accountId", is(1)))
                .andExpect(jsonPath("$[1].addressId", is(2)))
                .andExpect(jsonPath("$[1].street", is("456 State Avenue")))
                .andExpect(jsonPath("$[1].unit", is("2b")))
                .andExpect(jsonPath("$[1].city", is("Cameron")))
                .andExpect(jsonPath("$[1].state", is("Missouri")))
                .andExpect(jsonPath("$[1].postal", is("67890")))
                .andExpect(jsonPath("$[1].country", is("USA")));

    }

    @Test
    public void testGetOneAddress() throws Exception{
        Address mockAddress = new Address("123 Freedom Street", "", "Dallas", "Texas", "12345", "USA" );
        mockAddress.setAccountId(1L);
        mockAddress.setAddressId(1L);

        when(addressServices.getOneAddress(anyLong(), anyLong())).thenReturn(mockAddress);

        mockMvc.perform(get("/accounts/1/addresses/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.accountId", is(1)))
                .andExpect(jsonPath("$.addressId", is(1)))
                .andExpect(jsonPath("$.street", is("123 Freedom Street")))
                .andExpect(jsonPath("$.unit", is("")))
                .andExpect(jsonPath("$.city", is("Dallas")))
                .andExpect(jsonPath("$.state", is("Texas")))
                .andExpect(jsonPath("$.postal", is("12345")))
                .andExpect(jsonPath("$.country", is("USA")));
    }


}
