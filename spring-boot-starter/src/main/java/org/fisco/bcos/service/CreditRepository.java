package org.fisco.bcos.service;

import org.fisco.bcos.domain.OriginCredit;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;

public interface CreditRepository extends CrudRepository<OriginCredit, BigInteger> {

}
