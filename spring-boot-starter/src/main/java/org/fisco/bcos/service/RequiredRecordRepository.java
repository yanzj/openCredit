package org.fisco.bcos.service;

import org.fisco.bcos.domain.RequiredRecord;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;

public interface RequiredRecordRepository extends CrudRepository<RequiredRecord, BigInteger> {

    Iterable<RequiredRecord> findByCheckResult(Boolean checkResult);
}
