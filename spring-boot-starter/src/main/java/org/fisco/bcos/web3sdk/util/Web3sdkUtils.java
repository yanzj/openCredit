package org.fisco.bcos.web3sdk.util;


import java.io.FileWriter;
import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import org.fisco.bcos.web3sdk.config.FiscoConfig;
import org.fisco.bcos.web3sdk.Web3SDK2Wrapper;


/**
 * Utils 4 web3sdk.
 *
 * @author matthewliu
 * @since 2019/02/12
 */
@Slf4j
public class Web3sdkUtils {
    private static void writeAddressToFile(String filePath, String contractName, String contractAddress) {
        try (FileWriter fileWritter = new FileWriter(filePath, false)) {
            String content = String.format("%s=%s", contractName, contractAddress);
            fileWritter.write(content);
            fileWritter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * tool to generate system contract, 'TopicController'.
     *
     * @param args optional param, save data into file if set.
     * Usage:
     * java -Xbootclasspath/a:./config -cp weevent-broker-2.0.0.jar -Dloader.main=com.webank.weevent.broker.fisco.util.Web3sdkUtils org.springframework.boot.loader.PropertiesLauncher ./address.txt [1]
     */
    public static void main(String[] args) {
        try {
            FiscoConfig fiscoConfig = new FiscoConfig();
            fiscoConfig.load();

            String address;
            if (args.length >= 2) {
                // 2.0x
                org.fisco.bcos.web3j.crypto.Credentials credentials = Web3SDK2Wrapper.getCredentials(fiscoConfig);
                org.fisco.bcos.web3j.protocol.Web3j web3j = Web3SDK2Wrapper.initWeb3j(Long.valueOf(args[1]), fiscoConfig);
//                address = Web3SDK2Wrapper.deployTopicControl(web3j, credentials);
            } else {
                // 1.x
                throw new Exception("Argument too short");
            }

//            System.out.println("deploy contract[TopicController] success, address: " + address);
            if (args.length >= 1) {
//                writeAddressToFile(args[0], "TopicController", address);
            }
        } catch (Exception e) {
            log.error("detect exception", e);
            System.exit(1);
        }

        // web3sdk can't exit gracefully
        System.exit(0);
    }
}
