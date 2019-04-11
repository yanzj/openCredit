pragma solidity >=0.4.0 <0.7.0;
// pragma experimental ABIEncoderV2;

contract Credit {

    struct CreditData {
        address uploader;
        bytes32 id;      // 256 bits hashed id by keccak256()
        bytes32 data;    // hashed credit data 
        uint time;       // time the data upload
    }

    struct RecordData {
        address requirer; // the organize want the data
        CreditData creditData; // the address of credit data
        uint time;
        bool isSent;      // is the original data be sent from the uploader
    }

    mapping(bytes32 => CreditData[]) public credits; // key = id
    mapping(address => RecordData[]) records; // key = requirer
    bytes32[] public idArray;
    address[] public requirerArray;

    function addCreditData(string memory id_string, string memory data_string) public {
        bytes32 id = keccak256(abi.encodePacked(id_string));
        CreditData memory credit = CreditData(msg.sender, id, keccak256(abi.encodePacked(data_string)), now);
        credits[id].push(credit);
        idArray.push(id);
    }

    function getCreditDataById(string memory id_string) public view returns( address[] memory, 
                                                                //bytes32[] memory, 
                                                                bytes32[] memory, 
                                                                uint[] memory){
        CreditData[] memory credit_array = credits[keccak256(abi.encodePacked(id_string))];
        uint credit_array_length = credit_array.length;
        address[] memory uploaders = new address[](credit_array_length);
        //bytes32[] memory ids = new bytes32[](credit_array_length);
        bytes32[] memory datas = new bytes32[](credit_array_length);
        uint[] memory times = new uint[](credit_array_length);
        for (uint i = 0; i < credit_array.length; ++i) {
            uploaders[i] = credit_array[i].uploader;
            //ids[i] = credit_array[i].id;
            datas[i] = credit_array[i].data;
            times[i] = credit_array[i].time;
        }
        return (uploaders, datas, times);
    }


    // function addRecordData(addr name) {
        
    // }

    // function getRecordData() public view returns(records){

    // }
    
}