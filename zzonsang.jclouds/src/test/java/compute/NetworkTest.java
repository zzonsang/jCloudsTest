package compute;

import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.jclouds.cloudstack.domain.AsyncCreateResponse;
import org.jclouds.cloudstack.domain.Network;
import org.jclouds.cloudstack.domain.PublicIPAddress;
import org.jclouds.cloudstack.options.AssociateIPAddressOptions;
import org.jclouds.cloudstack.options.ListNetworksOptions;
import org.jclouds.cloudstack.options.ListPublicIPAddressesOptions;
import org.junit.Test;

import com.google.common.util.concurrent.ListenableFuture;

public class NetworkTest extends AbstractJclouds {
    
    @Test
    public void listNetworks() throws InterruptedException, ExecutionException, TimeoutException {
        
        ListNetworksOptions options = ListNetworksOptions.NONE;
        options.accountInDomain("accountName", testDomainId);
        
        ListenableFuture<Set<Network>> listNetworks = client.getNetworkClient().listNetworks(options);
        
        Set<Network> network = listNetworks.get(10, TimeUnit.SECONDS);
        
        System.out.println(network);
    }
    
//    @Test
//    public void createVirtualNetworks() {
//        
//        client.getNetworkClient().createNetworkInZone(zoneId, networkOfferingId, name, displayText, options)
//    }
    
    @Test
    public void listPublicIPAddresses() throws InterruptedException, ExecutionException, TimeoutException {
        
        ListPublicIPAddressesOptions options = ListPublicIPAddressesOptions.NONE;
        options.accountInDomain("accountName", testDomainId);
        
        ListenableFuture<Set<PublicIPAddress>> listPublicIPAddresses = client.getAddressClient().listPublicIPAddresses(options);
        Set<PublicIPAddress> publicIpAddress = listPublicIPAddresses.get(10, TimeUnit.SECONDS);
        
        System.out.println(publicIpAddress);
    }
    
    @Test
    public void associateIPAddressInZone() throws InterruptedException, ExecutionException, TimeoutException {
        
        AssociateIPAddressOptions options = AssociateIPAddressOptions.NONE;
        options.accountInDomain("accountName", testDomainId);
        
        ListenableFuture<AsyncCreateResponse> associateIPAddressInZone = client.getAddressClient().associateIPAddressInZone(zoneId, options);
        AsyncCreateResponse asyncCreateResponse = associateIPAddressInZone.get(10, TimeUnit.SECONDS);

        printResponse(asyncCreateResponse.getJobId());
    }

    @Test
    public void enableStaticNATForVirtualMachine() throws InterruptedException, ExecutionException, TimeoutException {
        
        String virtualMachineId = "aedc4134-48b7-4a4d-bf48-b18c1dfad5ba";
        
        ListPublicIPAddressesOptions options = ListPublicIPAddressesOptions.NONE;
        options.accountInDomain("accountName", testDomainId);
        
        ListenableFuture<Set<PublicIPAddress>> listPublicIPAddresses = client.getAddressClient().listPublicIPAddresses(options);
        
        Set<PublicIPAddress> publicIPAddress = listPublicIPAddresses.get(10, TimeUnit.SECONDS);
        for ( PublicIPAddress address : publicIPAddress ) {
            if ( !address.isSourceNAT() && !address.isStaticNAT() ) {
                String ipAddressId = address.getId();
                
                System.out.println("IPAddress ID : " + ipAddressId);
                
                ListenableFuture<Void> enableStaticNATForVirtualMachine = client.getNATClient().enableStaticNATForVirtualMachine(virtualMachineId, ipAddressId);
                enableStaticNATForVirtualMachine.get(20, TimeUnit.SECONDS);
            }
        }
    }
}
