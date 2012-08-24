package net.zzonsang.cloud.jclouds.cloudstack;

import java.util.Set;

import net.zzonsang.cloud.jclouds.CommonUtil;

import org.jclouds.cloudstack.CloudStackClient;
import org.jclouds.cloudstack.domain.Network;
import org.jclouds.cloudstack.domain.NetworkOffering;
import org.junit.Before;
import org.junit.Test;

public class NetworkAPITest {
    
    private CloudStackClient client;
    
    @Before
    public void before() {
        client = CloudStackClientUtil.getCloudStackClient();
    }
    
    public void createNetworkOffering() {
    }

    public void updateNetworkOffering() {
    }
    
    public void deleteNetworkOffering() {
    }
    
    @Test
    public void listNetworkOfferings() {
        CommonUtil.beforeMsg();
        
        Set<NetworkOffering> listNetworkOfferings = client.getOfferingClient().listNetworkOfferings();
        
        for ( NetworkOffering offering : listNetworkOfferings ) {
            System.out.println(offering);
        }
        
        CommonUtil.completeMsg();
    }
    
    @Test
    public void listNetworks() {
        CommonUtil.beforeMsg();
        
        Set<Network> listNetworks = client.getNetworkClient().listNetworks();
        
        for ( Network network : listNetworks ) {
            System.out.println( network );
        }
        
        CommonUtil.completeMsg();
    }
    
//    @Test
    public void createNetwork() throws Exception {
        CommonUtil.beforeMsg();
        
        String zoneId = CloudStackClientUtil.getFirstZoneID(client);
        String networkOfferingId = CloudStackClientUtil.getFirstNetworkOfferingId(client);
        String name = "jcloudsNetwork";
        String displayText = "jcloudsNetwork";
        
        Network network = client.getNetworkClient().createNetworkInZone(zoneId, networkOfferingId, name, displayText, null);
        
        System.out.println(network.toString());
        
        CommonUtil.completeMsg();
    }
    
    public void deleteNetwork() {
    }
    
//    @Test
    public void updateNetwork() {
        CommonUtil.beforeMsg();
        
        String networkId = client.getNetworkClient().listNetworks().iterator().next().getId();
        client.getNetworkClient().getNetwork(networkId).toBuilder().displayText("guestNetworkForBasicZone2");
        
        client.getNetworkClient().getNetwork(networkId).builder().displayText("guestNetworkForBasicZone2");
        
        CommonUtil.completeMsg();
    }
    
    @Test
    public void createPhysicalNetwork() {
        CommonUtil.beforeMsg();
        
        
        
        CommonUtil.completeMsg();
    }
}
