package org.fisco.bcos.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Error;
import org.fisco.bcos.solidity.Credit;
import org.fisco.bcos.solidity.Record;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.precompile.cns.CnsInfo;
import org.fisco.bcos.web3j.precompile.cns.CnsService;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigInteger;
import java.util.List;

@Slf4j
@Configuration
@ConfigurationProperties(prefix = "contract-type")
public class ContractConfig {

    private static BigInteger gasPrice = new BigInteger("300000000");
    private static BigInteger gasLimit = new BigInteger("300000000");

    private final String CREDIT_CONTRACT = "Credit";
    private final String CREDIT_CONTRACT_VERSION = "1";
    private final String RECORD_CONTRACT = "Record";
    private final String RECORD_CONTRACT_VERSION = "1";

    @Autowired
    Credentials credentials;

    @Autowired
    Web3j web3j;

    @Autowired
    CnsService cnsService;

    @Bean
    public Credit getCredit () throws Exception {
        Credit credit = null;
        List<CnsInfo> cnsInfoList = cnsService.queryCnsByName(CREDIT_CONTRACT);
        if (cnsInfoList.isEmpty()) {
            log.info("Contract Credit not found, register");
            // register
            try {
                credit = Credit.deploy(web3j, credentials, new StaticGasProvider(gasPrice, gasLimit)).send();
            } catch (Exception e) {
                System.out.println(e.toString());
                log.error("Error: getCredit: " + e.toString());
                throw e;
            }
            cnsService.registerCns(CREDIT_CONTRACT, CREDIT_CONTRACT_VERSION, credit.getContractAddress(), credit.getContractBinary());
        } else {
            String addr = cnsInfoList.get(0).getAddress();
            credit = Credit.load(addr, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
        }

        log.info("Contract Credit address = {}", credit.getContractAddress());

        return credit;
    }

    @Bean
    public Record getRecord () throws Exception {
        Record record;
        List<CnsInfo> cnsInfoList = cnsService.queryCnsByName(RECORD_CONTRACT);
        if (cnsInfoList.isEmpty()) {
            log.info("Contract Record not found, register");
            // register
            try {
                record = Record.deploy(web3j, credentials, new StaticGasProvider(gasPrice, gasLimit)).send();
            } catch (Exception e) {
                System.out.println(e.toString());
                log.error("Error: getCredit: " + e.toString());
                throw e;
            }
            cnsService.registerCns(RECORD_CONTRACT, RECORD_CONTRACT_VERSION, record.getContractAddress(), record.getContractBinary());
        } else {
            String addr = cnsInfoList.get(0).getAddress();
            record = Record.load(addr, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
        }
        log.info("Contract Record address = {}", record.getContractAddress());

        return record;
    }
}
