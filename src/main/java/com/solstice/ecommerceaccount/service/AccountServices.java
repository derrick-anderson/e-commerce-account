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

    public Account updateAccount(Long accountId, Account updateInfo) {
        Account savedAccount = getOneAccount(accountId);
        if(savedAccount != null) {
            if (updateInfo.getFirstName() != null) {
                savedAccount.setFirstName(updateInfo.getFirstName());
            }
            if (updateInfo.getLastName() != null) {
                savedAccount.setLastName(updateInfo.getLastName());
            }
            if (updateInfo.getEmailAddress() != null) {
                savedAccount.setEmailAddress(updateInfo.getEmailAddress());
            }
            accountRepository.save(savedAccount);
            return savedAccount;
        }else throw new EntityNotFoundException();
    }
}
