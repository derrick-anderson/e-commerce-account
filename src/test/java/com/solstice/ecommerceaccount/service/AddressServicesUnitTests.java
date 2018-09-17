package com.solstice.ecommerceaccount.service;

import com.solstice.ecommerceaccount.domain.Address;
import com.solstice.ecommerceaccount.repository.AddressRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AddressServices.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AddressServicesUnitTests {

    @MockBean
    private AddressRepository addressRepository;

    @Autowired
    private AddressServices addressServices;

    //todo: move these to a helper method
    private Address addressToSave = new Address("123 Freedom Street", "", "Dallas", "Texas", "12345", "USA");
    private Address mockAddress1 = new Address("123 Freedom Street", "", "Dallas", "Texas", "12345", "USA");
    private Address mockAddress2 = new Address("256 Liberty Drive", "2b", "Cameron", "Missouri", "67890", "USA");
    private List<Address> mockAddressList = new ArrayList<Address>(){{
        mockAddress1.setAccountId(1L);
        mockAddress1.setAddressId(1L);
        mockAddress2.setAccountId(1L);
        mockAddress2.setAddressId(2L);
        add(mockAddress1);
        add(mockAddress2);
    }};



    @Test
    public void getAllAddresses_HappyPath(){

        when(addressRepository.findAllByAccountId(anyLong())).thenReturn(mockAddressList);

        List<Address> addressesFound= addressServices.getAllAddressesForAccount(1L);

        assertThat(addressesFound.size(), is(2));

    }

    @Test
    public void getOneAddress_HappyPath(){

        when(addressRepository.findOneByAccountIdAndAddressId(anyLong(), anyLong())).thenReturn(mockAddress1);

        Address foundAddress = addressServices.getOneAddress(1L, 1L);

        assertThat(foundAddress.getAccountId(), is(1L));
        assertThat(foundAddress.getAddressId(), is(1L));
        assertThat(foundAddress.getStreet(), is("123 Freedom Street"));
    }

    @Test
    public void saveAddress_HappyPath(){

        when(addressRepository.save(any(Address.class))).thenReturn(mockAddress2);

        Address savedAddress = addressServices.saveAddress(1L, addressToSave);

        assertThat(savedAddress.getAddressId(), is(2L));
        assertThat(savedAddress.getStreet(), is("256 Liberty Drive"));
    }

    @Test
    public void deleteAddress_HappyPath(){

        when(addressRepository.findOneByAccountIdAndAddressId(12345L, 15L)).thenReturn(mockAddress2);

        addressServices.deleteAddress( 12345L, 15L);

        verify(addressRepository, times(1)).deleteById(15L);

    }

    @Test
    public void updateAddress_HappyPath(){

        when(addressRepository.findOneByAccountIdAndAddressId(anyLong(), anyLong())).thenReturn(mockAddress1);
        when(addressRepository.save(any())).thenReturn(mockAddress1);

        Address savedAddress = addressServices.updateAddress(1L, 1L, mockAddress1);

        assertThat(savedAddress.getAddressId(), is(1L));
        assertThat(savedAddress.getAccountId(), is(1L));
        assertThat(savedAddress.getStreet(), is("123 Freedom Street"));

        verify(addressRepository, times(1)).findOneByAccountIdAndAddressId(anyLong(), anyLong());
        verify(addressRepository, times(1)).save(any());

    }
}
