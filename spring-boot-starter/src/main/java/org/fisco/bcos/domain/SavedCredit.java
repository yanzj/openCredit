package org.fisco.bcos.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigInteger;

@Entity
public class SavedCredit {

    @Id
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
}
