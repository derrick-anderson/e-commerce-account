package com.solstice.ecommerceaccount.service;

import com.solstice.ecommerceaccount.domain.Address;
import com.solstice.ecommerceaccount.repository.AddressRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class AddressServices {

    private AddressRepository addressRepository;

    private AddressServices(AddressRepository addressRepository){
        this.addressRepository = addressRepository;
    }

    public List<Address> getAllAddressesForAccount(Long accountId) {
        return addressRepository.findAllByAccountId(accountId);
    }

    public Address saveAddress(Long accountId, Address addressIn) {
        addressIn.setAccountId(accountId);
        return addressRepository.save(addressIn);
    }

    public Address getOneAddress(Long accountId, Long addressId) {
        return addressRepository.findOneByAccountIdAndAddressId(accountId, addressId);
    }

    public void deleteAddress(Long accountId, Long addressId) {
        if(getOneAddress(accountId, addressId) != null){
            addressRepository.deleteById(addressId);
        }
        else{
            throw new EntityNotFoundException();
        }
    }
}
