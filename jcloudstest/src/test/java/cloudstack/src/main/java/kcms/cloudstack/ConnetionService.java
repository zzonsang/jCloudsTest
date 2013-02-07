/**
 * 
 */
package kcms.cloudstack;

import java.util.Set;

import kcms.cloudstack.model.Connection;

/**
 * @author zzonsang2
 *
 * cloudstack 연결 정보를 관리한다.
 * 1) 연결 항목 보기
 * 2) 연결 생성
 *      * 생성시 항목 
 *              * CloudStack 연결명
 *              * CloudStack UI URL 
 *              * API URL
 *              * API Key 
 *              * Secret Key
 * 3) 연결 수정
 *      * 수정시 항목
 *              * 수정시 id가 필요 
 *              * CloudStack 연결명
 *              * CloudStack UI URL 
 *              * API URL
 *              * API Key
 *              * Secret Key 
 *               
 * 4) 연결 삭제
 *      * 삭제시에는 id값으로만 처리한다. 
 */
public interface ConnetionService {

    public Set<Connection> listConnections();
    
    public boolean createConnection(String name, String uiUrl, String apiUrl, String apiKey, String secretKey);
    
    public boolean updateConnection(String id, String name, String uiUrl, String apiUrl, String apiKey, String secretKey);
    
    public boolean deleteConnection(String id);
}
