package org.fisco.bcos.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigInteger;

/**
 * 没
 */
@Entity
public class SendRecord {

    @Id
    private BigInteger recordId;  // index

    private String applicant;    // the organize want the data
    private String uploader;     // the organize upload the data
    private BigInteger time;

    private Boolean isChecked;
    private Boolean checkResult;  // If the record in the block chain

    private Boolean isSent;       // whether the original data has been sent from the uploader

    private Boolean isScored;     // whether the uploader has scored

    private BigInteger score;       // the score of this record

    private BigInteger creditId;

    public SendRecord() {
    }

    public SendRecord(BigInteger recordId, String applicant, String uploader, BigInteger time, Boolean checkResult, BigInteger creditId, BigInteger token) {
        this.recordId = recordId;
        this.applicant = applicant;
        this.uploader = uploader;
        this.time = time;
        this.isChecked = true;
        this.checkResult = checkResult;
        this.isSent = false;
        this.isScored = false;
//        this.score = score;
        this.creditId = creditId;
        this.token = token;
    }

    public BigInteger getToken() {
        return token;
    }

    public void setToken(BigInteger token) {
        this.token = token;
    }

    private BigInteger token; // for sending original data

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

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    public Boolean getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(Boolean checkResult) {
        this.checkResult = checkResult;
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

    public BigInteger getCreditId() {
        return creditId;
    }

    public void setCreditId(BigInteger creditId) {
        this.creditId = creditId;
    }
}
