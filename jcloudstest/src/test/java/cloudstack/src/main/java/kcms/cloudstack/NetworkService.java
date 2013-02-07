/**
 * 
 */
package kcms.cloudstack;

import java.util.Set;

import org.jclouds.cloudstack.domain.FirewallRule;

/**
 * @author zzonsang2
 *
 * Network, Firewall, 등을 관리한다.
 */
public interface NetworkService {
    
    /**
     * 포트포워딩 생성 
     */
    public void createPortforwardingRule();
    
    /**
     * 포트포워딩 삭제
     * 
     * @param id
     *          the id of the forwarding rule
     */
    public void deletePortforwardingRule(String id);
    
    /**
     * 포트할당 목록 
     */
    public Set<FirewallRule> listPortForwardingRules();
    
    /**
     * 
     * @param id
     * @return
     */
    public FirewallRule getPortForwardingRule(String id);
}
