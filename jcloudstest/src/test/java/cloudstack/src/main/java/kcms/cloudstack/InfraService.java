/**
 * 
 */
package kcms.cloudstack;

import java.util.List;
import java.util.Set;

import org.jclouds.cloudstack.domain.Host;
import org.jclouds.cloudstack.domain.Pod;
import org.jclouds.cloudstack.domain.Zone;

/**
 * @author zzonsang2
 * 
 * Zone, Pod, Host을 관리하기 위해 사용한다.
 */
public interface InfraService {

    /**
     * Zone 목록
     */
    public Set<Zone> listZones();
    
    /**
     * Pod 목록 
     */
    public Set<Pod> listPods();
    
    /**
     * Host 목록
     */
    public Set<Host> listHosts();
}
