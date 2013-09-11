package com.eadmarket.pangu.manager.recharge;

import com.eadmarket.pangu.ManagerException;
import com.eadmarket.pangu.dto.RechargeDTO;

/**
 * ��ֵҵ�������
 * 
 * @author liuyongpo@gmail.com
 */
public interface RechargeManager {
	/**
	 * ��ᵥ���ĳ�ֵ��¼
	 * 
	 * @param rechargeId ��ֵ��¼���
	 * @param outOrderId �ⲿ���
	 * @param cash ��ֵ���Է�Ϊ��λ
	 * @return true ����������
	 * @throws ManagerException
	 */
	boolean finish(Long rechargeId, String outOrderId, Long cash) throws ManagerException;
	
	/**
	 * Ϊָ����Ա������ֵ��¼
	 * 
	 * @param rechargeDTO ��ֵ�������
	 * @return �����ɵĳ�ֵ��¼���
	 * @throws ManagerException
	 */
	Long create(RechargeDTO rechargeDTO) throws ManagerException;
}
