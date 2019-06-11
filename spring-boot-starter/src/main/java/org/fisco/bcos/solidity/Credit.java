package org.fisco.bcos.solidity;

import io.reactivex.Flowable;
import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.web3j.abi.EventEncoder;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.*;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint256;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint8;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.DefaultBlockParameter;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.protocol.core.methods.request.BcosFilter;
import org.fisco.bcos.web3j.protocol.core.methods.response.Log;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple5;
import org.fisco.bcos.web3j.tx.Contract;
import org.fisco.bcos.web3j.tx.TransactionManager;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.ArrayList;
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
public class Credit extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b50610b58806100206000396000f30060806040526004361061004c576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806366da417f14610051578063e4c4e4e21461022f575b600080fd5b34801561005d57600080fd5b506100b8600480360381019080803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192905050506102de565b60405180806020018060200180602001806020018060200186810386528b818151815260200191508051906020019060200280838360005b8381101561010b5780820151818401526020810190506100f0565b5050505090500186810385528a818151815260200191508051906020019060200280838360005b8381101561014d578082015181840152602081019050610132565b50505050905001868103845289818151815260200191508051906020019060200280838360005b8381101561018f578082015181840152602081019050610174565b50505050905001868103835288818151815260200191508051906020019060200280838360005b838110156101d15780820151818401526020810190506101b6565b50505050905001868103825287818151815260200191508051906020019060200280838360005b838110156102135780820151818401526020810190506101f8565b505050509050019a505050505050505050505060405180910390f35b34801561023b57600080fd5b506102dc600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610775565b005b6060806060806060600060606000606080606080606060008e6040516020018082805190602001908083835b60208310151561032f578051825260208201915060208101905060208303925061030a565b6001836020036101000a0380198251168184511680821785525050505050509050019150506040516020818303038152906040526040518082805190602001908083835b6020831015156103985780518252602082019150602081019050602083039250610373565b6001836020036101000a038019825116818451168082178552505050505050905001915050604051809103902098506000808a60001916600019168152602001908152602001600020805480602002602001604051908101604052809291908181526020016000905b828210156104df578382906000526020600020906006020160c06040519081016040529081600082015481526020016001820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001600282015460001916600019168152602001600382015460001916600019168152602001600482015481526020016005820160009054906101000a900460ff1660ff1660ff168152505081526020019060010190610401565b50505050975087519650866040519080825280602002602001820160405280156105185781602001602082028038833980820191505090505b5095508660405190808252806020026020018201604052801561054a5781602001602082028038833980820191505090505b5094508660405190808252806020026020018201604052801561057c5781602001602082028038833980820191505090505b509350866040519080825280602002602001820160405280156105ae5781602001602082028038833980820191505090505b509250866040519080825280602002602001820160405280156105e05781602001602082028038833980820191505090505b509150600090505b87518110156107545787818151811015156105ff57fe5b9060200190602002015160000151868281518110151561061b57fe5b9060200190602002018181525050878181518110151561063757fe5b9060200190602002015160200151858281518110151561065357fe5b9060200190602002019073ffffffffffffffffffffffffffffffffffffffff16908173ffffffffffffffffffffffffffffffffffffffff1681525050878181518110151561069d57fe5b906020019060200201516060015184828151811015156106b957fe5b90602001906020020190600019169081600019168152505087818151811015156106df57fe5b906020019060200201516080015183828151811015156106fb57fe5b9060200190602002018181525050878181518110151561071757fe5b9060200190602002015160a00151828281518110151561073357fe5b9060200190602002019060ff16908160ff16815250508060010190506105e8565b85858585859d509d509d509d509d5050505050505050505091939590929450565b600061077f610ad6565b600160025401600281905550836040516020018082805190602001908083835b6020831015156107c4578051825260208201915060208101905060208303925061079f565b6001836020036101000a0380198251168184511680821785525050505050509050019150506040516020818303038152906040526040518082805190602001908083835b60208310151561082d5780518252602082019150602081019050602083039250610808565b6001836020036101000a0380198251168184511680821785525050505050509050019150506040518091039020915060c06040519081016040528060025481526020013373ffffffffffffffffffffffffffffffffffffffff16815260200183600019168152602001846040516020018082805190602001908083835b6020831015156108cf57805182526020820191506020810190506020830392506108aa565b6001836020036101000a0380198251168184511680821785525050505050509050019150506040516020818303038152906040526040518082805190602001908083835b6020831015156109385780518252602082019150602081019050602083039250610913565b6001836020036101000a0380198251168184511680821785525050505050509050019150506040518091039020600019168152602001428152602001600060ff168152509050600080836000191660001916815260200190815260200160002081908060018154018082558091505090600182039060005260206000209060060201600090919290919091506000820151816000015560208201518160010160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060408201518160020190600019169055606082015181600301906000191690556080820151816004015560a08201518160050160006101000a81548160ff021916908360ff16021790555050505060018290806001815401808255809150509060018203906000526020600020016000909192909190915090600019169055507fcc2d97dd2f0f2bb0d039b4928216d0ec45229cb1f8e22dfe466081e61161b06a81600001516040518082815260200191505060405180910390a150505050565b60c06040519081016040528060008152602001600073ffffffffffffffffffffffffffffffffffffffff168152602001600080191681526020016000801916815260200160008152602001600060ff16815250905600a165627a7a72305820a4174f287b66e39a62462f9894ea4e0be24301b140ff189d46ff997ee0a041480029";

    public static final String FUNC_GETCREDITDETIALDATABYPEOPLEID = "getCreditDetialDataByPeopleId";

    public static final String FUNC_ADDCREDITDATA = "addCreditData";

    public static final Event ADDCREDITDATASUCCESS_EVENT = new Event("addCreditDataSuccess", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected Credit(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Credit(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Credit(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Credit(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<Tuple5<List<BigInteger>, List<String>, List<byte[]>, List<BigInteger>, List<BigInteger>>> getCreditDetialDataByPeopleId(String id_string) {
        final Function function = new Function(FUNC_GETCREDITDETIALDATABYPEOPLEID, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(id_string)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint256>>() {}, new TypeReference<DynamicArray<Address>>() {}, new TypeReference<DynamicArray<Bytes32>>() {}, new TypeReference<DynamicArray<Uint256>>() {}, new TypeReference<DynamicArray<Uint8>>() {}));
        return new RemoteCall<Tuple5<List<BigInteger>, List<String>, List<byte[]>, List<BigInteger>, List<BigInteger>>>(
                new Callable<Tuple5<List<BigInteger>, List<String>, List<byte[]>, List<BigInteger>, List<BigInteger>>>() {
                    @Override
                    public Tuple5<List<BigInteger>, List<String>, List<byte[]>, List<BigInteger>, List<BigInteger>> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple5<List<BigInteger>, List<String>, List<byte[]>, List<BigInteger>, List<BigInteger>>(
                                convertToNative((List<Uint256>) results.get(0).getValue()), 
                                convertToNative((List<Address>) results.get(1).getValue()), 
                                convertToNative((List<Bytes32>) results.get(2).getValue()), 
                                convertToNative((List<Uint256>) results.get(3).getValue()), 
                                convertToNative((List<Uint8>) results.get(4).getValue()));
                    }
                });
    }

    public RemoteCall<TransactionReceipt> addCreditData(String id_string, String data_string) {
        final Function function = new Function(
                FUNC_ADDCREDITDATA, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(id_string), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(data_string)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void addCreditData(String id_string, String data_string, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_ADDCREDITDATA, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(id_string), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(data_string)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public List<AddCreditDataSuccessEventResponse> getAddCreditDataSuccessEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(ADDCREDITDATASUCCESS_EVENT, transactionReceipt);
        ArrayList<AddCreditDataSuccessEventResponse> responses = new ArrayList<AddCreditDataSuccessEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            AddCreditDataSuccessEventResponse typedResponse = new AddCreditDataSuccessEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<AddCreditDataSuccessEventResponse> addCreditDataSuccessEventFlowable(BcosFilter filter) {
        return web3j.logFlowable(filter).map(new io.reactivex.functions.Function<Log, AddCreditDataSuccessEventResponse>() {
            @Override
            public AddCreditDataSuccessEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(ADDCREDITDATASUCCESS_EVENT, log);
                AddCreditDataSuccessEventResponse typedResponse = new AddCreditDataSuccessEventResponse();
                typedResponse.log = log;
                typedResponse.id = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<AddCreditDataSuccessEventResponse> addCreditDataSuccessEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        BcosFilter filter = new BcosFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ADDCREDITDATASUCCESS_EVENT));
        return addCreditDataSuccessEventFlowable(filter);
    }

    @Deprecated
    public static Credit load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Credit(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Credit load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Credit(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Credit load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Credit(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Credit load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Credit(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Credit> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Credit.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Credit> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Credit.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Credit> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Credit.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Credit> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Credit.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class AddCreditDataSuccessEventResponse {
        public Log log;

        public BigInteger id;
    }
}
