/**
 * 
 */
package kcms.cloudstack;

import java.util.List;
import java.util.Set;

import org.jclouds.cloudstack.domain.VirtualMachine;
import org.jclouds.compute.domain.Template;

/**
 * @author zzonsang2
 *
 * 1) Template
 *      * Template 보기
 * 2) VM lifecyle
 *      * vm 목록 보기 
 *      * Deploy
 *      * Start
 *      * Stop
 *      * Restart
 *      * Destroy
 *      * Password Reset
 *      * Service Offering Change
 *      
 * 3) Usage
 * 4) Operation System
 * 5) 
 * 
 * 고려해야할 사항 
 * 1) org.jclouds.compute을 사용해야 하는 가?
 *      org.jclouds.cloudstack 을 사용 중이다.
 * 
 * 2) 
 */
public interface ComputeService {

    /**
     * VirtualMachine 목록
     */
    public Set<VirtualMachine> listVirtaulMachines();
    
    /**
     * VirtualMachine 생성  
     */
    public String deployVirtualMachineInZone();
    
    /**
     * VirtualMachine 시작
     * 
     * @param virtualMachineId
     * @return
     */
    public String startVirtualMachine(String virtualMachineId);
    
    /**
     * VirtualMachine 정지
     * 
     * @param virtualMachineId
     * @return
     */
    public String stopVirtualMachine(String virtualMachineId);
    
    /**
     * VirtualMachine 재시작
     * 
     * @param virtualMachineId
     * @return
     */
    public String restartVirtualMachine(String virtualMachineId);
    
    /**
     * VirtualMachine 삭제
     * 
     * @param virtualMachineId
     * @return
     */
    public String destroyVirtualMachine(String virtualMachineId);
    
    /**
     * VirtualMachine 암호 재설정
     * 
     * @param virtualMachineId
     * @return
     */
    public String resetpasswordForVirtualMachine(String virtualMachineId);
    
    /**
     * Template 목록 보기
     */
    public Set<Template> listTemplates();
    
}
