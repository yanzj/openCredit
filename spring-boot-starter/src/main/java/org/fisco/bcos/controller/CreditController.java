package org.fisco.bcos.controller;

import org.fisco.bcos.bean.CreditData;
import org.fisco.bcos.solidity.Credit;
import org.fisco.bcos.utils.SolidityTools;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple5;
import org.fisco.bcos.web3j.utils.Numeric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/credit")
public class CreditController {

    @Autowired
    Web3j web3j;

    @Autowired
    Credit credit;

    /**
     * Add a credit to the block chain
     * @param id people id
     * @param data the credit data of the people
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/add")
    public @ResponseBody BigInteger addCredit(@RequestParam("id") String id,
                                         @RequestParam("data") String data) throws Exception {
//        Uint256 result = new Uint256();
        TransactionReceipt result = credit.addCreditData(id, data).send();
        List<Credit.AddCreditDataSuccessEventResponse> reponses = credit.getAddCreditDataSuccessEvents(result);
        return reponses.get(0).id;
    }


    /**
     * Get the credits data on people id
     * @param id people id
     * @return List of credit data
     * @throws Exception
     */
    @PostMapping(value = "/get")
    public @ResponseBody List<CreditData> getCredits(@RequestParam("id") String id
                                         ) throws Exception {
//        Uint256 result = new Uint256();
        Tuple5<List<BigInteger>, List<String>, List<byte[]>, List<BigInteger>, List<BigInteger>> result = credit.getCreditDetialDataByPeopleId(id).send();
        ArrayList<CreditData> credits = new ArrayList<>();
        for (int i = 0; i < result.getValue1().size(); i++) {
            CreditData creditData = new CreditData(result.getValue1().get(i),
                    result.getValue2().get(i),
                    Numeric.toHexString(result.getValue3().get(i)),
                    result.getValue5().get(i),
            String.valueOf(result.getValue4().get(0)));
            credits.add(creditData);
        }
        return credits;
    }
}
