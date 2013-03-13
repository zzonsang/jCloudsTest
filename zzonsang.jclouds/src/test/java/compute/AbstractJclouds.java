package compute;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.jclouds.cloudstack.CloudStackGlobalAsyncClient;
import org.jclouds.cloudstack.domain.AsyncJob;
import org.jclouds.cloudstack.domain.AsyncJob.Status;
import org.junit.Before;

import com.google.common.util.concurrent.ListenableFuture;

import core.CloudStackClientFactory;

public class AbstractJclouds {

    protected final String apiUrl = "http://183.98.30.86:8080/client/api";
    protected final String rootApiKey = "Ch66GTHk6E_8e9mC5nimFHOevDntkeTV43SQytIfRBsnUsM4Jx20yMe2hDh4DhGXZ6cE21H4zVDBARNSs_9s9A";
//    protected final String rootApiKey = "A4lQn-SV1mGEe5vb_ZbOUd8k761cX-6d45553R3wBllDzpF1xvns-jEaPlW2EMhk2SdrWbJXf2zqZv93mvSCkw";
    protected final String rootSecretKey = "AENfC4THE1Dycx9d6uZUnPbDqU5ETqMJrB6lCVhWfqmCOJnOOtV7es6nZTAahjeQfotH_LoR8zgMhM0_jo3XYA";
//    protected final String rootSecretKey = "k8LK-96G8t2Do_38_KRMPvmbfaRT_7iWSiGCLEpnn0DDB2twuQ2YcBE68rAuZBdz7YKKI3RNhiGNcyfMht9CFQ";
    protected final String zoneId = "239d62bf-5708-4a6b-8a41-c551975c1f23";
    protected final String serviceOfferingId = "6926f42a-1908-451a-a2ab-ac7fffbfd76a";
    protected final String templateId = "6099263a-24b4-4860-bbe7-a89aacd92084";
    protected final String rootDomainId = "07a01f54-45cc-4b86-9e02-457cf97831b4";
//    protected final String testDomainId = "81fe53cc-67ed-4662-b416-34ff31375ca3";
    protected final String testDomainId = "c3b12fda-d0fa-4223-9c16-61b19b66e229";
    
    // xenserver-server1
    protected final String hostId1 = "239cd2ce-4bbd-4766-9893-3c99071e88c6";
    
    // xenserver-desktop 1
    protected final String hostId2 = "7d41b8a2-fe47-439c-a77f-cfa3ebb093cb";
    
    // xenserver-desktop 2
    protected final String hostId3 = "fbadc544-9d32-45fc-85dd-ebc315e59597";
    
    protected final String testHost = "3a45fed0-1f4d-4c30-9ff9-f16f77bf7dab";
    
    protected CloudStackClientFactory factory;
    protected CloudStackGlobalAsyncClient client;
    
    protected CloudStackGlobalAsyncClient getAsyncClient() {
        factory = new CloudStackClientFactory();
        client = factory.getAsyncClient(apiUrl, rootApiKey, rootSecretKey);
        
        return client;
    }
    
    @Before
    public void before() {
        client = getAsyncClient();
    }
    
    protected void printResponse(String jobId) throws InterruptedException, ExecutionException, TimeoutException {
        boolean flag = true;
        do {
            ListenableFuture<AsyncJob<Object>> asyncJob = client.getAsyncJobClient().getAsyncJob(jobId);
            AsyncJob<Object> asyncJob2 = asyncJob.get(20, TimeUnit.SECONDS);
            System.out.println(asyncJob2);
            
            Status status = asyncJob2.getStatus();
            if (!status.equals(Status.IN_PROGRESS)) {
                flag = false;
                System.out.println("############ Result ##############");
                System.out.println(asyncJob2.getResult());
            }
            Thread.sleep(1000);
        } while(flag);
    }
}
