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

    //todo: add the correct merging logic
    public Address updateAddress(Long accountId, Long addressId, Address addressUpdateInfo) {
        Address savedAddress = getOneAddress(accountId, addressId);
        if(savedAddress != null){
            if(addressUpdateInfo.getStreet() != null){
                savedAddress.setStreet(addressUpdateInfo.getStreet());
            }
            if(addressUpdateInfo.getUnit() != null){
                savedAddress.setUnit(addressUpdateInfo.getUnit());
            }
            if(addressUpdateInfo.getCity() != null){
                savedAddress.setCity(addressUpdateInfo.getCity());
            }
            if(addressUpdateInfo.getState() != null){
                savedAddress.setState(addressUpdateInfo.getState());
            }
            if(addressUpdateInfo.getPostal() != null){
                savedAddress.setPostal(addressUpdateInfo.getPostal());
            }
            if(addressUpdateInfo.getCountry() != null){
                savedAddress.setCountry(addressUpdateInfo.getCountry());
            }

            return addressRepository.save(savedAddress);
        }
        else{
            throw new EntityNotFoundException();
        }
    }
}
