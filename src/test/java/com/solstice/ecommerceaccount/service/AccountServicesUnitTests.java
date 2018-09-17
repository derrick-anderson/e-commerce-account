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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
    public void getAllAccounts_HappyPath() {

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
    public void getOneAccount_HappyPath(){

        Account mockAccount = new Account("John", "Smith", "jsmith@aol.net");
        mockAccount.setAccountId(1L);

        when(accountRepository.findOneByAccountId(anyLong())).thenReturn(mockAccount);

        Account foundAccount = accountServices.getOneAccount(1L);

        assertThat(foundAccount.getAccountId(), is(1L));
        assertThat(foundAccount.getEmailAddress(), is("jsmith@aol.net"));

    }

    @Test
    public void saveAccount_HappyPath(){
        Account accountToSave = new Account("John", "Smith", "jsmith@aol.net");
        Account mockAccount = new Account("John", "Smith", "jsmith@aol.net");
        mockAccount.setAccountId(5L);

        when(accountRepository.save(ArgumentMatchers.any(Account.class))).thenReturn(mockAccount);

        Account savedAccount = accountServices.saveAccount(accountToSave);

        assertThat(savedAccount.getAccountId(), is(5L));
    }

    @Test
    public void deleteAccount_HappyPath(){
        when(accountRepository.findOneByAccountId(12345L)).thenReturn(new Account());
        accountServices.deleteAccount(12345L);

        verify(accountRepository, times(1)).deleteById(12345L);
    }

    @Test
    public void updateAccount_HappyPath(){

        Account mockAccount = new Account("John", "Jacob", "jhsmith@gmail.com");
        mockAccount.setAccountId(67890L);

        Account updateInfo = new Account( null, "Smith", null);
        when(accountRepository.findOneByAccountId(67890L)).thenReturn(mockAccount);

        Account updatedAccount = accountServices.updateAccount(67890L, updateInfo);

        assertThat(updatedAccount.getAccountId(), is(67890L));
        assertThat(updatedAccount.getFirstName(), is("John"));
        assertThat(updatedAccount.getLastName(), is("Smith"));
        assertThat(updatedAccount.getEmailAddress(), is("jhsmith@gmail.com"));

        verify(accountRepository, times(1)).findOneByAccountId(67890L);
        verify(accountRepository, times(1)).save(ArgumentMatchers.any());

    }
}
