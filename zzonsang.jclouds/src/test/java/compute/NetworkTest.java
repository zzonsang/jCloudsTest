package compute;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.jclouds.cloudstack.domain.AsyncCreateResponse;
import org.jclouds.cloudstack.domain.FirewallRule;
import org.jclouds.cloudstack.domain.FirewallRule.Protocol;
import org.jclouds.cloudstack.domain.Network;
import org.jclouds.cloudstack.domain.PublicIPAddress;
import org.jclouds.cloudstack.options.AssociateIPAddressOptions;
import org.jclouds.cloudstack.options.CreateFirewallRuleOptions;
import org.jclouds.cloudstack.options.ListFirewallRulesOptions;
import org.jclouds.cloudstack.options.ListNetworksOptions;
import org.jclouds.cloudstack.options.ListPublicIPAddressesOptions;
import org.junit.Test;

import com.google.common.util.concurrent.ListenableFuture;

public class NetworkTest extends AbstractJclouds {
    
    @Test
    public void test() {
        Set<String> tests = new HashSet<String>();
        tests.add("test");
        
        System.out.println(tests.contains("test"));
        System.out.println(tests.contains("test1"));
    }
    
    @Test
    public void createFirewall() throws InterruptedException, ExecutionException, TimeoutException {
        
        String cidr = "0.0.0.0/4";
        Set<String> cidrs = new HashSet<String>();
        cidrs.add(cidr);
        
        int start = 1150;
        int end = 1190;
        Protocol protocol = Protocol.TCP;
        
        CreateFirewallRuleOptions options = CreateFirewallRuleOptions.NONE;
        options.startPort(start);
        options.endPort(end);
        options.CIDRs(cidrs);
        
        String ipAddressId = "317da80e-9217-491c-9f6d-d37072114d6a";
        
        ListFirewallRulesOptions firewallOptions = ListFirewallRulesOptions.NONE;
        firewallOptions.ipAddressId(ipAddressId);
        
        ListenableFuture<Set<FirewallRule>> listFirewallRules = 
                client.getFirewallClient().listFirewallRules(firewallOptions);
        Set<FirewallRule> firewallRules = listFirewallRules.get(10, TimeUnit.SECONDS);
        for (FirewallRule rule : firewallRules) {
            Set<String> rulecidrs =  rule.getCIDRs();
            if (!rulecidrs.contains(cidr)) continue;
            if (!rule.getProtocol().equals(protocol)) continue;
            int startPort = rule.getStartPort();
            int endPort = rule.getEndPort();
            if (start>=startPort && start<=endPort) {
                System.out.println("Exist");
                return;
            }
            // 범위 안에 포함되면 중복된 것으로 처리한다.
            if (end>=startPort && end<=endPort) {
                System.out.println("Exist");
                return;
            }
        }
        
//        for (int i=0;i<2;i++) {
            ListenableFuture<AsyncCreateResponse> createFirewallRuleForIpAndProtocol = 
                    client.getFirewallClient().createFirewallRuleForIpAndProtocol(ipAddressId, protocol, options);
            
            AsyncCreateResponse asyncCreateResponse = createFirewallRuleForIpAndProtocol.get(10, TimeUnit.SECONDS);
//            printResponse(asyncCreateResponse.getJobId());
            
//            Thread.sleep(2000);
//        }
    }
    
    @Test
    public void listFirewall() throws InterruptedException, ExecutionException, TimeoutException {
        ListFirewallRulesOptions options = ListFirewallRulesOptions.NONE;
        options.ipAddressId("8d29dadc-a7ae-4ed9-8c18-a4c2b52e592a");
        
        ListenableFuture<Set<FirewallRule>> listFirewallRules = client.getFirewallClient().listFirewallRules(options);
        Set<FirewallRule> firewallRules = listFirewallRules.get(10, TimeUnit.SECONDS);
        for (FirewallRule rule : firewallRules) {
            System.out.println(rule);
        }
    }
    
    private boolean checkFirewall(FirewallRule rule, int startPort, int endPort, String cidr, Protocol protocol) {
        if (rule.getStartPort() != startPort) return false;
        if (rule.getEndPort() != endPort) return false;
        if (!(rule.getCIDRs().size() == 1)) return false;
        if (rule.getCIDRs().iterator().next().equals(cidr)) return false;
        if (!rule.getProtocol().equals(protocol)) return false;
        return true;
    }
    
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
