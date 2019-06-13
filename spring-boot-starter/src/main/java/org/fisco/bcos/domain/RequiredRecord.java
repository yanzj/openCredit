package org.fisco.bcos.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigInteger;

@Entity
public class RequiredRecord {

    @Id
    private BigInteger Recordid;  // index

    private String applicant;    // the organize want the data
    private String uploader;     // the organize upload the data
    private BigInteger creditDataId; // the id of credit data
    private BigInteger time;

    private Boolean isChecked;
    private Boolean checkResult;  // If the record in the block chain

    private Boolean isSent;       // whether the original data has been sent from the uploader

    private Boolean isScored;     // whether the uploader has scored

    private BigInteger score;       // the score of this record

    private String URL; // the applicant URL


    public BigInteger getRecordid() {
        return Recordid;
    }

    public void setRecordid(BigInteger recordid) {
        Recordid = recordid;
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
