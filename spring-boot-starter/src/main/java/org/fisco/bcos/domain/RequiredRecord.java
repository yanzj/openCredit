package org.fisco.bcos.domain;


import org.fisco.bcos.service.OriginCreditRepository;
import org.fisco.bcos.solidity.Record;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigInteger;

@Entity
public class RequiredRecord {

    @Id
    private BigInteger recordId;  // index

    private String applicant;    // the organize want the data
    private String uploader;     // the organize upload the data
    private BigInteger time;

    private Boolean isSent;       // whether the original data has been sent from the uploader

    private Boolean isScored;     // whether the uploader has scored

    private BigInteger score;       // the score of this record

    private BigInteger creditId;   // the id of credit data

    private String dataOrigin;

    private String dataHash;

    private BigInteger type;

    public BigInteger getToken() {
        return token;
    }

    public void setToken(BigInteger token) {
        this.token = token;
    }

    private BigInteger token;   // for sending original data

    public RequiredRecord() {
    }

    public RequiredRecord(Record.AddRecordSuccessEventResponse response) {
        recordId = response._id;
        applicant = response._applicant;
        uploader = response._uploader;
        creditId = response._creditDataId;
        time = response._time;

        isSent = false;
        isScored = false;
    }

    public BigInteger getCreditId() {
        return creditId;
    }

    public void setCreditId(BigInteger creditId) {
        this.creditId = creditId;
    }

    public String getDataOrigin() {
        return dataOrigin;
    }

    public void setDataOrigin(String dataOrigin) {
        this.dataOrigin = dataOrigin;
    }

    public String getDataHash() {
        return dataHash;
    }

    public void setDataHash(String dataHash) {
        this.dataHash = dataHash;
    }

    public BigInteger getType() {
        return type;
    }

    public void setType(BigInteger type) {
        this.type = type;
    }

    public BigInteger getRecordId() {
        return recordId;
    }

    public void setRecordId(BigInteger recordId) {
        this.recordId = recordId;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public BigInteger getTime() {
        return time;
    }

    public void setTime(BigInteger time) {
        this.time = time;
    }

    public Boolean getSent() {
        return isSent;
    }

    public void setSent(Boolean sent) {
        isSent = sent;
    }

    public Boolean getScored() {
        return isScored;
    }

    public void setScored(Boolean scored) {
        isScored = scored;
    }

    public BigInteger getScore() {
        return score;
    }

    public void setScore(BigInteger score) {
        this.score = score;
    }
}
