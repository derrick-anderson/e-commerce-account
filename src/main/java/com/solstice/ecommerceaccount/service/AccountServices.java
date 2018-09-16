package com.solstice.ecommerceaccount.service;

import com.solstice.ecommerceaccount.domain.Account;
import com.solstice.ecommerceaccount.repository.AccountRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class AccountServices {

    private AccountRepository accountRepository;

    private AccountServices(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account getOneAccount(Long accountId) {
        return accountRepository.findOneByAccountId(accountId);
    }

    public Account saveAccount(Account accountIn) {
        return accountRepository.save(accountIn);
    }

    public void deleteAccount(Long accountId) {
        if(getOneAccount(accountId) !=null){
            accountRepository.deleteById(accountId);
        }
        else{
            throw new EntityNotFoundException();
        }
    }
}
