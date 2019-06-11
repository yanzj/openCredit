package org.fisco.bcos.controller;


import org.fisco.bcos.bean.RecordData;
import org.fisco.bcos.solidity.Record;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple8;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

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
     * @param owner the owner of the credit data
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

    @PostMapping(value = "/check")
    public @ResponseBody
    boolean checkRecord (@RequestParam("applicant") String applicant,
                         @RequestParam("uploader") String uploader,
                         @RequestParam("recordId") BigInteger recordId,
                         @RequestParam("creditDataId") BigInteger creditDataId
    ) throws Exception {
        return record.checkRecordExist(applicant, uploader, recordId, creditDataId).send();
    }

    /**
     * Make decision if send the original data and update the record data on the block chain
     * @param recordId
     * @param isSend
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/ifSend")
    public  @ResponseBody boolean ifSend(
            @RequestParam("recordId") BigInteger recordId,
            @RequestParam("isSend") Boolean isSend
    ) throws Exception {
        return record.sendRecordData(recordId, isSend).send().isStatusOK();
    }

    @PostMapping(value = "/get")
    public @ResponseBody
    RecordData getRecordDataById(@RequestParam("recordId") BigInteger recordId) throws Exception {
        Tuple8<String, String, BigInteger, BigInteger, BigInteger, Boolean, BigInteger, Boolean> tupleResult = record.getRecordDataById(recordId).send();
        return new RecordData(tupleResult);
    }

    @PostMapping(value = "/score")
    public @ResponseBody
    boolean scoreRecordData( @RequestParam("recordId") BigInteger recordId,
                             @RequestParam("score") BigInteger score) throws Exception {
        return record.scoreRecordData(recordId, score).send().isStatusOK();
    }
}
