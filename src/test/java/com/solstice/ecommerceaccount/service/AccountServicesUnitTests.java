package com.solstice.ecommerceaccount.service;

import com.solstice.ecommerceaccount.domain.Account;
import com.solstice.ecommerceaccount.repository.AccountRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AccountServicesUnitTests {

    @MockBean
    private AccountRepository accountRepository;

    @Autowired
    private AccountServices accountServices;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllAccounts() {

        List<Account> mockAccountList = new ArrayList<>();

        Account mockAccount1 = new Account("John", "Smith", "jsmith@aol.net");
        mockAccount1.setAccountId(1L);
        mockAccountList.add(mockAccount1);

        Account mockAccount2 = new Account("Jane", "Doe", "jdoe@aol.net");
        mockAccount2.setAccountId(2L);
        mockAccountList.add(mockAccount2);

        when(accountRepository.findAll()).thenReturn(mockAccountList);

        List<Account> accounts = accountServices.getAllAccounts();

        assertThat(accounts.size(), is(2));

    }

    @Test
    public void testGetOneAccountById(){

        Account mockAccount = new Account("John", "Smith", "jsmith@aol.net");
        mockAccount.setAccountId(1L);

        when(accountRepository.findOneByAccountId(anyLong())).thenReturn(mockAccount);

        Account foundAccount = accountServices.getOneAccount(1L);

        assertThat(foundAccount.getAccountId(), is(1L));
        assertThat(foundAccount.getEmailAddress(), is("jsmith@aol.net"));

    }

    @Test
    public void testSaveAccount(){
        Account accountToSave = new Account("John", "Smith", "jsmith@aol.net");
        Account mockAccount = new Account("John", "Smith", "jsmith@aol.net");
        mockAccount.setAccountId(5L);

        when(accountRepository.save(ArgumentMatchers.any(Account.class))).thenReturn(mockAccount);

        Account savedAccount = accountServices.saveAccount(accountToSave);

        assertThat(savedAccount.getAccountId(), is(5L));
    }
}
