package org.fisco.bcos;

import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@EnableConfigurationProperties
@RestController
public class Application {
    @Value("${spring.application.name}")
    private String name;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @RequestMapping(value = "/")
    public String name() {
        return "Hello world, here is "+ name;
    }

//    @Bean
//    public Web3j getWeb3j(Service service) throws Exception {
//        ChannelEthereumService channelEthereumService = new ChannelEthereumService();
//        System.out.println(service.getGroupId());
//        service.setGroupId(1);
//        service.run();
//        channelEthereumService.setChannelService(service);
//        channelEthereumService.setTimeout(30000);
//        return Web3j.build(channelEthereumService,service.getGroupId());
//    }
}
