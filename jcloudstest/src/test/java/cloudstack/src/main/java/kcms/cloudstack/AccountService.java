/**
 * 
 */
package kcms.cloudstack;

/**
 * @author zzonsang2
 *
 * 요구사항
 * 1) Service : Account = 1:1
 * 2) Account는 Service 생성시에 자동으로 생성된다.
 *      * Project+Account
 *      * 중복시에는 뒤에 숫자를 붙인다.
 * 3) Account Type은 User로 생성하고 Domain은 별도록 만들지 않는다.
 *      * user = 0
 *      * admin = 1
 *      * domain admin = 2
 * 
 * 이슈 
 * 1) Apikey, Secretkey 처리 문제
 * 2) Account : User = 1:1 인가?
 * 3) jclouds api는 user api 기준으로 작성된 것(?)이라 그런지 createAccount 없다. 고로 만들어야 한다.
 */
public interface AccountService {
    
    public void createAccount();
    
    
}
