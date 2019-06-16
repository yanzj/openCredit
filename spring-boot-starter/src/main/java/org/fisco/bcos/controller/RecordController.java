package org.fisco.bcos.controller;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.fisco.bcos.bean.RecordData;
import org.fisco.bcos.domain.OriginCredit;
import org.fisco.bcos.domain.RequiredRecord;
import org.fisco.bcos.service.CreditRepository;
import org.fisco.bcos.service.RequireRecordRepository;
import org.fisco.bcos.service.RequiredRecordRepository;
import org.fisco.bcos.solidity.Record;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple8;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping(value = "/record")
public class RecordController {

    @Autowired
    Web3j web3j;

    @Autowired
    Record record;

    @Autowired
    CreditRepository creditRepository;

    @Autowired
    RequiredRecordRepository requiredRecordRepository; // 对方

    @Autowired
    RequireRecordRepository requireRecordRepository; // 本地

    /**
     * Add record data in block chain and require the original data
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

        if (reponses.isEmpty())
            throw new Exception("No success! Response is empty!");

        RecordData recordData = new RecordData(reponses.get(0));

        RequiredRecord requiredRecord = new RequiredRecord();
        requiredRecord.setRecordId(recordData.getId());
        requiredRecord.setApplicant(recordData.getApplicant());
        requiredRecord.setUploader(uploader);
        requiredRecord.setCreditDataId(creditDataId);
        requiredRecord.setTime(recordData.getTime());

        requireRecordRepository.save(requiredRecord);

        // Todo: Get the URL
        String resultStr = requireOriginData(recordData, "http://localhost:8081/record/requireOrigin");

        System.out.println("/add" + resultStr);
        log.info("/add: " + resultStr);

        return recordData;
    }

    private String requireOriginData(RecordData recordData, String url) throws Exception{
        StringBuilder builder = new StringBuilder();
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {

            HttpPost request = new HttpPost(url);
            request.setHeader("User-Agent", "Java client");

            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("applicant", recordData.getApplicant()));
            params.add(new BasicNameValuePair("uploader", recordData.getUploader()));
            params.add(new BasicNameValuePair("recordId", recordData.getId().toString(10)));
            params.add(new BasicNameValuePair("creditDataId", recordData.getCreditDataId().toString(10)));
            params.add(new BasicNameValuePair("time", recordData.getTime().toString(10)));
            request.setEntity(new UrlEncodedFormEntity(params));

            HttpResponse response = client.execute(request);

            BufferedReader bufReader = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent()));

            String line;

            while ((line = bufReader.readLine()) != null) {
                builder.append(line);
                builder.append(System.lineSeparator());
            }

            System.out.println(builder);
        }
        return builder.toString();
    }

    /**
     * Stores the required record data
     * @param applicant
     * @param uploader
     * @param recordId
     * @param creditDataId
     * @param time
     * @return
     */
    @PostMapping(value = "/requireOrigin")
    public String requireRecordData(@RequestParam("applicant") String applicant,
                                    @RequestParam("uploader") String uploader,
                                    @RequestParam("recordId") BigInteger recordId,
                                    @RequestParam("creditDataId") BigInteger creditDataId,
                                    @RequestParam("time") BigInteger time) {
        JSONObject jsonObject = new JSONObject();

        boolean isSuccess = false;

        try {
            RequiredRecord requiredRecord = new RequiredRecord();
            requiredRecord.setRecordId(recordId);
            requiredRecord.setApplicant(applicant);
            requiredRecord.setUploader(uploader);
            requiredRecord.setCreditDataId(creditDataId);
            requiredRecord.setTime(time);
            requiredRecord.setCheckResult(record.checkRecordExist(applicant, uploader, recordId, creditDataId).sendAsync().get());

            OriginCredit credit = creditRepository.findById(creditDataId).get();
            requiredRecord.setDataOrigin(credit.getDataOrigin());
            requiredRecord.setDataHash(credit.getDataHash());
            requiredRecord.setType(credit.getType());

            requiredRecordRepository.save(requiredRecord);

            isSuccess = true;
        } catch (Exception e) {
            jsonObject.put("error", e.toString());
            log.error("");
        } finally {
            jsonObject.put("isSuccess", isSuccess);
        }
        return jsonObject.toJSONString();
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
                         @RequestParam("creditDataId") BigInteger creditDataId,
                        @RequestParam("time") BigInteger time
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
        JSONObject jsonObject = new JSONObject();
        boolean isSuccess = false;

        TransactionReceipt receipt = record.sendRecordData(recordId, isSend).sendAsync().get();
        if (!receipt.isStatusOK()) {
            System.out.println(receipt.getLogs().toString());
            throw new Exception("/ifSend Status not OK: " + receipt.getLogs().toString());
        }
        List<Record.SendRecordDataSuccessEventResponse> responses = record.getSendRecordDataSuccessEvents(receipt);
        if (responses.isEmpty()) {
            throw new Exception("response empty!");
        }

        String response;
        try {
            OriginCredit originCredit =creditRepository.findById(recordId).get();

            // Todo: get URL
            response = sendOriginData(originCredit, "http://localhost:8080"); // 请求者的 ip

            isSuccess = true;
        } catch (Exception e) {
            response = e.toString();
        }
//        JSONObject jsonResponse = new JSONObject(response);

        jsonObject.put("isSuccess", responses.get(0).yn && isSuccess);
        jsonObject.put("response", response);

        // Update the database
        RequiredRecord requiredRecord = requiredRecordRepository.findById(recordId).get();
        requiredRecord.setSent(isSend);
        requiredRecordRepository.save(requiredRecord);

        return jsonObject.toJSONString();

    }

    private String sendOriginData(OriginCredit originCredit, String url) throws Exception{
        StringBuilder builder = new StringBuilder();
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {

            HttpPost request = new HttpPost(url + "/credit/send");
            request.setHeader("User-Agent", "Java client");

            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("id", originCredit.getCreditId().toString(10)));
            params.add(new BasicNameValuePair("dataOrigin", originCredit.getDataOrigin()));
            params.add(new BasicNameValuePair("dataHash", originCredit.getDataHash()));
            params.add(new BasicNameValuePair("type", originCredit.getType().toString(10)));

            request.setEntity(new UrlEncodedFormEntity(params));

            HttpResponse response = client.execute(request);

            BufferedReader bufReader = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent()));

            String line;

            while ((line = bufReader.readLine()) != null) {
                builder.append(line);
                builder.append(System.lineSeparator());
            }

            System.out.println(builder);
        }
        return builder.toString();
    }

    /**
     * Get Record Data by id from block chain
     * @param recordId
     * @return
     * @throws Exception
     */
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

    /**
     * 可发送列表
     * @return
     */
    @PostMapping(value = "/sendList")
    public Iterable<RequiredRecord> sendList() {

        JSONObject jsonObject = new JSONObject();
        boolean isSuccess = false;
        Iterable<RequiredRecord> list = requiredRecordRepository.findByCheckResult(true);
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
