package org.fisco.bcos.controller;


import com.alibaba.fastjson.JSONObject;
import org.fisco.bcos.bean.RecordData;
import org.fisco.bcos.solidity.Record;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple8;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping(value = "/record")
public class RecordController {

    @Autowired
    Web3j web3j;

    @Autowired
    Record record;

    /**
     * Add record data in block chain
     * @param creditDataId the credit data id
     * @param uploader the owner of the credit data
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/add")
    public @ResponseBody
    RecordData addRecord(@RequestParam("creditDataId") BigInteger creditDataId,
                     @RequestParam("uploader") String uploader) throws Exception {
        TransactionReceipt result = record.addRecordData(creditDataId, uploader).sendAsync().get();
        if (!result.isStatusOK()) {
            System.out.println(result.getLogs().toString());
            throw new Exception("Status not OK: " + result.getLogs().toString());
        }
        List<Record.AddRecordSuccessEventResponse> reponses = record.getAddRecordSuccessEvents(result);
//        while (reponses.size() == 0)
//            reponses = record.getAddRecordSuccessEvents(result);
        if (reponses.isEmpty())
            return new RecordData();
        return new RecordData(reponses.get(0));
    }

    /**
     * Checks if the record is in the block chain
     * @param applicant
     * @param uploader
     * @param recordId
     * @param creditDataId
     * @return A json string
     * @throws Exception
     */
    @PostMapping(value = "/check")
    public @ResponseBody
    String checkRecord (@RequestParam("applicant") String applicant,
                         @RequestParam("uploader") String uploader,
                         @RequestParam("recordId") BigInteger recordId,
                         @RequestParam("creditDataId") BigInteger creditDataId
    ) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("isSuccess", record.checkRecordExist(applicant, uploader, recordId, creditDataId).sendAsync().get());
        return jsonObject.toJSONString();
    }

    /**
     * Make decision if send the original data and update the record data on the block chain
     * @param recordId
     * @param isSend
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/ifSend")
    public  @ResponseBody String ifSend(
            @RequestParam("recordId") BigInteger recordId,
            @RequestParam("isSend") Boolean isSend
    ) throws Exception {
        TransactionReceipt receipt = record.sendRecordData(recordId, isSend).sendAsync().get();
        if (!receipt.isStatusOK()) {
            System.out.println(receipt.getLogs().toString());
            throw new Exception("/ifSend Status not OK: " + receipt.getLogs().toString());
        }
        List<Record.SendRecordDataSuccessEventResponse> responses = record.getSendRecordDataSuccessEvents(receipt);

        JSONObject jsonObject = new JSONObject();
        if (responses.isEmpty()) {
            throw new Exception("response empty!");
        } else {
            jsonObject.put("isSuccess", responses.get(0).yn);
        }
        return jsonObject.toJSONString();

    }

    @PostMapping(value = "/get")
    public @ResponseBody
    RecordData getRecordDataById(@RequestParam("recordId") BigInteger recordId) throws Exception {
        Tuple8<String, String, BigInteger, BigInteger, BigInteger, Boolean, BigInteger, Boolean> tupleResult = record.getRecordDataById(recordId).sendAsync().get();
        RecordData recordData = new RecordData(tupleResult);
        if (recordData.getId().intValue() == 0)
            throw new Exception("NULL");
        return new RecordData(tupleResult);
    }

    @PostMapping(value = "/score")
    public @ResponseBody
    String scoreRecordData( @RequestParam("recordId") BigInteger recordId,
                             @RequestParam("score") BigInteger score) throws Exception {
        TransactionReceipt receipt = record.scoreRecordData(recordId, score).sendAsync().get();
        if (!receipt.isStatusOK()) {
            System.out.println(receipt.getLogs().toString());
            throw new Exception("/score Status not OK: " + receipt.getLogs().toString());
        }
        List<Record.SendRecordDataSuccessEventResponse> responses = record.getSendRecordDataSuccessEvents(receipt);
        JSONObject jsonObject = new JSONObject();
        if (responses.isEmpty()) {
            throw new Exception("response empty!");
        } else {
            jsonObject.put("isSuccess", responses.get(0).yn);
        }
        return jsonObject.toJSONString();
    }
}
