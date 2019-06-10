package org.fisco.bcos.controller;

import org.fisco.bcos.solidity.HelloWorld;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.gm.GenCredential;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigInteger;

@RestController
@RequestMapping(value = "/hello")
public class HelloController {

    private static BigInteger gasPrice = new BigInteger("300000000");
    private static BigInteger gasLimit = new BigInteger("300000000");
    private Credentials credentials;

    @Autowired
    Web3j web3j;

    @GetMapping(value = "/")
    public String sayHello() throws Exception {
        credentials = GenCredential.create("b83261efa42895c38c6c2364ca878f43e77f3cddbc922bf57d0d48070f79feb6");
        if (credentials == null) {
            throw new Exception("create Credentials failed");
        }

        System.out.println("Start hello world");
        String result = "";
        HelloWorld helloWorld = HelloWorld.deploy(web3j, credentials, new StaticGasProvider(gasPrice, gasLimit)).send();
        if (helloWorld != null) {
            System.out.println("HelloWorld address is: " + helloWorld.getContractAddress());
            //call set function
            helloWorld.set("Hello, World!").send();
            //call get function
            result = helloWorld.get().send();
            System.out.println(result);

        }
        return result;
    }

    @GetMapping(value = "/getBlockNumber")
    public String getBlockNumber() throws IOException {
        BigInteger blockNumber = web3j.getBlockNumber().send().getBlockNumber();
        return  blockNumber.toString();
    }
}
