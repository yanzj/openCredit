pragma solidity >=0.4.0 <0.7.0;

contract Record {
    mapping(address => RecordData[]) private records; // key = applicant address
    address[] private applicantArray;          // stores the organizes that applied the data
    uint private id;

    struct RecordData {
        address applicant; // the organize want the data
        address uploader;  // the organize upload the data
        uint id;           // index
        uint creditDataId; // the id of credit data
        uint time;
        bool isSent;       // whether the original data has been sent from the uploader
        uint8 score;       // the score of this record
        bool isScored;     // whether the uploader has scored
    }

    event addRecordSuccess(address _applicant,
                           address _uploader,
                           uint _id,
                           uint _creditDataId,
                           uint _time);

    // Add Record Data
    function addRecordData(uint creditDataId, address uploader)
    public
    {
        require(uploader != msg.sender, "The onwner of the data could not be the applicant!");
        id = id + 1;
        RecordData memory record = RecordData(msg.sender,
                                              uploader,
                                              id,
                                              creditDataId,
                                              now,
                                              false,
                                              0,
                                              false);
        records[msg.sender].push(record);
        applicantArray.push(msg.sender);
        emit addRecordSuccess (record.applicant, record.uploader, record.id, record.creditDataId, record.time);
    }

    // Check if the record is stored.
    function checkRecordExist(address applicant,
                                address uploader,
                                uint recordId,
                                uint creditDataId) public view
    returns (bool) {
        for (uint j = 0; j < records[applicant].length; j++) {
            if (records[applicant][j].applicant == applicant &&
                records[applicant][j].uploader == uploader &&
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
                            tmp.uploader,
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

    event sendRecordDataSuccess(bool yn);
    // 发送原始数据之后，将数据标为已发送
    // 需要对 CreditData 进行查找, 时间与空间的如何选择？ 只能遍历
    function sendRecordData(uint recordId, bool yn)
    public
    {
        for (uint i = 0; i < applicantArray.length; i ++) {
            for (uint j = 0; j < records[applicantArray[i]].length; j++) {
                if (records[applicantArray[i]][j].id == recordId) {
                    require(
                    records[applicantArray[i]][j].uploader == msg.sender,
                    "Only the owner of the data can choose whether to send.");
                    require(
                    records[applicantArray[i]][j].isSent == false,
                    "This data has been sent.");
                    records[applicantArray[i]][j].isSent = yn;
                    emit sendRecordDataSuccess(true);
                }
            }
        }
        emit sendRecordDataSuccess(false);
    }

    event scoreRecordDataSuccess(bool yn);
    // 机构评分 RecordData
    function scoreRecordData(uint recordId, uint8 score)
    public
    {
        for (uint i = 0; i < applicantArray.length; i ++) {
            for (uint j = 0; j < records[applicantArray[i]].length; j++) {
                if (records[applicantArray[i]][j].id == recordId) {
                    require(records[applicantArray[i]][j].isSent == true, "Only recieved data has been scored.");
                    require(records[applicantArray[i]][j].applicant == msg.sender, "Only the applicant of the record can score the data.");
                    require(records[applicantArray[i]][j].isScored == false, "This data has been recorded.");
                    records[applicantArray[i]][j].score = score;
                    records[applicantArray[i]][j].isScored = true;
                    emit scoreRecordDataSuccess(true);
                }
            }
        }
        emit scoreRecordDataSuccess(false);
    }
}