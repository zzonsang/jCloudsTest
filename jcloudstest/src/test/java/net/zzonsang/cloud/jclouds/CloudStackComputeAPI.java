package net.zzonsang.cloud.jclouds;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.jclouds.Constants;
import org.jclouds.ContextBuilder;
import org.jclouds.cloudstack.CloudStackAsyncClient;
import org.jclouds.cloudstack.CloudStackClient;
import org.jclouds.cloudstack.CloudStackContext;
import org.jclouds.cloudstack.domain.AsyncCreateResponse;
import org.jclouds.cloudstack.domain.AsyncJob;
import org.jclouds.cloudstack.domain.PublicIPAddress;
import org.jclouds.cloudstack.domain.ServiceOffering;
import org.jclouds.cloudstack.domain.VirtualMachine;
import org.jclouds.cloudstack.domain.VirtualMachine.State;
import org.jclouds.cloudstack.domain.Volume;
import org.jclouds.cloudstack.domain.Zone;
import org.jclouds.rest.RestContext;
import org.junit.Before;
import org.junit.Test;

/**
 * Using Cloud Stack API
 */
public class CloudStackComputeAPI {

    private CloudStackClient client;
    
    @Before
    public void setUp() {
        Properties prop = new Properties();
        prop.put(Constants.PROPERTY_ENDPOINT, TestConstants.ENDPOINT);
        
        ContextBuilder builder = ContextBuilder.newBuilder(TestConstants.PROVIDER)
                .credentials(TestConstants.API_KEY, TestConstants.SECRET_KEY)
//              .modules(modules)
                .overrides(prop);
        
        CloudStackContext context = builder.buildView(CloudStackContext.class);
        
        RestContext<CloudStackClient, CloudStackAsyncClient> providerContext = context.getProviderSpecificContext();
        client = providerContext.getApi();
    }
    
    @Test
    public void test() throws Exception {
//        listVirtaulMachines()
//        listZones();
//        listVolumes();
//        listPublicIPAddresses();
//        listServiceOfferings();
//        deployVirtualMachineInZone();
//        randomRebootVirtualMachine();
//        randomRebootRunningVirtualMachine();
        stopVirtaulMachine();
//        startVirtualMachine();
        
        CommonUtil.completeMsg();
    }
    
    

    private void randomRebootVirtualMachine() throws InterruptedException {
        CommonUtil.beforeMsg();
        
        VirtualMachine next = client.getVirtualMachineClient().listVirtualMachines().iterator().next();
        String vmId = next.getId();
        System.out.println("### Reboot VirtualMachine : Target VM ID : " + vmId);
        String jobId = client.getVirtualMachineClient().rebootVirtualMachine(vmId);
        
        jobStatus(jobId);
    }
    
    private void randomRebootRunningVirtualMachine() throws Exception {
        CommonUtil.beforeMsg();
        
        Set<VirtualMachine> listVirtualMachines = client.getVirtualMachineClient().listVirtualMachines();
        
        String vmId = runningVM(listVirtualMachines);
        System.out.println("### Reboot VirtualMachine : Target VM ID : " + vmId);
        
        String jobId = client.getVirtualMachineClient().rebootVirtualMachine(vmId);
        
        jobStatus(jobId);
    }
    
    protected String runningVM( Set<VirtualMachine> listVirtualMachines ) throws Exception {
        for ( VirtualMachine vm : listVirtualMachines ) {
            if ( vm.getState() == State.RUNNING ) {
                return vm.getId();
            }
        }
        
        throw new Exception("Not found the running vm");
    }
    
    protected String runningVM() throws Exception {
        Set<VirtualMachine> listVirtualMachines = client.getVirtualMachineClient().listVirtualMachines();
        for ( VirtualMachine vm : listVirtualMachines ) {
            if ( vm.getState() == State.RUNNING ) {
                return vm.getId();
            }
        }
        
        throw new Exception("Not found the running vm");
    }
    
    protected String stoppedVM() throws Exception {
        Set<VirtualMachine> listVirtualMachines = client.getVirtualMachineClient().listVirtualMachines();
        for ( VirtualMachine vm : listVirtualMachines ) {
            if ( vm.getState() == State.STOPPED ) {
                return vm.getId();
            }
        }
        
        throw new Exception("Not found the stopped vm");
    }
    
    /**
     * 
     * @param jobId
     * @throws InterruptedException
     */
    protected void jobStatus(String jobId) throws InterruptedException {
        System.out.println("Job ID : " + jobId );
        
        boolean jobContinue = true;
        do {
            AsyncJob<Object> asyncJob = client.getAsyncJobClient().getAsyncJob(jobId);
            AsyncJob.Status status = asyncJob.getStatus();
            
            switch(status) {
            case IN_PROGRESS:
                System.out.println("Job Status : IN_PROGRESS");
                break;
            case SUCCEEDED:
                jobContinue = false;
                System.out.println("Job Status : SUCCEDED");
                break;
            case FAILED:
                jobContinue = false;
                System.out.println("Job Status : FAILED");
                break;
            case UNKNOWN:
                jobContinue = false;
                System.out.println("Job Status : UNKNOWN");
                break;
            }
            
            Thread.sleep(5 * 1000);
        } while( jobContinue );
    }
    
    /**
     * deployVirtualMachineInZone
     * 
     */
    private void deployVirtualMachineInZone() throws Exception {
        CommonUtil.beforeMsg();

        String zoneId = getFirstZoneId();
        String serviceOfferingId = getRandomServiceOfferingId();
        String templateId = getRandomTemplateId();
        
        AsyncCreateResponse deployVirtualMachineInZone = 
                client.getVirtualMachineClient().deployVirtualMachineInZone(zoneId, serviceOfferingId, templateId);
        
        System.out.println( deployVirtualMachineInZone.getJobId() );
    }
    
    protected String getRandomServiceOfferingId() throws Exception {
        return client.getOfferingClient().listServiceOfferings().iterator().next().getId();
    }
    
    protected String getRandomTemplateId() throws Exception {
        return client.getTemplateClient().listTemplates().iterator().next().getId();
    }
    
    protected String getFirstZoneId() throws Exception { 
        Zone zone = client.getZoneClient().listZones().iterator().next();
        if ( zone != null ) return zone.getId();
        else throw new Exception("Not found zone");
    }
    
    /**
     * listServiceOfferings
     */
    private void listServiceOfferings() {
        CommonUtil.beforeMsg();
        
        Set<ServiceOffering> listServiceOfferings = client.getOfferingClient().listServiceOfferings();
        
        for ( ServiceOffering offering : listServiceOfferings ) {
            System.out.println( offering );
        }
        
    }
    
    /**
     * listVirtualMachines
     */
    private void listVirtaulMachines() {
        CommonUtil.beforeMsg();
        
        Set<VirtualMachine> listVirtualMachine = client.getVirtualMachineClient().listVirtualMachines();
        
        List<VirtualMachine> notRunningVm = new LinkedList<VirtualMachine>();
        
        System.out.println("\n@@@ Running VMs@@@");
        for (VirtualMachine vm : listVirtualMachine ) {
            if ( vm.getState() == VirtualMachine.State.RUNNING ) {
                System.out.println(vm.toString());
            } else {
                notRunningVm.add(vm);
            }
        }
        
        System.out.println("\n@@@ Not Running VMs @@@");
        for ( VirtualMachine vm : notRunningVm ) {
            System.out.println("VM state : " + vm.getState().toString() + ", Detail : " + vm);
        }
    }
    
    /**
     * listZones
     */
    private void listZones() {
        CommonUtil.beforeMsg();
        
        Set<Zone> listZones = client.getZoneClient().listZones();
        
        for ( Zone zone : listZones ) {
            System.out.println(zone);
        }
    }
    
    /**
     * listPods
     */
//    @Test
//    public void listPods() {
//        System.out.println("### listPods ###");
//        
//        cli
//        
//        System.out.println();
//    }
    
    /**
     * listVolumes
     */
    private void listVolumes() {
        CommonUtil.beforeMsg();
        
        Set<Volume> listVolumes = client.getVolumeClient().listVolumes();
        for ( Volume volume : listVolumes ) {
            System.out.println(volume.toString());
        }
    }
    
    /**
     * listPublicIPAddresses
     */
    private void listPublicIPAddresses() {
        CommonUtil.beforeMsg();
        
        Set<PublicIPAddress> listPublicIPAddresses = client.getAddressClient().listPublicIPAddresses();
        for ( PublicIPAddress address : listPublicIPAddresses ) {
            System.out.println(address.toString());
        }
    }
    
    private void startVirtualMachine() throws Exception {
        CommonUtil.beforeMsg();
       
        VirtualMachine virtualMachine = client.getVirtualMachineClient().getVirtualMachine( stoppedVM() );
        String name = virtualMachine.getName();
        String vmId = virtualMachine.getId();
        System.out.println("VM ID : " + vmId + ", VM Name : " + name);
        String jobId = client.getVirtualMachineClient().startVirtualMachine(vmId);
        
        jobStatus(jobId);
    }
    
    private void stopVirtaulMachine() throws Exception {
        CommonUtil.beforeMsg();
       
        VirtualMachine virtualMachine = client.getVirtualMachineClient().getVirtualMachine( runningVM() );
        String name = virtualMachine.getName();
        String vmId = virtualMachine.getId();
        System.out.println("VM ID : " + vmId + ", VM Name : " + name);
        String jobId = client.getVirtualMachineClient().stopVirtualMachine(vmId);
        
        jobStatus(jobId);
    }
}


































