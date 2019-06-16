package org.fisco.bcos.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.fisco.bcos.bean.CreditData;
import org.fisco.bcos.domain.OriginCredit;
import org.fisco.bcos.domain.RequiredRecord;
import org.fisco.bcos.domain.SavedCredit;
import org.fisco.bcos.service.CreditRepository;
import org.fisco.bcos.service.RequireRecordRepository;
import org.fisco.bcos.service.SavedCreditRepository;
import org.fisco.bcos.solidity.Credit;
import org.fisco.bcos.utils.SolidityTools;
import org.fisco.bcos.web3j.crypto.Hash;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple5;
import org.fisco.bcos.web3j.utils.Numeric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping(value = "/credit")
public class CreditController {

    @Autowired
    Web3j web3j;

    @Autowired
    Credit credit;

    @Autowired
    CreditRepository creditRepository;

    @Autowired
    SavedCreditRepository savedCreditRepository;

    @Autowired
    RequireRecordRepository requireRecordRepository;

    /**
     * Add a credit to the block chain
     * @param id people id
     * @param data the credit data of the people
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/add")
    public @ResponseBody String addCredit(@RequestParam("id") String id,
                                          @RequestParam("data") String data,
                                          @RequestParam("type") BigInteger type) throws Exception {
        log.info("/credit/add");
        TransactionReceipt result = credit.addCreditData(id, data).sendAsync().get();
        List<Credit.AddCreditDataSuccessEventResponse> reponses = credit.getAddCreditDataSuccessEvents(result);

        // Construct return
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", reponses.get(0).id);

        OriginCredit originCredit = new OriginCredit();
        originCredit.setCreditId(reponses.get(0).id);
        originCredit.setDataOrigin(data);
        originCredit.setDataHash(Hash.sha3String(data));
        originCredit.setType(type);

        creditRepository.save(originCredit);

        log.info("return: "  + jsonObject.toJSONString());
        return jsonObject.toJSONString();
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
        Tuple5<List<BigInteger>, List<String>, List<byte[]>, List<BigInteger>, List<BigInteger>> result = credit.getCreditDetialDataByPeopleId(id).sendAsync().get();
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

    @PostMapping(value = "/send")
    public String send(@RequestParam("id") BigInteger id,
                       @RequestParam("dataOrigin") String dataOrigin,
                       @RequestParam("dataHash") String dataHash,
                       @RequestParam("type") BigInteger type) {

        JSONObject jsonObject = new JSONObject();
        boolean isSuccess = false;
        try {
            SavedCredit sv = new SavedCredit();
            sv.setCreditId(id);
            sv.setDataOrigin(dataOrigin);
            sv.setDataHash(dataHash);
            sv.setType(type);
            savedCreditRepository.save(sv);
            RequiredRecord rr = requireRecordRepository.findByCreditDataId(sv.getCreditId());
            rr.setSent(true);
            rr.setDataOrigin(sv.getDataOrigin());
            rr.setDataHash(sv.getDataHash());
            rr.setType(type);

            isSuccess = true;
        } catch (Exception e) {
            jsonObject.put("error", e.toString());
        }
        jsonObject.put("isSuccess", isSuccess);

        return  jsonObject.toJSONString();
    }

    /**
     * 可评分列表
     * @return
     */
    @PostMapping(value = "/scoreList")
    public Iterable<RequiredRecord> scoreList() {

        JSONObject jsonObject = new JSONObject();
        boolean isSuccess = false;
        Iterable<RequiredRecord> list = requireRecordRepository.findByIsSent(true);
//        try {
//
//            list =
//
//
//
//            isSuccess = true;
//        } catch (Exception e) {
//            jsonObject.put("error", e.toString());
//        }
//        jsonObject.put("isSuccess", isSuccess);

        return  list;
    }
}
