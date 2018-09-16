package com.solstice.ecommerceaccount.controller;

import com.solstice.ecommerceaccount.domain.Account;
import com.solstice.ecommerceaccount.domain.Address;
import com.solstice.ecommerceaccount.service.AccountServices;
import com.solstice.ecommerceaccount.service.AddressServices;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
public class AccountController {

    private AccountServices accountServices;
    private AddressServices addressServices;

    private AccountController(AccountServices accountServices, AddressServices addressServices) {

        this.accountServices = accountServices;
        this.addressServices = addressServices;

    }

    @GetMapping("/accounts")
    public List<Account> getAllAccounts(){

        return accountServices.getAllAccounts();

    }

    //todo: Add Error Throwing for trying to create an ID that exists
    @PostMapping("/accounts")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createAccount(@RequestBody Account accountIn){

        return accountServices.saveAccount(accountIn);

    }

    //todo: Add Error throw to account that doesn't exist
    @GetMapping("/accounts/{accountId}")
    public Account getOneAccount(@PathVariable("accountId") Long accountId){

        return accountServices.getOneAccount(accountId);
    }

    //Address Methods
    //todo: Split these into their own controller

    @GetMapping("accounts/{accountId}/addresses")
    public List<Address> getAllAddresses(@PathVariable("accountId") Long accountId){

        return addressServices.getAllAddressesForAccount(accountId);

    }

    //todo: Add Error Throwing for trying to create an ID that exists
    @PostMapping("/accounts/{accountId}/addresses")
    @ResponseStatus(HttpStatus.CREATED)
    public Address createAddress(@PathVariable("accountId") Long accountId, @RequestBody Address addressIn){

        return addressServices.saveAddress(accountId, addressIn);

    }

    //todo: Add error throwing for Address or account that doesn't exist
    @GetMapping("/accounts/{accountId}/addresses/{addressId}")
    public Address getOneAddress(@PathVariable("accountId") Long accountId, @PathVariable("addressId") Long addressId){

        return addressServices.getOneAddress(accountId, addressId);
    }

    @DeleteMapping("/accounts/{accountId}/addresses/{addressId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAddress(@PathVariable("accountId") Long accountId, @PathVariable("addressId") Long addressId){

        addressServices.deleteAddress(accountId, addressId);

    }

    @PutMapping("/accounts/{accountId}/addresses/{addressId}")
    public Address updateAddress(@PathVariable("accountId") Long accountId, @PathVariable("addressId") Long addressId, @RequestBody Address addressIn){

        return addressServices.updateAddress(accountId, addressId, addressIn);

    }


}
