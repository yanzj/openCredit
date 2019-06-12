package org.fisco.bcos.bean;

import org.fisco.bcos.solidity.Record;
import org.fisco.bcos.web3j.tuples.generated.Tuple8;

import java.math.BigInteger;

public class RecordData {

    String applicant; // the organize want the data
    String uploader;     // the organize upload the data
    BigInteger id;           // index
    BigInteger creditDataId; // the id of credit data
    BigInteger time;
    Boolean isSent;       // whether the original data has been sent from the uploader
    BigInteger score;       // the score of this record
    Boolean isScored;     // whether the uploader has scored

    public RecordData() {
    }

    public RecordData(String applicant, String owner, BigInteger id, BigInteger creditDataId, BigInteger time, Boolean isSent, BigInteger score, Boolean isScored) {
        this.applicant = applicant;
        this.uploader = owner;
        this.id = id;
        this.creditDataId = creditDataId;
        this.time = time;
        this.isSent = isSent;
        this.score = score;
        this.isScored = isScored;
    }

    public RecordData(Record.AddRecordSuccessEventResponse reponse) {
        this.applicant = reponse._applicant;
        this.uploader = reponse._uploader;
        this.id = reponse._id;
        this.creditDataId = reponse._creditDataId;
        this.time = reponse._time;
        this.isSent = false;
        this.score = new BigInteger("0");
        this.isScored = false;
    }

    public RecordData(Tuple8<String, String, BigInteger, BigInteger, BigInteger, Boolean, BigInteger, Boolean> result)  {
        this.applicant = result.getValue1();
        this.uploader = result.getValue2();
        this.id = result.getValue3();
        this.creditDataId = result.getValue4();
        this.time = result.getValue5();
        this.isSent = result.getValue6();
        this.score = result.getValue7();
        this.isScored = result.getValue8();
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

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
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

    public Boolean getSent() {
        return isSent;
    }

    public void setSent(Boolean sent) {
        isSent = sent;
    }

    public BigInteger getScore() {
        return score;
    }

    public void setScore(BigInteger score) {
        this.score = score;
    }

    public Boolean getScored() {
        return isScored;
    }

    public void setScored(Boolean scored) {
        isScored = scored;
    }

    @Override
    public String toString() {
        return "RecordData{" +
                "applicant='" + applicant + '\'' +
                ", uploader='" + uploader + '\'' +
                ", id=" + id +
                ", creditDataId=" + creditDataId +
                ", time=" + time +
                ", isSent=" + isSent +
                ", score=" + score +
                ", isScored=" + isScored +
                '}';
    }
}
