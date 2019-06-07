pragma solidity >=0.4.0 <0.7.0;

contract Record {
    mapping(address => RecordData[]) private records; // key = applicant address
    address[] private applicantArray;          // stores the organizes that applied the data
    uint private id;

    struct RecordData {
        address applicant; // the organize want the data
        address owner;     // the organize upload the data
        uint id;           // index
        uint creditDataId; // the id of credit data
        uint time;
        bool isSent;       // whether the original data has been sent from the uploader
        uint8 score;       // the score of this record
        bool isScored;     // whether the uploader has scored
    }

    // Add Record Data
    function addRecordData(uint creditDataId, address owner) public payable
    returns (address, address, uint, uint, uint) {
        require(owner != msg.sender, "The onwner of the data could not be the applicant!");
        id = id + 1;
        RecordData memory record = RecordData(msg.sender,
                                              owner,
                                              id,
                                              creditDataId,
                                              now,
                                              false,
                                              0,
                                              false);
        records[msg.sender].push(record);
        applicantArray.push(msg.sender);
        return (record.applicant, record.owner, record.id, record.creditDataId, record.time);
    }

    // Check if the record is stored.
    function checkRecordExist(address applicant,
                                address owner,
                                uint recordId,
                                uint creditDataId) public view
    returns (bool) {
        for (uint j = 0; j < records[applicant].length; j++) {
            if (records[applicant][j].applicant == applicant &&
                records[applicant][j].owner == owner &&
                records[applicant][j].id == recordId &&
                records[applicant][j].creditDataId == creditDataId) {
                return true;
            }
        }
        return false;
    }

    // Get the RecordData detail by recordId
    function getRecordDataById(uint recordId) public view
    returns (address, address, uint, uint, uint, bool, uint8, bool) {
        for (uint i = 0; i < applicantArray.length; i ++) {
            for (uint j = 0; j < records[applicantArray[i]].length; j++) {
                if (records[applicantArray[i]][j].id == recordId) {
                    RecordData memory tmp = records[applicantArray[i]][j];
                    return (tmp.applicant,
                            tmp.owner,
                            tmp.id,
                            tmp.creditDataId,
                            tmp.time,
                            tmp.isSent,
                            tmp.score,
                            tmp.isScored);
                }
            }
        }
        return (msg.sender, msg.sender, 0, 0, now, false, 0, false);
        // return a zero record to imply can't find the record
    }

    // 发送原始数据之后，将数据标为已发送
    // 需要对 CreditData 进行查找, 时间与空间的如何选择？ 只能遍历
    function sendRecordData(uint recordId, bool yn) public
    returns(bool){
        for (uint i = 0; i < applicantArray.length; i ++) {
            for (uint j = 0; j < records[applicantArray[i]].length; j++) {
                if (records[applicantArray[i]][j].id == recordId) {
                    require(records[applicantArray[i]][j].owner == msg.sender, "Only the owner of the data can choose whether to send.");
                    require(records[applicantArray[i]][j].isSent == false, "This data has been sent.");
                    records[applicantArray[i]][j].isSent = yn;
                    return true;
                }
            }
        }
        return false;
    }

    // 机构评分 RecordData
    function scoreRecordData(uint recordId, uint8 score) public
    returns(bool) {
        for (uint i = 0; i < applicantArray.length; i ++) {
            for (uint j = 0; j < records[applicantArray[i]].length; j++) {
                if (records[applicantArray[i]][j].id == recordId) {
                    require(records[applicantArray[i]][j].isSent == true, "Only recieved data has been scored.");
                    require(records[applicantArray[i]][j].applicant == msg.sender, "Only the applicant of the record can score the data.");
                    require(records[applicantArray[i]][j].isScored == false, "This data has been recorded.");
                    records[applicantArray[i]][j].score = score;
                    records[applicantArray[i]][j].isScored = true;
                    return true;
                }
            }
        }
        return false;
    }

    function extractRecordDataToBytes(RecordData memory record) private pure
    returns (bytes memory) {
        uint _size = 130; // uint(32 bytes)*3 + unit8(1 byte)*1 + bool(1 byte)*2 + address(20 bytes)*2 = 32*3 + 1 + 1 + 32 * 2 = 130
        bytes memory _data = new bytes(_size);
        uint counter = 0;
        
        // serialize applicant address
        uint applicantAddr = uint(record.applicant);
        for (uint i = 0; i < 32; i++) {
            _data[counter++] = byte(uint8(applicantAddr>>(8*i)));
        }
        // serialize owner address
        uint ownerAddr = uint(record.applicant);
        for (uint i = 0; i < 32; i++) {
            _data[counter++] = byte(uint8(ownerAddr>>(8*i)));
        }
        // serialize id, creditDataId, time, score
        for (uint i = 0; i < 32; i++) {
            _data[counter++] = byte(uint8(record.id>>(8*i)));
        }
        for (uint i = 0; i < 32; i++) {
            _data[counter++] = byte(uint8(record.creditDataId>>(8*i)));
        }
        for (uint i = 0; i < 32; i++) {
            _data[counter++] = byte(uint8(record.time>>(8*i)));
        }
        _data[counter++] = byte(record.score);
        // serialize isSent and isSent
        uint8 isSS;
        if (record.isSent && record.isSent) {
            isSS = 3;
        } else if (record.isSent && !record.isSent) {
            isSS = 1;
        } else if (!record.isSent && record.isSent) {
            isSS = 2;
        } else {
            isSS = 0;
        }
        _data[counter++] = byte(isSS);
        return _data;
    }

    function extractRecordData(bytes memory data) private pure
    returns (RecordData memory r) {
        uint counter = 0;
        // deserialize
        uint applicantAddr = bytesToUint(data, counter);
        counter += 32;
        r.applicant = address(applicantAddr);

        uint ownerAddr = bytesToUint(data, counter);
        counter += 32;
        r.owner = address(ownerAddr);
        
        r.id = bytesToUint(data, counter);
        counter += 32;
        
        r.creditDataId = bytesToUint(data, counter);
        counter += 32;
        
        r.time = bytesToUint(data, counter);
        counter += 32;

        r.score = uint8(data[counter++]);
        
        uint8 isSS = uint8(data[counter]);
        if (isSS == 0) {
            r.isScored = false;
            r.isSent = false;
        } else if (isSS == 1) {
            r.isSent = true;
            r.isSent = false;
        } else if (isSS == 2) {
            r.isSent = false;
            r.isSent = true;
        } else {
            r.isSent = true;
            r.isSent = true;
        }
    }

    function bytesToUint(bytes memory data, uint offset) private pure
    returns (uint num) {
        for (uint i = 0; i < 32; i++) {
            uint temp = uint(uint8(data[i+offset]));
            temp <<= 8*i;
            num ^= temp;
        }
    }
}