package com.eadmarket.pangu.manager.user;

import com.eadmarket.pangu.ManagerException;
import com.eadmarket.pangu.domain.UserDO;

/**
 * User涓氬姟鎿嶄綔绫� * 
 * @author liuyongpo@gmail.com
 */
public interface UserManager {
	/**
	 * 鐢ㄦ埛娉ㄥ唽鍔熻兘
	 * 
	 * @param user 浼氬憳淇℃伅
	 * @return 浼氬憳缂栧彿
	 * @throws ManagerException
	 */
	Long registerUser(UserDO user) throws ManagerException;
	
	/**
	 * 閫氳繃userId鑾峰彇User
	 * 
	 * @param userId 浼氬憳缂栧彿
	 * @return
	 * @throws ManagerException
	 */
	UserDO getUserById(Long userId) throws ManagerException;
	
	/**
	 * 鏍规嵁鐢ㄦ埛email鍜屽瘑鐮佽幏鍙栫敤鎴蜂俊鎭�	 * 
	 * @param email
	 * @param password
	 * @return
	 * @throws ManagerException
	 */
	UserDO getUserByEmailAndPwd(String email, String password) throws ManagerException;
	
	/**
	 * 閫氳繃email鑾峰彇User
	 * 
	 * @param email 浼氬憳email
	 * @return
	 * @throws ManagerException
	 */
	UserDO getUserByEmail(String email) throws ManagerException;
	
	/**
	 * 婵�椿浼氬憳
	 * 
	 * @param userId 浼氬憳缂栧彿
	 * @throws ManagerException
	 */
	void activeUser(Long userId) throws ManagerException;
	
	/**
	 * 缁欐寚瀹氱殑浼氬憳鍙戦�璐﹀彿婵�椿閭欢
	 * 
	 * @param userId 浼氬憳缂栧彿
	 * @throws ManagerException
	 */
	void sendActiveEmailTo(Long userId) throws ManagerException;
	
	/**
	 * 重置密码
	 * 
	 * @param userId 用户编号
	 * @param orinalPassword 原始密码
	 * @param newPassword 新密码
	 * @throws ManagerException
	 */
	void resetPassword(Long userId, String orinalPassword, String newPassword) throws ManagerException;
	
	/**
	 * 更新用户信息
	 * 
	 * @param userDO
	 * @throws ManagerException
	 */
	void updateProfile(UserDO userDO) throws ManagerException;
}
