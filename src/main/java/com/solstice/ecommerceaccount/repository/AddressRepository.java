package com.solstice.ecommerceaccount.repository;

import com.solstice.ecommerceaccount.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findAllByAccountId(long accountId);

    Address findOneByAccountIdAndAddressId(long accountId, long addressId);
}
