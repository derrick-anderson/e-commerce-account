package com.solstice.ecommerceaccount.repository;

import com.solstice.ecommerceaccount.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {


    Account findOneByAccountId(Long id);
}
