package org.fisco.bcos.temp;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.web3j.abi.EventEncoder;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Address;
import org.fisco.bcos.web3j.abi.datatypes.Bool;
import org.fisco.bcos.web3j.abi.datatypes.Event;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint256;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint8;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.DefaultBlockParameter;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.protocol.core.methods.request.BcosFilter;
import org.fisco.bcos.web3j.protocol.core.methods.response.Log;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple8;
import org.fisco.bcos.web3j.tx.Contract;
import org.fisco.bcos.web3j.tx.TransactionManager;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;

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
    private static final String BINARY = "608060405234801561001057600080fd5b5061199d806100206000396000f30060806040526004361061006d576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680638eb69e8114610072578063943d0677146100bf578063b31b33f61461014e578063bd74960914610188578063c30f79a814610260575b600080fd5b34801561007e57600080fd5b506100bd60048036038101908080359060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610299565b005b3480156100cb57600080fd5b50610134600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803573ffffffffffffffffffffffffffffffffffffffff1690602001909291908035906020019092919080359060200190929190505050610694565b604051808215151515815260200191505060405180910390f35b34801561015a57600080fd5b5061018660048036038101908080359060200190929190803560ff16906020019092919050505061093a565b005b34801561019457600080fd5b506101b360048036038101908080359060200190929190505050611048565b604051808973ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018873ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001878152602001868152602001858152602001841515151581526020018360ff1660ff168152602001821515151581526020019850505050505050505060405180910390f35b34801561026c57600080fd5b50610297600480360381019080803590602001909291908035151590602001909291905050506113dd565b005b6102a16118f8565b3373ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff161415151561036b576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260328152602001807f546865206f6e776e6572206f6620746865206461746120636f756c64206e6f7481526020017f20626520746865206170706c6963616e7421000000000000000000000000000081525060400191505060405180910390fd5b600160025401600281905550610100604051908101604052803373ffffffffffffffffffffffffffffffffffffffff1681526020018373ffffffffffffffffffffffffffffffffffffffff1681526020016002548152602001848152602001428152602001600015158152602001600060ff1681526020016000151581525090506000803373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819080600181540180825580915050906001820390600052602060002090600602016000909192909190915060008201518160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060208201518160010160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060408201518160020155606082015181600301556080820151816004015560a08201518160050160006101000a81548160ff02191690831515021790555060c08201518160050160016101000a81548160ff021916908360ff16021790555060e08201518160050160026101000a81548160ff02191690831515021790555050505060013390806001815401808255809150509060018203906000526020600020016000909192909190916101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550507fa6a338f2b4280fe9c40640ec744427ec4efa8acc5be76b8fd647bbd8288afd6881600001518260200151836040015184606001518560800151604051808673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018481526020018381526020018281526020019550505050505060405180910390a1505050565b600080600090505b6000808773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000208054905081101561092c578573ffffffffffffffffffffffffffffffffffffffff166000808873ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000208281548110151561074757fe5b906000526020600020906006020160000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1614801561084357508473ffffffffffffffffffffffffffffffffffffffff166000808873ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020828154811015156107f957fe5b906000526020600020906006020160010160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16145b80156108aa5750836000808873ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000208281548110151561089657fe5b906000526020600020906006020160020154145b80156109115750826000808873ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020828154811015156108fd57fe5b906000526020600020906006020160030154145b1561091f5760019150610931565b808060010191505061069c565b600091505b50949350505050565b600080600091505b60018054905082101561100657600090505b60008060018481548110151561096657fe5b9060005260206000200160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002080549050811015610ff957836000806001858154811015156109e957fe5b9060005260206000200160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002082815481101515610a5b57fe5b9060005260206000209060060201600201541415610fec5760011515600080600185815481101515610a8957fe5b9060005260206000200160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002082815481101515610afb57fe5b906000526020600020906006020160050160009054906101000a900460ff161515141515610bb7576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260238152602001807f4f6e6c79207265636965766564206461746120686173206265656e2073636f7281526020017f65642e000000000000000000000000000000000000000000000000000000000081525060400191505060405180910390fd5b3373ffffffffffffffffffffffffffffffffffffffff16600080600185815481101515610be057fe5b9060005260206000200160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002082815481101515610c5257fe5b906000526020600020906006020160000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16141515610d35576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260348152602001807f4f6e6c7920746865206170706c6963616e74206f6620746865207265636f726481526020017f2063616e2073636f72652074686520646174612e00000000000000000000000081525060400191505060405180910390fd5b60001515600080600185815481101515610d4b57fe5b9060005260206000200160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002082815481101515610dbd57fe5b906000526020600020906006020160050160029054906101000a900460ff161515141515610e53576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601c8152602001807f54686973206461746120686173206265656e207265636f726465642e0000000081525060200191505060405180910390fd5b82600080600185815481101515610e6657fe5b9060005260206000200160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002082815481101515610ed857fe5b906000526020600020906006020160050160016101000a81548160ff021916908360ff1602179055506001600080600185815481101515610f1557fe5b9060005260206000200160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002082815481101515610f8757fe5b906000526020600020906006020160050160026101000a81548160ff0219169083151502179055507f71c3634aa990a16a9da4df60686edbe51accebe978cdbc1a050ec9a62c50fe356001604051808215151515815260200191505060405180910390a15b8080600101915050610954565b8180600101925050610942565b7f71c3634aa990a16a9da4df60686edbe51accebe978cdbc1a050ec9a62c50fe356000604051808215151515815260200191505060405180910390a150505050565b60008060008060008060008060008061105f6118f8565b600092505b6001805490508310156113aa57600091505b60008060018581548110151561108857fe5b9060005260206000200160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000208054905082101561139d578b60008060018681548110151561110b57fe5b9060005260206000200160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000208381548110151561117d57fe5b9060005260206000209060060201600201541415611390576000806001858154811015156111a757fe5b9060005260206000200160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000208281548110151561121957fe5b906000526020600020906006020161010060405190810160405290816000820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020016001820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020016002820154815260200160038201548152602001600482015481526020016005820160009054906101000a900460ff161515151581526020016005820160019054906101000a900460ff1660ff1660ff1681526020016005820160029054906101000a900460ff1615151515815250509050806000015181602001518260400151836060015184608001518560a001518660c001518760e001519a509a509a509a509a509a509a509a506113cf565b8180600101925050611076565b8280600101935050611064565b33336000804260008060008595508494508191509a509a509a509a509a509a509a509a505b505050919395975091939597565b600080600091505b6001805490508210156118b657600090505b60008060018481548110151561140957fe5b9060005260206000200160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020805490508110156118a9578360008060018581548110151561148c57fe5b9060005260206000200160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020828154811015156114fe57fe5b906000526020600020906006020160020154141561189c573373ffffffffffffffffffffffffffffffffffffffff1660008060018581548110151561153f57fe5b9060005260206000200160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020828154811015156115b157fe5b906000526020600020906006020160010160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16141515611694576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260368152602001807f4f6e6c7920746865206f776e6572206f662074686520646174612063616e206381526020017f686f6f7365207768657468657220746f2073656e642e0000000000000000000081525060400191505060405180910390fd5b600015156000806001858154811015156116aa57fe5b9060005260206000200160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000208281548110151561171c57fe5b906000526020600020906006020160050160009054906101000a900460ff1615151415156117b2576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260188152602001807f54686973206461746120686173206265656e2073656e742e000000000000000081525060200191505060405180910390fd5b826000806001858154811015156117c557fe5b9060005260206000200160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000208281548110151561183757fe5b906000526020600020906006020160050160006101000a81548160ff0219169083151502179055507f80e7f65d4e2321dd4a9c8326dd975ebdc2ba697bdc08e0036f7831c562f464c36001604051808215151515815260200191505060405180910390a15b80806001019150506113f7565b81806001019250506113e5565b7f80e7f65d4e2321dd4a9c8326dd975ebdc2ba697bdc08e0036f7831c562f464c36000604051808215151515815260200191505060405180910390a150505050565b61010060405190810160405280600073ffffffffffffffffffffffffffffffffffffffff168152602001600073ffffffffffffffffffffffffffffffffffffffff168152602001600081526020016000815260200160008152602001600015158152602001600060ff16815260200160001515815250905600a165627a7a72305820eff48841dc29a09bd80d2b8caab53c1cd638bc25f060f8be421f53957bd39da90029";

    public static final String FUNC_ADDRECORDDATA = "addRecordData";

    public static final String FUNC_CHECKRECORDEXIST = "checkRecordExist";

    public static final String FUNC_SCORERECORDDATA = "scoreRecordData";

    public static final String FUNC_GETRECORDDATABYID = "getRecordDataById";

    public static final String FUNC_SENDRECORDDATA = "sendRecordData";

    public static final Event ADDRECORDSUCCESS_EVENT = new Event("addRecordSuccess", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event SENDRECORDDATASUCCESS_EVENT = new Event("sendRecordDataSuccess", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
    ;

    public static final Event SCORERECORDDATASUCCESS_EVENT = new Event("scoreRecordDataSuccess", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
    ;

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

    public RemoteCall<TransactionReceipt> addRecordData(BigInteger creditDataId, String uploader) {
        final Function function = new Function(
                FUNC_ADDRECORDDATA, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(creditDataId), 
                new org.fisco.bcos.web3j.abi.datatypes.Address(uploader)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void addRecordData(BigInteger creditDataId, String uploader, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_ADDRECORDDATA, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(creditDataId), 
                new org.fisco.bcos.web3j.abi.datatypes.Address(uploader)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public RemoteCall<Boolean> checkRecordExist(String applicant, String uploader, BigInteger recordId, BigInteger creditDataId) {
        final Function function = new Function(FUNC_CHECKRECORDEXIST, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(applicant), 
                new org.fisco.bcos.web3j.abi.datatypes.Address(uploader), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(recordId), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(creditDataId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<TransactionReceipt> scoreRecordData(BigInteger recordId, BigInteger score) {
        final Function function = new Function(
                FUNC_SCORERECORDDATA, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(recordId), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint8(score)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void scoreRecordData(BigInteger recordId, BigInteger score, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_SCORERECORDDATA, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(recordId), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint8(score)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public RemoteCall<Tuple8<String, String, BigInteger, BigInteger, BigInteger, Boolean, BigInteger, Boolean>> getRecordDataById(BigInteger recordId) {
        final Function function = new Function(FUNC_GETRECORDDATABYID, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(recordId)), 
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
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(recordId), 
                new org.fisco.bcos.web3j.abi.datatypes.Bool(yn)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void sendRecordData(BigInteger recordId, Boolean yn, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_SENDRECORDDATA, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(recordId), 
                new org.fisco.bcos.web3j.abi.datatypes.Bool(yn)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public List<AddRecordSuccessEventResponse> getAddRecordSuccessEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ADDRECORDSUCCESS_EVENT, transactionReceipt);
        ArrayList<AddRecordSuccessEventResponse> responses = new ArrayList<AddRecordSuccessEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            AddRecordSuccessEventResponse typedResponse = new AddRecordSuccessEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._applicant = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._uploader = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse._id = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse._creditDataId = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse._time = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<AddRecordSuccessEventResponse> addRecordSuccessEventFlowable(BcosFilter filter) {
        return web3j.logFlowable(filter).map(new io.reactivex.functions.Function<Log, AddRecordSuccessEventResponse>() {
            @Override
            public AddRecordSuccessEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ADDRECORDSUCCESS_EVENT, log);
                AddRecordSuccessEventResponse typedResponse = new AddRecordSuccessEventResponse();
                typedResponse.log = log;
                typedResponse._applicant = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._uploader = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse._id = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse._creditDataId = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse._time = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<AddRecordSuccessEventResponse> addRecordSuccessEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        BcosFilter filter = new BcosFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ADDRECORDSUCCESS_EVENT));
        return addRecordSuccessEventFlowable(filter);
    }

    public List<SendRecordDataSuccessEventResponse> getSendRecordDataSuccessEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(SENDRECORDDATASUCCESS_EVENT, transactionReceipt);
        ArrayList<SendRecordDataSuccessEventResponse> responses = new ArrayList<SendRecordDataSuccessEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            SendRecordDataSuccessEventResponse typedResponse = new SendRecordDataSuccessEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.yn = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<SendRecordDataSuccessEventResponse> sendRecordDataSuccessEventFlowable(BcosFilter filter) {
        return web3j.logFlowable(filter).map(new io.reactivex.functions.Function<Log, SendRecordDataSuccessEventResponse>() {
            @Override
            public SendRecordDataSuccessEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(SENDRECORDDATASUCCESS_EVENT, log);
                SendRecordDataSuccessEventResponse typedResponse = new SendRecordDataSuccessEventResponse();
                typedResponse.log = log;
                typedResponse.yn = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<SendRecordDataSuccessEventResponse> sendRecordDataSuccessEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        BcosFilter filter = new BcosFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SENDRECORDDATASUCCESS_EVENT));
        return sendRecordDataSuccessEventFlowable(filter);
    }

    public List<ScoreRecordDataSuccessEventResponse> getScoreRecordDataSuccessEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(SCORERECORDDATASUCCESS_EVENT, transactionReceipt);
        ArrayList<ScoreRecordDataSuccessEventResponse> responses = new ArrayList<ScoreRecordDataSuccessEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ScoreRecordDataSuccessEventResponse typedResponse = new ScoreRecordDataSuccessEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.yn = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ScoreRecordDataSuccessEventResponse> scoreRecordDataSuccessEventFlowable(BcosFilter filter) {
        return web3j.logFlowable(filter).map(new io.reactivex.functions.Function<Log, ScoreRecordDataSuccessEventResponse>() {
            @Override
            public ScoreRecordDataSuccessEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(SCORERECORDDATASUCCESS_EVENT, log);
                ScoreRecordDataSuccessEventResponse typedResponse = new ScoreRecordDataSuccessEventResponse();
                typedResponse.log = log;
                typedResponse.yn = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ScoreRecordDataSuccessEventResponse> scoreRecordDataSuccessEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        BcosFilter filter = new BcosFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SCORERECORDDATASUCCESS_EVENT));
        return scoreRecordDataSuccessEventFlowable(filter);
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

    public static class AddRecordSuccessEventResponse {
        public Log log;

        public String _applicant;

        public String _uploader;

        public BigInteger _id;

        public BigInteger _creditDataId;

        public BigInteger _time;
    }

    public static class SendRecordDataSuccessEventResponse {
        public Log log;

        public Boolean yn;
    }

    public static class ScoreRecordDataSuccessEventResponse {
        public Log log;

        public Boolean yn;
    }
}
