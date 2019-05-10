package org.fisco.bcos;

import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ServiceTest {

    @Autowired
    Service service;

    @Test
    public void getWeb3j() throws Exception {
        ChannelEthereumService channelEthereumService = new ChannelEthereumService();
        System.out.println(service.getGroupId());
        service.run();
        channelEthereumService.setChannelService(service);
        channelEthereumService.setTimeout(30000);
        assertTrue(service.getGroupId() == 1);
    }
}
