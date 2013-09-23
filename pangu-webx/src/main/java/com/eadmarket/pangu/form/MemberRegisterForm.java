/**
 * 
 */
package com.eadmarket.pangu.form;

import lombok.Data;


/**
 * @author liuyongpo@gmail.com
 *
 */
@Data
public class MemberRegisterForm {
	/**
	 * 会员昵称
	 */
	private String nick;
	/**
	 * email
	 */
	private String email;
	/**
	 * 会员密码
	 */
	private String password;
	/**
	 * 账户类型
	 */
	private String passwordConfirm; 
	/**
	 * 提现方式
	 */
	private Integer payment;
	/**
	 * 资金账户用于会员提现
	 */
	private String account;

}
