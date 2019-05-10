package org.fisco.bcos;


import static org.junit.Assert.assertTrue;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import org.fisco.bcos.temp.Credit;
import org.fisco.bcos.temp.HelloWorld;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.Hash;
import org.fisco.bcos.web3j.crypto.SHA3Digest;
import org.fisco.bcos.web3j.crypto.gm.GenCredential;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.tuples.generated.Tuple3;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ContractTest {

    private static BigInteger gasPrice = new BigInteger("300000000");
    private static BigInteger gasLimit = new BigInteger("300000000");
    private Credentials credentials;

    @Autowired
    Web3j web3j;

    @Before
    public void setUp() throws Exception {
        credentials = GenCredential.create("b83261efa42895c38c6c2364ca878f43e77f3cddbc922bf57d0d48070f79feb6");
        if (credentials == null) {
            throw new Exception("create Credentials failed");
        }
    }

    @After
    public void tearDown() {

    }

    @Test
    public void deployAndCallHelloWorld() throws Exception {
        //deploy contract
        System.out.println("Start hello world");
        HelloWorld helloWorld = HelloWorld.deploy(web3j, credentials, new StaticGasProvider(gasPrice, gasLimit)).send();
        if (helloWorld != null) {
            System.out.println("HelloWorld address is: " + helloWorld.getContractAddress());
            //call set function
            helloWorld.set("Hello, World!").send();
            //call get function
            String result = helloWorld.get().send();
            System.out.println(result);
            assertTrue( "Hello, World!".equals(result));
        }
    }

//    @Test
//    public void deployAndCallCreditAdd() throws Exception {
//        //deploy contract
//        System.out.println("Start Credit Add");
//        Credit credit = Credit.deploy(web3j, credentials, new StaticGasProvider(gasPrice, gasLimit)).send();
//        if (credit != null) {
//            System.out.println("Credit address is: " + credit.getContractAddress());
//            //call set function
//            credit.addCreditData("1234567","Hello, World!").send();
//            //call get function
//            Tuple3<List<String>, List<byte[]>, List<BigInteger>> result = credit.getCreditDataById("1234567").send();
//            System.out.println("Uploader: " + result.getValue1().toString());
//
//            byte[] there = result.getValue2().get(0);
//            String printed = Arrays.toString(there);
//            // [-84, -81, 50, -119, -41, -74, 1, -53, -47, 20, -5, 54, -60, -46, -100, -123, -69, -3, 94, 19, 63, 20, -53, 53, 92, 63, -40, -39, -109, 103, -106, 79]
//            byte[] here2 = (new SHA3Digest()).hash("Hello, World!").getBytes();
//            byte[] here = Hash.sha3String("Hello, World!").getBytes();
//            String herePrinted = Arrays.toString(here);
//            String herePrinted2 = Arrays.toString(here2);
//            // [48, 120, 97, 99, 97, 102, 51, 50, 56, 57, 100, 55, 98, 54, 48, 49, 99, 98, 100, 49, 49, 52, 102, 98, 51, 54, 99, 52, 100, 50, 57, 99, 56, 53, 98, 98, 102, 100, 53, 101, 49, 51, 51, 102, 49, 52, 99, 98, 51, 53, 53, 99, 51, 102, 100, 56, 100, 57, 57, 51, 54, 55, 57, 54, 52, 102]
//
//            System.out.println("Datas: " + printed);
//            System.out.println("Datas here: " + herePrinted);
//            System.out.println("Datas here2: " + herePrinted2);
//            System.out.println("Time: " + result.getValue3().toString());
//
//            assertTrue(Arrays.equals(there, here));
//        }
//    }
}
