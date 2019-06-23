package org.fisco.bcos.service;

import org.fisco.bcos.domain.SendRecord;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;

public interface SendRecordRepository extends CrudRepository<SendRecord, BigInteger> {

    Iterable<SendRecord> findByCheckResult(Boolean checkResult);
}
