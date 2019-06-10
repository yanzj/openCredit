package org.fisco.bcos.bean;

import java.math.BigInteger;
import java.util.List;

public class CreditData {

    BigInteger id;
    String uploader;
    String peopleId;
    String data;
    String time;

    public CreditData(BigInteger id, String uploader, String peopleId, String data, String time) {
        this.id = id;
        this.uploader = uploader;
        this.peopleId = peopleId;
        this.data = data;
        this.time = time;
    }


    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public String getPeopleId() {
        return peopleId;
    }

    public void setPeopleId(String peopleId) {
        this.peopleId = peopleId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "CreditData{" +
                "id=" + id +
                ", uploader='" + uploader + '\'' +
                ", peopleId='" + peopleId + '\'' +
                ", data='" + data + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
