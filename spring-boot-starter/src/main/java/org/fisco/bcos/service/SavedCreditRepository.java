package org.fisco.bcos.service;

import org.fisco.bcos.domain.SavedCredit;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;

public interface SavedCreditRepository extends CrudRepository<SavedCredit, BigInteger> {
}
