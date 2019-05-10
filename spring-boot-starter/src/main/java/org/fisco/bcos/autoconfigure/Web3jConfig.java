package org.fisco.bcos.autoconfigure;

import lombok.Data;
import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "web3j-config")
public class Web3jConfig {

    @Bean
    public Web3j getWeb3j(Service service) throws Exception {
        ChannelEthereumService channelEthereumService = new ChannelEthereumService();
        System.out.println(service.getGroupId());
        service.run();
        channelEthereumService.setChannelService(service);
        channelEthereumService.setTimeout(30000);
        return Web3j.build(channelEthereumService,service.getGroupId());
    }
}
