package compute;

import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.jclouds.cloudstack.domain.Account;
import org.jclouds.cloudstack.domain.Account.Type;
import org.jclouds.cloudstack.domain.ApiKeyPair;
import org.jclouds.cloudstack.domain.Domain;
import org.jclouds.cloudstack.options.CreateAccountOptions;
import org.jclouds.cloudstack.options.CreateDomainOptions;
import org.jclouds.cloudstack.options.ListDomainsOptions;
import org.junit.Test;

import com.google.common.util.concurrent.ListenableFuture;

public class AccountTest extends AbstractJclouds {
    
    @Test
    public void createDomain() throws InterruptedException, ExecutionException, TimeoutException {
        String name = "test";
        
        CreateDomainOptions options = CreateDomainOptions.NONE;
        options.parentDomainId(rootDomainId);
        
        ListenableFuture<Domain> createDomain = client.getDomainClient().createDomain(name, options);
        
        Domain domain = createDomain.get(10, TimeUnit.SECONDS);

        System.out.println(domain);
    }
    
    @Test
    public void listDomain() throws InterruptedException, ExecutionException, TimeoutException {
        ListDomainsOptions options = ListDomainsOptions.NONE;
        options.name("sc");  // like 검색 
        
        ListenableFuture<Set<Domain>> listDomains = client.getDomainClient().listDomains(options);
        
        Set<Domain> domains = listDomains.get(10, TimeUnit.SECONDS);
        System.out.println(domains);
    }
    
    @Test
    public void createAccount() throws InterruptedException, ExecutionException, TimeoutException {
        String userName = "userName";
        String email = "";
        String firstName = "firstName";
        String lastName = "lastName";
        String hashedPassword = "";
        
        String domainId = "81fe53cc-67ed-4662-b416-34ff31375ca3";
        
        CreateAccountOptions options = CreateAccountOptions.NONE;
        options.account("accountName");
        options.domainId(domainId);
        
        ListenableFuture<Account> createAccount = client.getAccountClient().createAccount(userName, Type.USER, email, firstName, lastName, hashedPassword, options);
        
        Account account = createAccount.get(10, TimeUnit.SECONDS);
        
        System.out.println(account);
    }
    
    @Test
    public void registerUserKeys() throws InterruptedException, ExecutionException, TimeoutException {
        String userId = "1a2db400-4730-40d2-b91b-ec39dd0828c1";
        
        ListenableFuture<ApiKeyPair> registerUserKeys = client.getUserClient().registerUserKeys(userId);
        
        ApiKeyPair apiKeyPair = registerUserKeys.get(10, TimeUnit.SECONDS);
        
        System.out.println(apiKeyPair);
    }
}
