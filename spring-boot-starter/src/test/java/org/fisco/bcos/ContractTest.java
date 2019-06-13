package org.fisco.bcos;


import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import lombok.extern.java.Log;
import org.fisco.bcos.bean.CreditData;
import org.fisco.bcos.temp.Credit;
import org.fisco.bcos.temp.HelloWorld;
import org.fisco.bcos.utils.SolidityTools;
import org.fisco.bcos.web3j.abi.EventEncoder;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Address;
import org.fisco.bcos.web3j.abi.datatypes.Event;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.Hash;
import org.fisco.bcos.web3j.crypto.SHA3Digest;
import org.fisco.bcos.web3j.crypto.gm.GenCredential;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.DefaultBlockParameterName;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple5;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.fisco.bcos.web3j.utils.Numeric;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rx.functions.Action1;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ContractTest {

    private static BigInteger gasPrice = new BigInteger("300000000");
    private static BigInteger gasLimit = new BigInteger("300000000");
    private Credentials credentials;
    private Credentials credentials2;

    @Autowired
    Web3j web3j;

    @Before
    public void setUp() throws Exception {
        credentials = GenCredential.create("b83261efa42895c38c6c2364ca878f43e77f3cddbc922bf57d0d48070f79feb6");
        credentials2 = GenCredential.create("b83261efa42895c38c6c2364ca878f43e77f3cddbc922bf57d0d48070f79feb7");
        if (credentials == null || credentials2 == null) {
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
        HelloWorld helloWorld = HelloWorld.deploy(web3j, credentials, new StaticGasProvider(gasPrice, gasLimit)).sendAsync().get(60000, TimeUnit.MILLISECONDS);
        if (helloWorld != null) {
            System.out.println("HelloWorld address is: " + helloWorld.getContractAddress());
            //call set function
            helloWorld.set("Hello, World!").sendAsync().get(60000, TimeUnit.MILLISECONDS);
            //call get function
            String result = helloWorld.get().sendAsync().get(60000, TimeUnit.MILLISECONDS);
            System.out.println(result);
            assertTrue( "Hello, World!".equals(result));
        }
    }

    /**
     * Test the credit contract
     * The way to decode output is hard to find
     * @throws Exception
     */
    @Test
    public void deployAndCallCreditAdd() throws Exception {
        //deploy contract
        System.out.println("Start Credit Add");
        Credit credit = Credit.deploy(web3j, credentials, new StaticGasProvider(gasPrice, gasLimit)).send();

        if (credit != null) {
            System.out.println("Credit address is: " + credit.getContractAddress());
            String id = String.valueOf((int)Math.random());
            String data = SolidityTools.randomAlphaNumeric((int)Math.random());
            //call set function
            TransactionReceipt receipt = credit.addCreditData( id, data).sendAsync().get();
            List<Credit.AddCreditDataSuccessEventResponse> reponses = credit.getAddCreditDataSuccessEvents(receipt);
            System.out.println("reponse");
            for (int i = 0; i < reponses.size(); i++) {
                System.out.println(reponses.get(i).id);
            }
            //call get function
            Tuple5<List<BigInteger>, List<String>, List<byte[]>, List<BigInteger>, List<BigInteger>> result = credit.getCreditDetialDataByPeopleId(id).send();
            System.out.println("Uploader: " + result.getValue1().toString());
            List<BigInteger> ids = result.getValue1();
            List<String> uploaders = result.getValue2();
            List<byte[]> datas = result.getValue3();
            List<BigInteger> types = result.getValue4();
            List<BigInteger> times = result.getValue5();
            System.out.println("ids.get(0) = " + ids.get(0));
            System.out.println("uploaders.get(0) = " + uploaders.get(0));
            System.out.println("datas.get(0) = " + Numeric.toHexString(datas.get(0)));
            System.out.println("times.get(0) = " + times.get(0));
            CreditData creditData = new CreditData(result.getValue1().get(0),
                    result.getValue2().get(0),
                    Numeric.toHexString(datas.get(0)),
                    types.get(0),
                    String.valueOf(result.getValue5().get(0)));

            String printed = Numeric.toHexString(datas.get(0));
            // [-84, -81, 50, -119, -41, -74, 1, -53, -47, 20, -5, 54, -60, -46, -100, -123, -69, -3, 94, 19, 63, 20, -53, 53, 92, 63, -40, -39, -109, 103, -106, 79]

//            Hash.sha3("EVWithdraw(address,uint256,bytes32)".getBytes(StandardCharsets.UTF_8));
            byte[] here2 = (new SHA3Digest()).hash(data).getBytes();
            String here2_raw = (new SHA3Digest()).hash(data);
            String here_raw = Hash.sha3String(data);
            byte[] here = Hash.sha3String(data).getBytes();
            String herePrinted = Numeric.toHexString(here);
            String herePrinted2 = Numeric.toHexString(here2);
            // [48, 120, 97, 99, 97, 102, 51, 50, 56, 57, 100, 55, 98, 54, 48, 49, 99, 98, 100, 49, 49, 52, 102, 98, 51, 54, 99, 52, 100, 50, 57, 99, 56, 53, 98, 98, 102, 100, 53, 101, 49, 51, 51, 102, 49, 52, 99, 98, 51, 53, 53, 99, 51, 102, 100, 56, 100, 57, 57, 51, 54, 55, 57, 54, 52, 102]

            System.out.println("Datas: " + printed);
            System.out.println("Datas here: " + herePrinted);
            System.out.println("Datas here raw: " + here_raw);
            System.out.println("Datas here2: " + herePrinted2);
            System.out.println("Datas here2 raw: " + here2_raw);
            System.out.println("Time: " + creditData.getTime());
            System.out.println(creditData.toString());
            System.out.println("getBlockNumber:" + web3j.getBlockNumber().send().getBlockNumber());

            assertTrue(printed.equals(here_raw));
        }
    }

}
