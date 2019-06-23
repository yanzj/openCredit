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
import org.fisco.bcos.domain.SendRecord;
import org.fisco.bcos.service.OriginCreditRepository;
import org.fisco.bcos.service.RequireRecordRepository;
import org.fisco.bcos.service.SendRecordRepository;
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
    OriginCreditRepository originCreditRepository;

    @Autowired
    SendRecordRepository sendRecordRepository; // 对方

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
    String addRecord(@RequestParam("creditDataId") BigInteger creditDataId,
                         @RequestParam("uploader") String uploader) throws Exception {
        boolean isSuccess = false;
        JSONObject returnValue = new JSONObject();

        // Add record data in block chain
        TransactionReceipt result = record.addRecordData(creditDataId, uploader).sendAsync().get();

        if (!result.isStatusOK()) {
            log.error(result.getLogs().toString());
            returnValue.put("isSuccess", false);
            returnValue.put("error", result.getLogs().toString());
            return returnValue.toJSONString();
        }

        // get the response from block chain
        List<Record.AddRecordSuccessEventResponse> responses = record.getAddRecordSuccessEvents(result);

        if (responses.isEmpty()) {
            log.error("No success! Response is empty!");
            returnValue.put("isSuccess", false);
            returnValue.put("error", "No success! Response is empty!");
            return returnValue.toJSONString();
        }

        // Save the record in the database
        RequiredRecord requiredRecord = new RequiredRecord(responses.get(0));
        requireRecordRepository.save(requiredRecord);

        // Todo: Get the URL
        String resultStr = requireOriginData(requiredRecord, "http://localhost:8081");
        log.info("/record/add Http requireOriginData, result = {}", resultStr);

        JSONObject response = JSONObject.parseObject(resultStr);

        if (response.getBoolean("isSuccess")) {
            isSuccess = true;
        }

        returnValue.put("isSuccess", isSuccess);
        returnValue.put("RequiredRecord", requiredRecord);
        returnValue.put("responseStr", response.toJSONString());

        return returnValue.toJSONString();
    }

    /**
     * Start a HTTP request for requiring original data
     * @param requiredRecord
     * @param url
     * @return The HTTP response
     * @throws Exception
     */
    private String requireOriginData(RequiredRecord requiredRecord, String url) throws Exception{
        StringBuilder builder = new StringBuilder();
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {

            HttpPost request = new HttpPost(url + "/record/requireOrigin");
            request.setHeader("User-Agent", "Java client");

            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("applicant", requiredRecord.getApplicant()));
            params.add(new BasicNameValuePair("uploader", requiredRecord.getUploader()));
            params.add(new BasicNameValuePair("recordId", requiredRecord.getRecordId().toString(10)));
            params.add(new BasicNameValuePair("creditId", requiredRecord.getCreditId().toString(10)));
            params.add(new BasicNameValuePair("time", requiredRecord.getTime().toString(10)));
            params.add(new BasicNameValuePair("token", requiredRecord.getToken().toString(10)));
            request.setEntity(new UrlEncodedFormEntity(params));

            HttpResponse response = client.execute(request);

            BufferedReader bufReader = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent()));

            String line;

            while ((line = bufReader.readLine()) != null) {
                builder.append(line);
                builder.append(System.lineSeparator());
            }
        }
        return builder.toString();
    }

    /**
     * Stores the required record data
     * @param applicant
     * @param uploader
     * @param recordId
     * @param creditId
     * @param time
     * @return
     */
    @PostMapping(value = "/requireOrigin")
    public String requireRecordData(@RequestParam("applicant") String applicant,
                                    @RequestParam("uploader") String uploader,
                                    @RequestParam("recordId") BigInteger recordId,
                                    @RequestParam("creditId") BigInteger creditId,
                                    @RequestParam("time") BigInteger time,
                                    @RequestParam("token") BigInteger token) {
        JSONObject jsonObject = new JSONObject();

        boolean isSuccess = false;

        try {
            boolean isExist = record.checkRecordExist(applicant, uploader, recordId, creditId).sendAsync().get();
            log.info("/record/requireOrigin isExist = {}", isExist);

            SendRecord sendRecord = new SendRecord(recordId, applicant, uploader, time, isExist, creditId, token);

            sendRecordRepository.save(sendRecord);

            isSuccess = true;
        } catch (Exception e) {
            jsonObject.put("error", e.toString());
            log.error(e.toString());
        } finally {
            jsonObject.put("isSuccess", isSuccess);
        }
        return jsonObject.toJSONString();
    }

    /**
     * Not used now
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
        JSONObject jsonObjectError = new JSONObject();

        TransactionReceipt receipt = record.sendRecordData(recordId, isSend).sendAsync().get();
        if (!receipt.isStatusOK()) {
            log.error("/record/ifSend Status not OK: " + receipt.getLogs().toString());
            jsonObjectError.put("receiptStatus", receipt.isStatusOK());
        }
        List<Record.SendRecordDataSuccessEventResponse> responses = record.getSendRecordDataSuccessEvents(receipt);
        if (responses.isEmpty()) {
            log.error("/record/ifSend response empty!");
            jsonObjectError.put("sizeOfResponseFromContract", responses.size());
        }

        String responseStr;
        try {
            SendRecord sendRecord = sendRecordRepository.findById(recordId).get();
            OriginCredit originCredit = originCreditRepository.findById(sendRecord.getCreditId()).get();

            // Todo: get URL
            responseStr = sendOriginData(originCredit, "http://localhost:8080", sendRecord.getToken()); // 请求者的 ip

            log.info("/record/ifSend Http sendOriginData, result = {}", responseStr);

            JSONObject response = JSONObject.parseObject(responseStr);

            if (response.getBoolean("isSuccess")) {
                isSuccess = true;

                // Update the database
                sendRecord.setSent(true);
                sendRecordRepository.save(sendRecord);
            } else {
                jsonObjectError.put("/credit/sendFail", response);
                throw new Exception(response.getString("error"));
            }

        } catch (Exception e) {
            responseStr = e.toString();
        }

        jsonObject.put("isSuccess", responses.get(0).yn && isSuccess);
        jsonObject.put("response", responseStr);
        if (!jsonObject.getBoolean("isSuccess")) {
            jsonObject.put("error", jsonObjectError);
        }

        return jsonObject.toJSONString();
    }

    private String sendOriginData(OriginCredit originCredit, String url, BigInteger token) throws Exception{
        StringBuilder builder = new StringBuilder();
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {

            HttpPost request = new HttpPost(url + "/credit/send");
            request.setHeader("User-Agent", "Java client");

            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("id", originCredit.getCreditId().toString(10)));
            params.add(new BasicNameValuePair("dataOrigin", originCredit.getDataOrigin()));
            params.add(new BasicNameValuePair("dataHash", originCredit.getDataHash()));
            params.add(new BasicNameValuePair("type", originCredit.getType().toString(10)));
            params.add(new BasicNameValuePair("token", token.toString(10)));

            request.setEntity(new UrlEncodedFormEntity(params));

            HttpResponse response = client.execute(request);

            BufferedReader bufReader = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent()));

            String line;

            while ((line = bufReader.readLine()) != null) {
                builder.append(line);
                builder.append(System.lineSeparator());
            }
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

    /**
     * 评分
     * @param recordId
     * @param score
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/score")
    public @ResponseBody
    String scoreRecordData( @RequestParam("recordId") BigInteger recordId,
                             @RequestParam("score") BigInteger score) throws Exception {
        JSONObject jsonObject = new JSONObject();
        Boolean isSuccess = false;
        String resultStr = "";

        TransactionReceipt receipt = record.scoreRecordData(recordId, score).sendAsync().get();
        if (!receipt.isStatusOK()) {
            log.error("/record/score Status not OK: {}", receipt.getLogs().toString());
            resultStr = "/score Status not OK: " + receipt.getLogs().toString();
        }
        List<Record.SendRecordDataSuccessEventResponse> responses = record.getSendRecordDataSuccessEvents(receipt);

        if (responses.isEmpty()) {
            log.error("/record/score responses isEmpty()");
            resultStr += "response empty!";
        } else {
            isSuccess = true;
        }

        jsonObject.put("isSuccess", responses.get(0).yn && isSuccess);
        jsonObject.put("error", resultStr);

        return jsonObject.toJSONString();
    }

    /**
     * Get
     * 可发送列表
     * @return
     */
    @GetMapping(value = "/sendList")
    public Iterable<SendRecord> sendList() {
        Iterable<SendRecord> list = sendRecordRepository.findByCheckResult(true);
        return  list;
    }


    /**
     * Get
     * 已请求列表
     * @return
     */
    @GetMapping(value = "/requiredList")
    public Iterable<RequiredRecord> getRequiredRecordList() {
        return requireRecordRepository.findAll();
    }


    /**
     * Get
     * 可评分列表
     * @return
     */
    @GetMapping(value = "/scoreList")
    public Iterable<RequiredRecord> scoreList() {
        // 已收到未评分
        Iterable<RequiredRecord> list = requireRecordRepository.findByIsSentAndIsScored(true, false);
        return  list;
    }
}
