package org.fisco.bcos.autoconfigure;

import org.fisco.bcos.solidity.Credit;
import org.fisco.bcos.solidity.Record;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigInteger;

@Configuration
@ConfigurationProperties(prefix = "contract-type")
public class ContractConfig {

    private static BigInteger gasPrice = new BigInteger("300000000");
    private static BigInteger gasLimit = new BigInteger("300000000");

    @Autowired
    Credentials credentials;

    @Autowired
    Web3j web3j;

    @Bean
    public Credit getCredit () throws Exception {
        return Credit.deploy(web3j, credentials, new StaticGasProvider(gasPrice, gasLimit)).send();
    }

    @Bean
    public Record getRecord () throws Exception {
        return Record.deploy(web3j, credentials, new StaticGasProvider(gasPrice, gasLimit)).send();
    }
}
