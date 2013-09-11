package com.eadmarket.pangu.manager.recharge;

import com.eadmarket.pangu.ManagerException;
import com.eadmarket.pangu.dto.RechargeDTO;

/**
 * 充值业务操作层
 * 
 * @author liuyongpo@gmail.com
 */
public interface RechargeManager {
	/**
	 * 完结单独的充值记录
	 * 
	 * @param rechargeId 充值记录编号
	 * @param outOrderId 外部编号
	 * @param cash 充值金额，以分为单位
	 * @return true 如果正常完结
	 * @throws ManagerException
	 */
	boolean finish(Long rechargeId, String outOrderId, Long cash) throws ManagerException;
	
	/**
	 * 为指定会员创建充值记录
	 * 
	 * @param rechargeDTO 充值传输对象
	 * @return 新生成的充值记录编号
	 * @throws ManagerException
	 */
	Long create(RechargeDTO rechargeDTO) throws ManagerException;
}
