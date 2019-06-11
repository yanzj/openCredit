package org.fisco.bcos.bean;

import java.math.BigInteger;
import java.util.List;

public class CreditData {

    BigInteger id;
    String uploader;
    BigInteger type;
    String data;
    String time;

    public CreditData(BigInteger id, String uploader, String data, BigInteger type, String time) {
        this.id = id;
        this.uploader = uploader;
        this.data = data;
        this.type = type;
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

    public BigInteger getType() {
        return type;
    }

    public void setType(BigInteger type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "CreditData{" +
                "id=" + id +
                ", uploader='" + uploader + '\'' +
                ", type=" + type +
                ", data='" + data + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
