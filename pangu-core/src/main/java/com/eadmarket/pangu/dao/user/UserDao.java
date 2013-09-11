package com.eadmarket.pangu.dao.user;

import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.domain.UserDO;
import com.eadmarket.pangu.domain.UserDO.UserStatus;

/**
 * 会员Dao接口类
 * 
 * @author liuyongpo@gmail.com
 */
public interface UserDao {
	/**
	 * 根据会员编号查询会员
	 * 
	 * @param userId 会员编号
	 * @return 会员实体
	 */
	UserDO getById(Long userId) throws DaoException;
	
	/**
	 * 根据email查询会员信息
	 * @param email 会员email
	 * @return 会员信息
	 */
	UserDO getByEmail(String email) throws DaoException;
	
	/**
	 * 更新会员状态
	 * 
	 * @param userId 会员编号
	 * @param status targetStatus
	 */
	void updateStatusById(Long userId, UserStatus status) throws DaoException;
	
	/**
	 * 新增会员
	 * 
	 * @param user
	 * @return 会员编号
	 */
	Long insert(UserDO user) throws DaoException;
	
	/**
	 * 给账户增加金额，注意并发修改
	 * 
	 * @param userId 会员编号
	 * @param cash 金额，以分为单位
	 * @return 更新是否成功
	 */
	void addCashTo(Long userId, Long cash) throws DaoException;
	
	/**
	 * 扣除账户金额，注意并发修改并且金额不能小于0
	 * 
	 * @param userId 会员编号
	 * @param cash 金额，以分为单位
	 * @return 更新是否成功
	 */
	boolean reduceCachFrom(Long userId, Long cash) throws DaoException;
	
	/**
	 * 重置用户的密码
	 * 
	 * @param userId 用户编号
	 * @param password 加密之后的密码
	 */
	void updatePassword(Long userId, String password) throws DaoException;
	
	/**
	 * 修改会员信息，头像，提现账户，支付方式
	 * 
	 * @param userDO
	 * @throws DaoException
	 */
	void updateUser(UserDO userDO) throws DaoException;
}
