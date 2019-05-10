pragma solidity >=0.4.0 <0.7.0;

contract Record {

    struct RecordData {
        address applicant; // the organize want the data
        uint id;           // index
        uint creditDataId; // the id of credit data
        uint time;
        bool isSent;       // whether the original data has been sent from the uploader
        uint8 score;        // the score of this record
        bool isScored;     // whether the uploader has scored
    }
    mapping(address => RecordData[]) records; // key = applicant address
    address[] public applicantArray;
    uint id;

    // 请求数据， 链上记录
    function addRecordData(uint creditDataId) public payable returns (uint ) {
        id = id + 1;
        RecordData memory record = RecordData(msg.sender, id, creditDataId, now, false, 0, false);
        records[msg.sender].push(record);
        applicantArray.push(msg.sender);
        return id;
    }

    // get serialized record data by id
    function getRecordDataDetialById(uint id) public view returns (bytes memory) {
        return extractRecordDataToBytes(getRecordDataById(id)); // return a zero record to imply can't find the record
    }

    function getRecordDataById(uint id) private view returns (RecordData memory) {
        for (uint i = 0; i < applicantArray.length; i ++) {
            for (uint j = 0; j < records[applicantArray[i]].length; j++) {
                if (records[applicantArray[i]][j].id == id) {
                    return records[applicantArray[i]][j];
                }
            }
        }
        RecordData memory record = RecordData(msg.sender, 0, 0, now, false, 0, false);
        return record; // return a zero record to imply can't find the record
    }

    // 查询是否有请求
    function getOneRecordData() public view returns(uint) {
        for (uint i = 0; i < applicantArray.length; i++) {
            for (uint j = 0; j < records[applicantArray[i]].length ;j++) {
                if (records[applicantArray[i]][j].creditDataId == id){
                    // Credit.credits[Credit.idArray[i]][j].isSent = true;
                    return 1;
                }
            }
        }
        return 0;
    }

    // 发送原始数据之后，将数据标为已发送
    // 需要对 CreditData 进行查找, 时间与空间的如何选择？
    function sendRecordData(uint recordId) public view returns(bool){
        RecordData memory record = extractRecordData(getRecordDataDetialById(recordId));
        // require(credit.uploader == msg.sender, "Only the applicant of the record can record the data.");
        // require(record.isScored, "This record has been recorded.");
    }

    function scoreRecordData(uint recordId, uint8 score) public view {
        RecordData memory record = extractRecordData(getRecordDataDetialById(recordId));
        require(record.applicant == msg.sender, "Only the applicant of the record can record the data.");
        require(record.isScored, "This record has been recorded.");
        record.score = score;
        record.isScored = true;
    }

    function extractRecordDataToBytes(RecordData memory record) private pure
    returns (bytes memory) {
        uint _size = 130; // uint(32 bytes)*3 + unit8(1 byte)*1 + bool(1 byte)*2 + address(20 bytes)*1 = 32*3 + 1 + 1 + 32 = 130
        bytes memory _data = new bytes(_size);
        uint counter = 0;
        
        // serialize address
        uint recordAddr = uint(record.applicant);
        for (uint i = 0; i < 32; i++) {
            _data[counter++] = byte(uint8(recordAddr>>(8*i)));
        }
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
        uint recordAddr = bytesToUint(data, counter);
        counter += 32;
        
        r.applicant = address(recordAddr);
        counter += 32;
        
        r.id = bytesToUint(data, counter);
        counter += 32;
        
        r.creditDataId = bytesToUint(data, counter);
        counter += 32;
        
        r.time = bytesToUint(data, counter);
        counter += 32;
        
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