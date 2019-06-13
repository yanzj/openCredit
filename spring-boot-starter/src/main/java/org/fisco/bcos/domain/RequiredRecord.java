package org.fisco.bcos.domain;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigInteger;

@Entity
public class RequiredRecord {

    @Id
    private BigInteger recordId;  // index

    private String applicant;    // the organize want the data
    private String uploader;     // the organize upload the data
    private BigInteger creditDataId; // the id of credit data
    private BigInteger time;

    private Boolean isChecked;
    private Boolean checkResult;  // If the record in the block chain

    private Boolean isSent;       // whether the original data has been sent from the uploader

    private Boolean isScored;     // whether the uploader has scored

    private BigInteger score;       // the score of this record

    private BigInteger creditId;

    private String dataOrigin;

    private String dataHash;

    private BigInteger type;

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

    public BigInteger getCreditDataId() {
        return creditDataId;
    }

    public void setCreditDataId(BigInteger creditDataId) {
        this.creditDataId = creditDataId;
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
}
