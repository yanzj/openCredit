pragma solidity >=0.4.0 <0.7.0;
pragma experimental ABIEncoderV2;

contract Credit {

    struct CreditData {
        uint id;               // index
        address uploader;
        bytes32 peopleId;      // 256 bits hashed id by keccak256()
        bytes32 data;          // hashed credit data
        uint time;             // time the data upload
    }

    // ?上次遗留：如果不 map到一个数组，如何读取历史记录,如何高效查找数据
    // ？如果要修改一个数组的一个值，如何修改？ 直接取出来是放在内存里的，修改完之后会自动覆盖在数组上面吗？还是会重新建一个数组？
    mapping(bytes32 => CreditData[]) public credits; // key = id
    bytes32[] public idArray;
    uint id; // 初始化为 0

    function addCreditData(string memory id_string, string memory data_string) public {
        id = id + 1;
        bytes32 peopleId = keccak256(abi.encodePacked(id_string));
        CreditData memory credit = CreditData(id, msg.sender, peopleId, keccak256(abi.encodePacked(data_string)), now);
        credits[peopleId].push(credit);
        idArray.push(peopleId);
    }

    // 问题： 数据量太大怎么办? 先不考虑
    function getCreditDetialDataById(string memory id_string) public view
    returns(
            uint[] memory,    //  id
            address[] memory, // uploader, 返回为二进制，如何辨识
            //bytes32[] memory,
            bytes32[] memory,              // datas
            uint[] memory) {                // times
        CreditData[] memory credit_array = credits[keccak256(abi.encodePacked(id_string))];
        uint credit_array_length = credit_array.length;
        uint[] memory ids = new uint[](credit_array_length);
        address[] memory uploaders = new address[](credit_array_length);
        //bytes32[] memory ids = new bytes32[](credit_array_length);
        bytes32[] memory datas = new bytes32[](credit_array_length);
        uint[] memory times = new uint[](credit_array_length);
        for (uint i = 0; i < credit_array.length; ++i) {
            ids[i] = credit_array[i].id;
            uploaders[i] = credit_array[i].uploader;
            //ids[i] = credit_array[i].id;
            datas[i] = credit_array[i].data;
            times[i] = credit_array[i].time;
        }
        return (ids, uploaders, datas, times);
    }

    function getCreditDataById(uint id) public view
    returns(CreditData memory) {
        for (uint i = 0; i < idArray.length; i++) {
            for (uint j = 0; j < credits[idArray[i]].length ;j++) {
                if (credits[idArray[i]][j].id == id){
                    return credits[idArray[i]][j];
                }
            }
        }
    }
}