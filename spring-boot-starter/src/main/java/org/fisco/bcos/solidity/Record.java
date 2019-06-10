package org.fisco.bcos.solidity;

import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Address;
import org.fisco.bcos.web3j.abi.datatypes.Bool;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint256;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint8;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple8;
import org.fisco.bcos.web3j.tx.Contract;
import org.fisco.bcos.web3j.tx.TransactionManager;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.fisco.bcos.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version none.
 */
@SuppressWarnings("unchecked")
public class Record extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b506118f3806100206000396000f30060806040526004361061006d576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680638eb69e8114610072578063943d067714610147578063b31b33f6146101d6578063bd74960914610228578063c30f79a814610300575b600080fd5b34801561007e57600080fd5b506100bd60048036038101908080359060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610351565b604051808673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018481526020018381526020018281526020019550505050505060405180910390f35b34801561015357600080fd5b506101bc600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190803590602001909291905050506106ba565b604051808215151515815260200191505060405180910390f35b3480156101e257600080fd5b5061020e60048036038101908080359060200190929190803560ff169060200190929190505050610960565b604051808215151515815260200191505060405180910390f35b34801561023457600080fd5b5061025360048036038101908080359060200190929190505050611006565b604051808973ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018873ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001878152602001868152602001858152602001841515151581526020018360ff1660ff168152602001821515151581526020019850505050505050505060405180910390f35b34801561030c57600080fd5b506103376004803603810190808035906020019092919080351515906020019092919050505061139b565b604051808215151515815260200191505060405180910390f35b600080600080600061036161184e565b3373ffffffffffffffffffffffffffffffffffffffff168773ffffffffffffffffffffffffffffffffffffffff161415151561042b576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260328152602001807f546865206f6e776e6572206f6620746865206461746120636f756c64206e6f7481526020017f20626520746865206170706c6963616e7421000000000000000000000000000081525060400191505060405180910390fd5b600160025401600281905550610100604051908101604052803373ffffffffffffffffffffffffffffffffffffffff1681526020018873ffffffffffffffffffffffffffffffffffffffff1681526020016002548152602001898152602001428152602001600015158152602001600060ff1681526020016000151581525090506000803373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819080600181540180825580915050906001820390600052602060002090600602016000909192909190915060008201518160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060208201518160010160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060408201518160020155606082015181600301556080820151816004015560a08201518160050160006101000a81548160ff02191690831515021790555060c08201518160050160016101000a81548160ff021916908360ff16021790555060e08201518160050160026101000a81548160ff02191690831515021790555050505060013390806001815401808255809150509060018203906000526020600020016000909192909190916101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550508060000151816020015182604001518360600151846080015195509550955095509550509295509295909350565b600080600090505b6000808773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002080549050811015610952578573ffffffffffffffffffffffffffffffffffffffff166000808873ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000208281548110151561076d57fe5b906000526020600020906006020160000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1614801561086957508473ffffffffffffffffffffffffffffffffffffffff166000808873ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000208281548110151561081f57fe5b906000526020600020906006020160010160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16145b80156108d05750836000808873ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020828154811015156108bc57fe5b906000526020600020906006020160020154145b80156109375750826000808873ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000208281548110151561092357fe5b906000526020600020906006020160030154145b156109455760019150610957565b80806001019150506106c2565b600091505b50949350505050565b60008060008091505b600180549050821015610ff957600090505b60008060018481548110151561098d57fe5b9060005260206000200160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002080549050811015610fec5784600080600185815481101515610a1057fe5b9060005260206000200160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002082815481101515610a8257fe5b9060005260206000209060060201600201541415610fdf5760011515600080600185815481101515610ab057fe5b9060005260206000200160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002082815481101515610b2257fe5b906000526020600020906006020160050160009054906101000a900460ff161515141515610bde576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260238152602001807f4f6e6c79207265636965766564206461746120686173206265656e2073636f7281526020017f65642e000000000000000000000000000000000000000000000000000000000081525060400191505060405180910390fd5b3373ffffffffffffffffffffffffffffffffffffffff16600080600185815481101515610c0757fe5b9060005260206000200160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002082815481101515610c7957fe5b906000526020600020906006020160000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16141515610d5c576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260348152602001807f4f6e6c7920746865206170706c6963616e74206f6620746865207265636f726481526020017f2063616e2073636f72652074686520646174612e00000000000000000000000081525060400191505060405180910390fd5b60001515600080600185815481101515610d7257fe5b9060005260206000200160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002082815481101515610de457fe5b906000526020600020906006020160050160029054906101000a900460ff161515141515610e7a576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601c8152602001807f54686973206461746120686173206265656e207265636f726465642e0000000081525060200191505060405180910390fd5b83600080600185815481101515610e8d57fe5b9060005260206000200160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002082815481101515610eff57fe5b906000526020600020906006020160050160016101000a81548160ff021916908360ff1602179055506001600080600185815481101515610f3c57fe5b9060005260206000200160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002082815481101515610fae57fe5b906000526020600020906006020160050160026101000a81548160ff02191690831515021790555060019250610ffe565b808060010191505061097b565b8180600101925050610969565b600092505b505092915050565b60008060008060008060008060008061101d61184e565b600092505b60018054905083101561136857600091505b60008060018581548110151561104657fe5b9060005260206000200160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000208054905082101561135b578b6000806001868154811015156110c957fe5b9060005260206000200160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000208381548110151561113b57fe5b906000526020600020906006020160020154141561134e5760008060018581548110151561116557fe5b9060005260206000200160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020828154811015156111d757fe5b906000526020600020906006020161010060405190810160405290816000820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020016001820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020016002820154815260200160038201548152602001600482015481526020016005820160009054906101000a900460ff161515151581526020016005820160019054906101000a900460ff1660ff1660ff1681526020016005820160029054906101000a900460ff1615151515815250509050806000015181602001518260400151836060015184608001518560a001518660c001518760e001519a509a509a509a509a509a509a509a5061138d565b8180600101925050611034565b8280600101935050611022565b33336000804260008060008595508494508191509a509a509a509a509a509a509a509a505b505050919395975091939597565b60008060008091505b60018054905082101561184157600090505b6000806001848154811015156113c857fe5b9060005260206000200160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002080549050811015611834578460008060018581548110151561144b57fe5b9060005260206000200160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020828154811015156114bd57fe5b9060005260206000209060060201600201541415611827573373ffffffffffffffffffffffffffffffffffffffff166000806001858154811015156114fe57fe5b9060005260206000200160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000208281548110151561157057fe5b906000526020600020906006020160010160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16141515611653576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260368152602001807f4f6e6c7920746865206f776e6572206f662074686520646174612063616e206381526020017f686f6f7365207768657468657220746f2073656e642e0000000000000000000081525060400191505060405180910390fd5b6000151560008060018581548110151561166957fe5b9060005260206000200160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020828154811015156116db57fe5b906000526020600020906006020160050160009054906101000a900460ff161515141515611771576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260188152602001807f54686973206461746120686173206265656e2073656e742e000000000000000081525060200191505060405180910390fd5b8360008060018581548110151561178457fe5b9060005260206000200160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020828154811015156117f657fe5b906000526020600020906006020160050160006101000a81548160ff02191690831515021790555060019250611846565b80806001019150506113b6565b81806001019250506113a4565b600092505b505092915050565b61010060405190810160405280600073ffffffffffffffffffffffffffffffffffffffff168152602001600073ffffffffffffffffffffffffffffffffffffffff168152602001600081526020016000815260200160008152602001600015158152602001600060ff16815260200160001515815250905600a165627a7a723058209858af0da1f2abad3876c214352b5fef01de9c864500ddd1f025c3993455bb5f0029";

    public static final String FUNC_ADDRECORDDATA = "addRecordData";

    public static final String FUNC_CHECKRECORDEXIST = "checkRecordExist";

    public static final String FUNC_SCORERECORDDATA = "scoreRecordData";

    public static final String FUNC_GETRECORDDATABYID = "getRecordDataById";

    public static final String FUNC_SENDRECORDDATA = "sendRecordData";

    @Deprecated
    protected Record(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Record(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Record(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Record(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> addRecordData(BigInteger creditDataId, String owner) {
        final Function function = new Function(
                FUNC_ADDRECORDDATA, 
                Arrays.<Type>asList(new Uint256(creditDataId),
                new Address(owner)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void addRecordData(BigInteger creditDataId, String owner, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_ADDRECORDDATA,
                Arrays.<Type>asList(new Uint256(creditDataId),
                new Address(owner)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public RemoteCall<Boolean> checkRecordExist(String applicant, String owner, BigInteger recordId, BigInteger creditDataId) {
        final Function function = new Function(FUNC_CHECKRECORDEXIST,
                Arrays.<Type>asList(new Address(applicant),
                new Address(owner),
                new Uint256(recordId),
                new Uint256(creditDataId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<TransactionReceipt> scoreRecordData(BigInteger recordId, BigInteger score) {
        final Function function = new Function(
                FUNC_SCORERECORDDATA,
                Arrays.<Type>asList(new Uint256(recordId),
                new Uint8(score)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void scoreRecordData(BigInteger recordId, BigInteger score, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_SCORERECORDDATA,
                Arrays.<Type>asList(new Uint256(recordId),
                new Uint8(score)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public RemoteCall<Tuple8<String, String, BigInteger, BigInteger, BigInteger, Boolean, BigInteger, Boolean>> getRecordDataById(BigInteger recordId) {
        final Function function = new Function(FUNC_GETRECORDDATABYID,
                Arrays.<Type>asList(new Uint256(recordId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}, new TypeReference<Uint8>() {}, new TypeReference<Bool>() {}));
        return new RemoteCall<Tuple8<String, String, BigInteger, BigInteger, BigInteger, Boolean, BigInteger, Boolean>>(
                new Callable<Tuple8<String, String, BigInteger, BigInteger, BigInteger, Boolean, BigInteger, Boolean>>() {
                    @Override
                    public Tuple8<String, String, BigInteger, BigInteger, BigInteger, Boolean, BigInteger, Boolean> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple8<String, String, BigInteger, BigInteger, BigInteger, Boolean, BigInteger, Boolean>(
                                (String) results.get(0).getValue(),
                                (String) results.get(1).getValue(),
                                (BigInteger) results.get(2).getValue(),
                                (BigInteger) results.get(3).getValue(),
                                (BigInteger) results.get(4).getValue(),
                                (Boolean) results.get(5).getValue(),
                                (BigInteger) results.get(6).getValue(),
                                (Boolean) results.get(7).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> sendRecordData(BigInteger recordId, Boolean yn) {
        final Function function = new Function(
                FUNC_SENDRECORDDATA,
                Arrays.<Type>asList(new Uint256(recordId),
                new Bool(yn)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void sendRecordData(BigInteger recordId, Boolean yn, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_SENDRECORDDATA,
                Arrays.<Type>asList(new Uint256(recordId),
                new Bool(yn)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    @Deprecated
    public static Record load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Record(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Record load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Record(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Record load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Record(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Record load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Record(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Record> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Record.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Record> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Record.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Record> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Record.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Record> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Record.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}
