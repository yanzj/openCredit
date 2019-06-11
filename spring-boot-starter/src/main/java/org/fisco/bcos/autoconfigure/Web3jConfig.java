package org.fisco.bcos.autoconfigure;

import lombok.Data;
import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.gm.GenCredential;
import org.fisco.bcos.web3j.crypto.gm.KeyInfo;
import org.fisco.bcos.web3j.crypto.gm.KeyInfoInterface;
import org.fisco.bcos.web3j.precompile.cns.CnsService;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.fisco.bcos.web3sdk.config.FiscoConfig;
import org.fisco.bcos.web3sdk.util.KeyInfoUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "web3j-config")
public class Web3jConfig {


    @Bean
    public KeyInfoUtils getKeyInfoUtils() {
        KeyInfoUtils keyInfoUtils = new KeyInfoUtils();
        keyInfoUtils.loadPrivateKeyInfo("./node.key");
        return keyInfoUtils;
    }

    @Bean
    public Web3j getWeb3j(Service service) throws Exception {
        ChannelEthereumService channelEthereumService = new ChannelEthereumService();
        service.run();
        channelEthereumService.setChannelService(service);
        channelEthereumService.setTimeout(30000);
        return Web3j.build(channelEthereumService,service.getGroupId());
    }

    @Bean
    public Credentials getCredentials(KeyInfoUtils keyInfoUtils) {

        // Todo: 先写死，后期得从本地读取 private key 然后生成
//        return GenCredential.create("b83261efa42895c38c6c2364ca878f43e77f3cddbc922bf57d0d48070f79feb6");
        Credentials credentials;
        try {
            String privateKey = keyInfoUtils.getPrivateKey();
            // 取
            credentials = GenCredential.create(privateKey.substring(privateKey.length() - 21, privateKey.length()-1));
        } catch (Exception e) {
            System.out.println(e.toString());
            throw e;
        }
        return credentials;

    }

    @Bean
    public CnsService getCnsService(Web3j web3j, Credentials credentials) {
        CnsService cnsService = new CnsService(web3j, credentials);
        return cnsService;
//        List<CnsInfo> qcns = cnsService.queryCnsByNameAndVersion("Credit", 1);
    }


}
