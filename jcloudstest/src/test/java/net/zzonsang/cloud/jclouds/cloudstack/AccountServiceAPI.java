package net.zzonsang.cloud.jclouds.cloudstack;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import net.zzonsang.cloud.jclouds.CommonUtil;

import org.apache.commons.lang.RandomStringUtils;
import org.jclouds.cloudstack.CloudStackGlobalClient;
import org.jclouds.cloudstack.domain.Account;
import org.junit.Before;
import org.junit.Test;

public class AccountServiceAPI {

    private CloudStackGlobalClient client;
    
    @Before
    public void before() {
        client = CloudStackClientUtil.getCloudStackClient();
    }
    
    @Test
    public void createAccount() throws NoSuchAlgorithmException {
        CommonUtil.beforeMsg();
        
//        String username = RandomStringUtils.random(6);
//        String email = username + "@test.kr";
//        String firstName = RandomStringUtils.random(8);
//        String lastName = RandomStringUtils.random(3);
//        MessageDigest digest = MessageDigest.getInstance("MD5");
//        digest.update(rawpassword.getBytes());
//        byte[] convertByte = digest.digest();
//        String hashedPassword = new String( convertByte, 0, digest.digest().length ); 
        
        // Account 생성 
        Account createAccount = client.getAccountClient().createAccount("hscho2", Account.Type.USER, "hscho@test.com", 
                "hs", "cho", "123ewq123ewq123ewq123ewq123ewq123ewq123ewq123ewq123ewq123ewq");
        
        System.out.println("[Account Name] : " + createAccount.getName());
        
        // User 생성
        
        CommonUtil.completeMsg();
    }
    
}
