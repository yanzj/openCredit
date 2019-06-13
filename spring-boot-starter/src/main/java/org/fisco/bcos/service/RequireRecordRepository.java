package org.fisco.bcos.service;

import org.fisco.bcos.domain.RequiredRecord;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;


/**
 * 保存本地向外请求的数据
 */
public interface RequireRecordRepository extends CrudRepository<RequiredRecord, BigInteger> {
    Iterable<RequiredRecord> findByIsSent(Boolean isSent);
    RequiredRecord findByCreditDataId(BigInteger creditDataId);
}
