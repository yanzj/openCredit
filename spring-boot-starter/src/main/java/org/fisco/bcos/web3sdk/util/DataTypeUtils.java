package org.fisco.bcos.web3sdk.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.encoders.Hex;
import org.fisco.bcos.web3sdk.BrokerException;
import org.fisco.bcos.web3sdk.ErrorCode;
import org.fisco.bcos.web3sdk.constant.OpenCreditConstants;

/**
 * Data type conversion utilities between solidity data type and java data type.
 */
@Slf4j
public final class DataTypeUtils {
    /**
     * encode eventId
     *
     * @param topicName
     * @param eventBlockNumber blockchain blocknumber
     * @param eventSeq eventSeq number
     * @return encodeString
     */
    public static String encodeEventId(String topicName, int eventBlockNumber, int eventSeq) {
        StringBuilder sb = new StringBuilder();
        sb.append(genTopicNameHash(topicName));
        sb.append(OpenCreditConstants.EVENT_ID_SPLIT_CHAR);
        sb.append(eventSeq);
        sb.append(OpenCreditConstants.EVENT_ID_SPLIT_CHAR);
        sb.append(eventBlockNumber);
        return sb.toString();
    }

    /**
     * generate topicName hash
     *
     * @param topicName
     * @return substring left 4bit hash data to hex encode
     */
    public static String genTopicNameHash(String topicName) {
        String encodeData = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] messageDigest = md.digest(topicName.getBytes());
            encodeData = new String(Hex.encode(messageDigest)).substring(0, OpenCreditConstants.TOPIC_NAME_ENCODE_LENGTH);
        } catch (NoSuchAlgorithmException e) {
            log.error("NoSuchAlgorithmException:{}", e.getMessage());
        }
        return encodeData;
    }

    /**
     * decode eventId get seq
     *
     * @param eventId
     * @return seq
     */
    public static Long decodeSeq(String eventId) throws BrokerException {
        String[] tokens = eventId.split(OpenCreditConstants.EVENT_ID_SPLIT_CHAR);
        if (tokens.length != 3) {
            throw new BrokerException(ErrorCode.EVENT_ID_IS_ILLEGAL);
        }

        if (tokens[0].length() != OpenCreditConstants.TOPIC_NAME_ENCODE_LENGTH) {
            throw new BrokerException(ErrorCode.EVENT_ID_IS_ILLEGAL);
        }
        return DataTypeUtils.String2Long(tokens[1]);
    }

    /**
     * decode eventId get blockNumber
     *
     * @param eventId
     * @return blockNumber
     */
    public static Long decodeBlockNumber(String eventId) throws BrokerException {
        String[] tokens = eventId.split(OpenCreditConstants.EVENT_ID_SPLIT_CHAR);
        if (tokens.length != 3) {
            throw new BrokerException(ErrorCode.EVENT_ID_IS_ILLEGAL);
        }
        if (tokens[0].length() != OpenCreditConstants.TOPIC_NAME_ENCODE_LENGTH) {
            throw new BrokerException(ErrorCode.EVENT_ID_IS_ILLEGAL);
        }
        return DataTypeUtils.String2Long(tokens[2]);
    }

    /**
     * decode eventId get topicName hash
     *
     * @param eventId
     * @return topicName hash
     */
    public static String decodeTopicNameHash(String eventId) throws BrokerException {
        String[] tokens = eventId.split(OpenCreditConstants.EVENT_ID_SPLIT_CHAR);
        if (tokens.length != 3) {
            throw new BrokerException(ErrorCode.EVENT_ID_IS_ILLEGAL);
        }
        if (tokens[0].length() != OpenCreditConstants.TOPIC_NAME_ENCODE_LENGTH) {
            throw new BrokerException(ErrorCode.EVENT_ID_IS_ILLEGAL);
        }
        return tokens[0];
    }

    /**
     * String2Long, return 0L if exception.
     *
     * @param value the value
     * @return java.lang.Long
     */
    public static Long String2Long(String value) {
        try {
            return Long.valueOf(value);
        } catch (NumberFormatException e) {
            return 0L;
        }
    }
}
